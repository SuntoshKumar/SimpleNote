package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.data.NoteDatabase;
import javafx.application.Application;

import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws SQLException {
        NoteDatabase database = new NoteDatabase();
        database.initializeDatabase();
        Application.launch(HelloApplication.class, args);
    }
}
