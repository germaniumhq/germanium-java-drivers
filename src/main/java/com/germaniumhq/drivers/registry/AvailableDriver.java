package com.germaniumhq.drivers.registry;

import com.germaniumhq.drivers.platform.Platform;

import java.io.File;

public class AvailableDriver {
    private final String binaryPath;
    private final String sha1Hash;

    public AvailableDriver(String binaryPath, String sha1Hash) {
        this.binaryPath = binaryPath;
        this.sha1Hash = sha1Hash;
    }

    /* Scans the path if an available driver exists for the given
     * platform and browser. If it doesn't will return null. If it
     * does, it will return an available driver that points to the
     * path of the driver.
     * @param platform
     * @param browser
     * @return
     */
    public static AvailableDriver getAvailableDriverFromPath(Platform platform, String browser) {
        String searchedName = DriverRegistry.getDriverName(platform, browser);

        if (searchedName == null) {
            return null;
        }

        String[] pathFolders = getPathFolders();
        for (String folder: pathFolders) {
            AvailableDriver result = getAvailableDriverFromFolder(folder, searchedName);

            if (result != null) {
                return result;
            }
        }

        return null;
    }


    private static AvailableDriver getAvailableDriverFromFolder(String folder, String searchedName) {
        if (isFolderContaining(folder, searchedName)) {
            String binaryPath = new File(folder, searchedName).getAbsolutePath();
            return new AvailableDriver(binaryPath, ShaHash.shaFile(binaryPath));
        }

        return null;
    }

    public static String[] getPathFolders() {
        return System.getenv("PATH").split(File.pathSeparator);
    }

    private static boolean isFolderContaining(String folderName, String searchedName) {
        File folder = new File(folderName);

        if (! folder.exists() || !folder.isDirectory()) {
            return false;
        }

        File searchedFile = new File(folder, searchedName);

        return searchedFile.exists() && searchedFile.isFile();
    }


    public String getBinaryPath() {
        return binaryPath;
    }

    public String getSha1Hash() {
        return sha1Hash;
    }
}
