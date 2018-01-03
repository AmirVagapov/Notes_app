/*
package com.vagapov.amir.a2_l1_vagapov;


import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class SharedPrefsHelper {

    private static final String FILENAME = "NotePreferences";
    private static String path;


    private static String getPath(Context context) {
        return path = context.getFilesDir() + "/" + FILENAME;
    }

    static void refreshFile(ArrayList<Note> noteList) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        File file = new File(path);
        try {
            fileOutputStream = new FileOutputStream(file, false);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(noteList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static ArrayList readFromFile(Context context) {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        try {
            fileInputStream = new FileInputStream(getPath(context));
            objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Note> listNotes = (ArrayList<Note>) objectInputStream.readObject();
            return listNotes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Note>();
    }


}
*/
