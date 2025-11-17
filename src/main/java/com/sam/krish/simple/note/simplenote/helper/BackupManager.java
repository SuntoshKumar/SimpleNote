package com.sam.krish.simple.note.simplenote.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sam.krish.simple.note.simplenote.data.Note;
import com.sam.krish.simple.note.simplenote.data.NoteDatabase;

import java.io.*;
        import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BackupManager {

    private final NoteDatabase database;
    private final Gson gson = new Gson();

    public BackupManager() throws Exception {
        database = new NoteDatabase();
    }

    /** CREATE BACKUP FILE */
    public boolean backupToFile(File file) {
        try {
            List<Note> notes = database.getAllNoteList();

            String json = gson.toJson(notes);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(json);
            }

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
    }

    /** RESTORE BACKUP FROM FILE */
    public boolean restoreFromFile(File file) {
        try {
            if (!file.exists()) return false;

            StringBuilder builder = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }

            Type listType = new TypeToken<ArrayList<Note>>() {}.getType();
            List<Note> notes = gson.fromJson(builder.toString(), listType);

            database.deleteAll();      // Remove old data
            database.insertAll(notes);     // Insert restored data

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}