package com.example.emmalady.rikkeiandroidlesson5.db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Liz Nguyen on 21/10/2017.
 */

public class ConnectDatabase {
    public static SQLiteDatabase sqLiteDatabase(Activity activity, String databaseName) {
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
            File f = new File(outFileName);
            if (!f.exists()) {
                InputStream e = activity.getAssets().open(databaseName);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(outFileName);
                byte[] buffer = new byte[2048];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }
}
