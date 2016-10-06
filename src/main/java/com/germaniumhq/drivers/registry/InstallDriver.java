package com.germaniumhq.drivers.registry;

import com.germaniumhq.drivers.platform.OperatingSystem;
import com.germaniumhq.drivers.platform.Platform;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.HashSet;

public class InstallDriver {
    public static String installDriver(Platform platform, String browser) {
        String driverName = DriverRegistry.getDriverName(platform, browser);
        String internalDriverPath = DriverRegistry.getInternalDriverPath(platform, browser);
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

        try (InputStream inputStream = InstallDriver.class.getResourceAsStream("/" + internalDriverPath)) {
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
}
