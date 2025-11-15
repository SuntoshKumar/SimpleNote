package com.sam.krish.simple.note.simplenote.data;

import javafx.collections.ObservableList;

public interface NoteDatabaseWrapper {

    public void initializeDatabase();

    void insert(Note note);
    void update(Note note);
    void delete(Note note);

    void deleteAll();
    ObservableList<Note> getAllNoteList();

    void loadNote();


}
