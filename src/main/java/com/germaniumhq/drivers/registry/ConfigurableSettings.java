package com.germaniumhq.drivers.registry;

import java.io.File;

public class ConfigurableSettings {
    private static boolean MS_EDGE_LICENSE_AGREED;

    public static boolean isGermaniumUseIeDriverForPlatform() {
        return System.getenv("GERMANIUM_USE_IE_DRIVER_FOR_PLATFORM") != null;
    }

    public static boolean isGermaniumUsePathDriverSet() {
        return System.getenv("GERMANIUM_USE_PATH_DRIVER") != null;
    }

    public static String getGermaniumDriversFolder() {
        String germaniumDriversFolder = System.getenv("GERMANIUM_DRIVERS_FOLDER");

        if (germaniumDriversFolder == null) {
            return new File(System.getProperty("java.io.tmpdir"), "germanium-drivers")
                    .getAbsolutePath();
        }

        return germaniumDriversFolder;
    }

    public static boolean isMsEdgeLicenseAgreed() {
        return MS_EDGE_LICENSE_AGREED ||
               System.getenv("GERMANIUM_I_AGREE_TO_MS_EDGE_LICENSE") != null;
    }

    public static void iAgreeToMsEdgeLicense() {
        MS_EDGE_LICENSE_AGREED = true;
    }
}
