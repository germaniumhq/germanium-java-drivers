package com.germaniumhq;

import com.germaniumhq.driver.AvailableDriver;
import com.germaniumhq.driver.ConfigurableSettings;
import com.germaniumhq.driver.DriverRegistry;
import com.germaniumhq.driver.InstallDriver;
import com.germaniumhq.drivers.platform.Platform;
import com.germaniumhq.drivers.platform.PlatformDetector;
import org.apache.log4j.Logger;

public class EnsureDriver {
    private final static org.apache.log4j.Logger log = Logger.getLogger(EnsureDriver.class);

    /**
     * Returns the path to the driver if the driver exists.
     * @param browser
     * @return
     */
    public static String ensureDriver(String browser) {
        Platform currentPlatform = PlatformDetector.detectPlatform();

        AvailableDriver foundDriver = AvailableDriver.getAvailableDriverFromPath(currentPlatform, browser);

        if (foundDriver == null) {
            return InstallDriver.installDriver(currentPlatform, browser);
        }

        if (DriverRegistry.isDriverUpToDate(currentPlatform, browser, foundDriver)) {
            return foundDriver.getBinaryPath();
        }

        log.warn(String.format(
                "Germanium found a driver at `%s`. Unfortunately this is not matching " +
                "the current version embedded in Germanium, so Germanium will use the " +
                "embedded one. If you want to force Germanium to use the driver from " +
                "the PATH, then you can set the GERMANIUM_USE_PATH_DRIVER environment " +
                "variable. Note that Germanium support is only offered for the embedded " +
                "drivers.", foundDriver.getBinaryPath()));

        if (ConfigurableSettings.isGermaniumUsePathDriverSet()) {
            return foundDriver.getBinaryPath();
        }

        return InstallDriver.installDriver(currentPlatform, browser);
    }
}
