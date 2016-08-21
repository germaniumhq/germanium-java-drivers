package com.germaniumhq.driver;

import java.io.File;

public class ConfigurableSettings {
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
}
