package org.example.helpers;

import java.io.File;

public class FileHelper {
    public static void deleteFolder(String folder) {
        File dir = new File(folder);
        if (dir.exists() && dir.isDirectory()) {
            try {
                for (File file : dir.listFiles()) {
                    file.delete();
                }
                dir.delete();
            } catch (Exception ignored) {
            }
        }
    }
}
