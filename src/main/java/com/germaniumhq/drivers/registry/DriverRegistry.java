package com.germaniumhq.drivers.registry;

import com.germaniumhq.drivers.platform.BitArchitecture;
import com.germaniumhq.drivers.platform.OperatingSystem;
import com.germaniumhq.drivers.platform.Platform;

public class DriverRegistry {
    @SuppressWarnings("Duplicates") // this is because the architecture LINUX and MAC are similar
    public static String getDriverName(Platform platform, String browser) {
        if (platform.getOperatingSystem() == OperatingSystem.LINUX) {
            if ("chrome".equalsIgnoreCase(browser)) {
                return "chromedriver";
            } else if ("firefox".equalsIgnoreCase(browser)) {
                return "wires";
            } else {
                return null;
            }
        } else if (platform.getOperatingSystem() == OperatingSystem.MAC) {
            if ("chrome".equalsIgnoreCase(browser)) {
                return "chromedriver";
            } else if ("firefox".equalsIgnoreCase(browser)) {
                return "wires";
            } else {
                return null;
            }
        } else if (platform.getOperatingSystem() == OperatingSystem.WINDOWS) {
            if ("chrome".equalsIgnoreCase(browser)) {
                return "chromedriver.exe";
            } else if ("firefox".equalsIgnoreCase(browser)) {
                return "wires.exe";
            } else if ("ie".equalsIgnoreCase(browser)) {
                return "IEDriverServer.exe";
            } else if ("edge".equalsIgnoreCase(browser)) {
                return "MicrosoftWebDriver.exe";
            } else {
                return null;
            }
        }

        return null;
    }

    public static String getInternalDriverPath(Platform platform, String browser) {
        if ("chrome".equalsIgnoreCase(browser)) {
            if (platform.getOperatingSystem() == OperatingSystem.LINUX) {
                if (platform.getBitArchitecture() == BitArchitecture.BIT_32) {
                    return "binary/chrome/linux/32/chromedriver";
                } else if (platform.getBitArchitecture() == BitArchitecture.BIT_64) {
                    return "binary/chrome/linux/64/chromedriver";
                } else {
                    return raiseUnknownBrowser(platform, browser);
                }
            } else if (platform.getOperatingSystem() == OperatingSystem.MAC) {
                return "binary/chrome/mac/32/chromedriver";
            } else if (platform.getOperatingSystem() == OperatingSystem.WINDOWS) {
                return "binary/chrome/win/32/chromedriver.exe";
            } else {
                return raiseUnknownBrowser(platform, browser);
            }
        } else if ("firefox".equalsIgnoreCase(browser)) {
            if (platform.getOperatingSystem() == OperatingSystem.LINUX) {
                return "binary/firefox/linux/64/geckodriver";
            } else if (platform.getOperatingSystem() == OperatingSystem.MAC) {
                return "binary/firefox/mac/32/geckodriver";
            } else if (platform.getOperatingSystem() == OperatingSystem.WINDOWS) {
                return "binary/firefox/win/64/geckodriver.exe";
            } else {
                return raiseUnknownBrowser(platform, browser);
            }
        } else if ("ie".equalsIgnoreCase(browser)) {
            if (platform.getOperatingSystem() == OperatingSystem.WINDOWS) {
                if (!ConfigurableSettings.isGermaniumUseIeDriverForPlatform()) {
                    return "binary/ie/win/32/IEDriverServer.exe";
                } else if (platform.getBitArchitecture() == BitArchitecture.BIT_32) {
                    return "binary/ie/win/32/IEDriverServer.exe";
                } else if (platform.getBitArchitecture() == BitArchitecture.BIT_64) {
                    return "binary/ie/win/64/IEDriverServer.exe";
                }
            }
        } else if ("edge".equalsIgnoreCase(browser)) {
            return "https://download.microsoft.com/download/3/2/D/32D3E464-F2EF-490F-841B-05D53C848D15/MicrosoftWebDriver.exe";
        }

        return raiseUnknownBrowser(platform, browser);
    }

    public static boolean isDriverUpToDate(Platform platform, String browser, AvailableDriver availableDriver) {
        String internalDriverSum = getInternalDriverSha1(platform, browser);

        return internalDriverSum.equals(availableDriver.getSha1Hash());
    }

    public static boolean isDriverUpToDate(Platform platform, String browser, String driverPath) {
        String availableDriverSum = ShaHash.shaFile(driverPath);
        String internalDriverSum = getInternalDriverSha1(platform, browser);

        return availableDriverSum.equals(internalDriverSum);
    }

    private static String getInternalDriverSha1(Platform platform, String browser) {
        if ("edge".equalsIgnoreCase(browser)) {
            return "6f9e81e5f60fa3e8dccba15a3715ba20d44d0775";
        }

        String internalDriverPath = getInternalDriverPath(platform, browser);
        return ShaHash.shaClassPath(internalDriverPath);
    }

    private static String raiseUnknownBrowser(Platform platform, String browser) {
        String message = String.format(
                "Unable to find driver for %s on %s bits, for browser %s.",
                platform.getOperatingSystem(),
                platform.getBitArchitecture(),
                browser);

        throw new IllegalStateException(message);
    }

}

