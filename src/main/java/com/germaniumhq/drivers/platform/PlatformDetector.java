package com.germaniumhq.drivers.platform;

public class PlatformDetector {
    public static Platform detectPlatform() {
        BitArchitecture bitArchitecture = BitArchitecture.BIT_32;

        if (System.getProperty("os.name").contains("Windows")) {
            if (System.getenv("ProgramFiles(x86)") != null) {
                bitArchitecture = BitArchitecture.BIT_64;
            }
        } else {
            if (System.getProperty("os.arch").contains("64")) {
                bitArchitecture = BitArchitecture.BIT_64;
            }
        }

        String operatingSystemName = System.getProperty("os.name");

        OperatingSystem operatingSystem = null;

        if (operatingSystemName.contains("Linux") || operatingSystemName.contains("LINUX")) {
            operatingSystem = OperatingSystem.LINUX;
        }

        if (operatingSystemName.contains("Mac") || operatingSystemName.contains("Mac OS X")) {
            operatingSystem = OperatingSystem.MAC;
        }

        if (operatingSystemName.contains("Windows")) {
            operatingSystem = OperatingSystem.WINDOWS;
        }

        if (operatingSystem == null) {
            throw new IllegalArgumentException(String.format(
                    "The current platform is %s. Unfortunately only " +
                    "Windows, Linux and Mac are supported.", operatingSystemName)
            );
        }

        return new Platform(operatingSystem, bitArchitecture);
    }
}
