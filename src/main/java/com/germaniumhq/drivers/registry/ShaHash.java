package com.germaniumhq.drivers.registry;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;

public class ShaHash {
    public static String shaFile(String path) {
        try {
            InputStream fileInputStream = new FileInputStream(path);
            return shaInputStream(fileInputStream);
        } catch (FileNotFoundException e) {
            return "MISSING_FILE";
        }
    }

    public static String shaClassPath(String path) {
        InputStream inputStream = ShaHash.class.getResourceAsStream("/" + path);
        return shaInputStream(inputStream);
    }

    private static String shaInputStream(InputStream inputStream) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = inputStream.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }

            return new HexBinaryAdapter().marshal(digest.digest());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to compute hash for input stream.", e);
        }
    }
}
