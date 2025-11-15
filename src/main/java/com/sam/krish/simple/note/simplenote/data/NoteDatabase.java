package com.sam.krish.simple.note.simplenote.data;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class NoteDatabase implements NoteDatabaseWrapper {

    private final Statement statement;
    private final Connection connection;

    public NoteDatabase() throws SQLException {
       connection = DriverManager.getConnection("jdbc:h2:./data/simple_note.db");
       statement = connection.createStatement();
    }

    @Override
    public void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
        CREATE TABLE IF NOT EXISTS note (
            id INT AUTO_INCREMENT PRIMARY KEY,
            title TEXT,
            text TEXT,
            fontSize INT,
            fontWeight INT,
            fontStyle INT,
            textColor INT,
            backgroundColor INT,
            createdDate VARCHAR(255)
        )
    """);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database creation error!" + e.getMessage());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void insert(Note note) {
        String sql = "INSERT INTO note (text, createdDate) VALUES (?, CURRENT_TIMESTAMP)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getText());
            pstmt.setInt(3, note.getFontSize());
            pstmt.setInt(4, note.getFontWeight());
            pstmt.setInt(5, note.getFontStyle());
            pstmt.setInt(6, note.getTextColor());
            pstmt.setInt(7, note.getBackgroundColor());
            pstmt.setString(8, note.getCreatedDate());
            pstmt.executeUpdate();
            System.out.println("Note inserted successfully!");
            loadNote();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database insertion error!" + e.getMessage());
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void update(Note note) {

    }

    @Override
    public void delete(Note note) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public ObservableList<Note> getAllNoteList() {
        return null;
    }

    @Override
    public void loadNote() {

    }
}
