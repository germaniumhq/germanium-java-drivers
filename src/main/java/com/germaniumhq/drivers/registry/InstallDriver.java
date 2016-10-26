package com.germaniumhq.drivers.registry;

import com.germaniumhq.drivers.platform.OperatingSystem;
import com.germaniumhq.drivers.platform.Platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.HashSet;

public class InstallDriver {
    public static String installDriver(Platform platform, String browser) {
        String driverName = DriverRegistry.getDriverName(platform, browser);

        if (driverName == null) {
            throw new IllegalArgumentException(String.format(
                    "Unsupported Platform/Browser combination. '%s', " +
                    "browser: '%s'." ,
                    platform,
                    browser));
        }

        String driversFolderLocation = ConfigurableSettings.getGermaniumDriversFolder();

        File driversFolderFile = new File(driversFolderLocation);
        if (driversFolderFile.exists()) {
            if (!driversFolderFile.isDirectory()) {
                throw new IllegalStateException(String.format(
                        "The drivers folder %s, exists already but is not a file. " +
                        "Please specify a different location using the " +
                        "GERMANIUM_DRIVERS_FOLDER environment variable.",
                        driversFolderLocation
                ));
            }
        } else {
            if (!driversFolderFile.mkdirs()) {
                throw new IllegalStateException(String.format(
                        "Unable to create folder for drivers at %s. Please specify another " +
                        "location using the GERMANIUM_DRIVERS_FOLDER environment variable, " +
                        "check the permissions, and if the path to the drivers folder is " +
                        "correct.",
                        driversFolderLocation
                ));
            }
        }

        String fullPathToDriver = new File(driversFolderFile, driverName).getAbsolutePath();

        if (DriverRegistry.isDriverUpToDate(platform, browser, fullPathToDriver)) {
            return fullPathToDriver;
        }

        String internalDriverPath = DriverRegistry.getInternalDriverPath(platform, browser);
        try (InputStream inputStream = loadData(internalDriverPath)) {
            Files.copy(inputStream, Paths.get(fullPathToDriver), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Unable to copy driver for platform %s, browser %s, to %s.",
                    platform,
                    browser,
                    fullPathToDriver), e);
        }

        if (platform.getOperatingSystem() == OperatingSystem.LINUX ||
                platform.getOperatingSystem() == OperatingSystem.MAC) {
            try {
                Files.setPosixFilePermissions(Paths.get(fullPathToDriver),
                        new HashSet<PosixFilePermission>(Arrays.asList(
                                PosixFilePermission.OWNER_EXECUTE,
                                PosixFilePermission.OWNER_READ,
                                PosixFilePermission.GROUP_EXECUTE,
                                PosixFilePermission.GROUP_READ,
                                PosixFilePermission.OTHERS_EXECUTE,
                                PosixFilePermission.OTHERS_READ
                        )));
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format(
                        "Unable to set executable bit for %s.",
                        fullPathToDriver), e);
            }
        }

        return fullPathToDriver;
    }

    private static InputStream loadData(String pathOrUrl) {
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            validateLicenseAgreement();
            try {
                return new URL(pathOrUrl).openStream();
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to open URL: " + pathOrUrl, e);
            }
        }

        return InstallDriver.class.getResourceAsStream("/" + pathOrUrl);
    }

    private static void validateLicenseAgreement() {
        if (ConfigurableSettings.isMsEdgeLicenseAgreed()) {
            return;
        }

        throw new IllegalStateException(
                "In order to use Edge, you need to first read the EULA from " +
                "https://az813057.vo.msecnd.net/eulas/webdriver-eula.pdf . If " +
                "you agree with it, you can either: 1. export GERMANIUM_I_AGREE_TO_MS_EDGE_LICENSE " +
                "into the environment, or 2. call germaniumdrivers.i_agree_to_ms_edge_license(). " +
                "Afterwards Germanium will download the drivers for you automatically. By default the " +
                "download will be in a temporary file, but you can configure the location using the " +
                "GERMANIUM_DRIVERS_FOLDER environment variable."
        );
    }
}

