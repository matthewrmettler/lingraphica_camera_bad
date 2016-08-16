package com.example.android.camera2basic;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Matt on 8/16/2016.
 */



public class Helper {
    private static final String TAG = "Helper";

    /**
     * Creates a new File to be used by the picture taken, in the DCIM/bad_camera directory.
     * @param timestamp Time since epoch, used for the filename in order to distinguish pictures.
     * @return A File object where the FileOutputStream can write the .jpeg to.
     */
    public static File createNewFile(String timestamp) {
        File picFile = new File(getPhotoDirectory().getAbsolutePath(), timestamp);

        return picFile;
    }

    public static File getPhotoDirectory() {
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/bad_camera");

        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Making bad_camera folder.");
        }

        return folder;
    }

    public static int getPhotoCount() {
        return getPhotoDirectory().listFiles().length;
    }
}
