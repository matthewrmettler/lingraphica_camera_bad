package com.example.android.camera2basic;

import android.app.Activity;
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
     * @param a Actibity used to create file
     * @return A File object where the FileOutputStream can write the .jpeg to.
     */
    public static File createNewFile(Activity a) {
        //Set a new path file based on the current timestamp.
        //We keep in milliseconds in case we take multiple photos within a one second window.
        Long tsLong = System.currentTimeMillis();
        String file = tsLong.toString() + ".jpg";

        return new File(a.getExternalFilesDir(null), file);

    }

    public static File getPhotoDirectory(Activity a) {
        File folder = a.getExternalFilesDir(null);

        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Making bad_camera folder.");
        }

        return folder;
    }

    public static int getPhotoCount(Activity a) {
        return getPhotoDirectory(a).listFiles().length;
    }
}
