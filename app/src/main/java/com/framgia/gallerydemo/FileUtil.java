package com.framgia.gallerydemo;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {

    private String[] mExtensions;
    private ArrayList<String> mListPaths;

    public FileUtil(String[] extensions) {
        this.mExtensions = extensions;
        mListPaths = new ArrayList<>();
    }

    public void loadAllImages(String rootFolder) {
        File file = new File(rootFolder);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        loadAllImages(f.getAbsolutePath());
                    } else {
                        for (String extension : mExtensions) {
                            if (f.getAbsolutePath().endsWith(extension)) {
                                mListPaths.add(f.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<String> getListPaths() {
        return mListPaths;
    }
}
