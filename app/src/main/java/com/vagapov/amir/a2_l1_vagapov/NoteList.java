package com.vagapov.amir.a2_l1_vagapov;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vagapov.amir.a2_l1_vagapov.database.DataBaseDescription;
import com.vagapov.amir.a2_l1_vagapov.database.DataBaseOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class NoteList implements DataBaseDescription {

    private static NoteList mNoteList;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static NoteList getNoteList(Context context) {
        if (mNoteList == null) {
            mNoteList = new NoteList(context);
        }
        return mNoteList;
    }

    private void setNotes(ArrayList<Note> notes){
        mDatabase.delete(TABLE_NAME, null, null);
        for (Note note: notes) {
            addNote(note);
        }
    }

    private NoteList(Context context) {
        mContext = context;
        mDatabase = new DataBaseOpenHelper(mContext).getWritableDatabase();
    }

    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = mDatabase.query(TABLE_NAME, null, null,
                null, null, null, null);
        try {
            while (cursor != null && cursor.moveToNext()) {
                notes.add(getNoteFromCursor(cursor));
            }
        } finally {
            cursor.close();
        }

        return notes;
    }

    public void moveNote(int fromPosition, int toPosition) {
        ArrayList<Note> notes = (ArrayList<Note>) getNotes();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(notes, i, i + 1);
            }
        } else {
            for (int i = toPosition; i < fromPosition; i++) {
                Collections.swap(notes, i, i + 1);
            }
        }
        setNotes(notes);
    }

    void dataBaseSwap(Note from, Note to) {
        ContentValues values = new ContentValues();
        putInValues(from, values);
        mDatabase.update(TABLE_NAME, values, COLUMN_UUID + " = ?",
                new String[]{String.valueOf(to.getUUID())});
        ContentValues values1 = new ContentValues();
        putInValues(to, values1);
        mDatabase.update(TABLE_NAME, values1, COLUMN_UUID + " = ?",
                new String[]{String.valueOf(from.getUUID())});
    }

    void updateNote(Note note) {
        ContentValues values = new ContentValues();
        putInValues(note, values);
        mDatabase.update(TABLE_NAME, values, COLUMN_UUID + " = ?",
                new String[]{String.valueOf(note.getUUID())});
    }

    private Note getNoteFromCursor(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
        String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
        String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_UUID));

        Note note = Note.createNote(UUID.fromString(id));
        note.setTitle(title);
        note.setAddress(address);
        note.setDescription(description);
        return note;
    }

    void addNote(Note note) {
        ContentValues values = new ContentValues();
        putInValues(note, values);
        mDatabase.insert(TABLE_NAME, null, values);
    }

    private void putInValues(Note note, ContentValues values) {
        values.put(COLUMN_UUID, String.valueOf(note.getUUID()));
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_ADDRESS, note.getAddress());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
    }

    public void deleteNote(UUID id) {
        mDatabase.delete(TABLE_NAME, COLUMN_UUID + " = ?",
                new String[]{String.valueOf(id)});
    }

    Note getNote(UUID id) {
        Cursor cursor = mDatabase.query(TABLE_NAME, null, COLUMN_UUID + " = ?",
                new String[]{String.valueOf(id)}, null,
                null, null);
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return getNoteFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
}
