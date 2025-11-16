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

    void updateTitle(int id, String title);
    void updateText(int id, String text);
    void updateFontSize(int id, int fontSize);
    void updateFontWeight(int id, int fontWeight);
    void updateFontStyle(int id, int fontStyle);
    void updateTextColor(int id, int textColor);
    void updateBackgroundColor(int id, int backgroundColor);
    void updateCreatedDate(int id, String createdDate);

}
