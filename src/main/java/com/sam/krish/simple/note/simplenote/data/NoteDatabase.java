package com.sam.krish.simple.note.simplenote.data;

import com.sam.krish.simple.note.simplenote.helper.AlertDialogHelper;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;

public class NoteDatabase implements NoteDatabaseWrapper {

    //private final Statement statement;
    private final Connection connection;

    public NoteDatabase() throws SQLException{
        connection = DriverManager.getConnection("jdbc:h2:./data/simple_note.db");

        //statement = connection.createStatement();
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
            AlertDialogHelper.ErrorDialog("Database creation error!" + e.getMessage());
        }
    }

    @Override
    public void insert(Note note) {
        String sql = "INSERT INTO note (title, text, fontSize, fontWeight, fontStyle, textColor, backgroundColor, createdDate) VALUES (?,?,?,?,?,?,?,?)";

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

            AlertDialogHelper.ErrorDialog("Database insertion error!" + e.getMessage());

        }
    }

    @Override
    public void update(Note note) {
        String sql = "UPDATE note SET title=?, text=?, fontSize=?, fontWeight=?, fontStyle=?, textColor=?, backgroundColor=?, createdDate=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getText());
            pstmt.setInt(3, note.getFontSize());
            pstmt.setInt(4, note.getFontWeight());
            pstmt.setInt(5, note.getFontStyle());
            pstmt.setInt(6, note.getTextColor());
            pstmt.setInt(7, note.getBackgroundColor());
            pstmt.setString(8, note.getCreatedDate());
            pstmt.setInt(9, note.getId());
            pstmt.executeUpdate();
            loadNote();
        } catch (SQLException e) {
            AlertDialogHelper.ErrorDialog("Database update error!" + e.getMessage());

        }
    }

    @Override
    public void delete(Note note) {
        String sql = "DELETE FROM note WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, note.getId());
            pstmt.executeUpdate();
            loadNote();
        } catch (SQLException e) {
            AlertDialogHelper.ErrorDialog("Database delete error!" + e.getMessage());

        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM note";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            loadNote();
        } catch (SQLException e) {
            AlertDialogHelper.ErrorDialog("Database deleteAll error!" + e.getMessage());

        }
    }

    @Override
    public ObservableList<Note> getAllNoteList() {
        ObservableList<Note> list = javafx.collections.FXCollections.observableArrayList();
        String sql = "SELECT * FROM note";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Note note = new Note(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getInt("fontSize"),
                        rs.getInt("fontWeight"),
                        rs.getInt("fontStyle"),
                        rs.getInt("textColor"),
                        rs.getInt("backgroundColor"),
                        rs.getString("createdDate")
                );
                list.add(note);
            }
        } catch (SQLException e) {
            AlertDialogHelper.ErrorDialog("Database load list error!" + e.getMessage());

        }
        return list;
    }

    @Override
    public void loadNote() {
        getAllNoteList();
    }

    @Override
    public void updateTitle(int id, String title) {
        {
            String sql = "UPDATE note SET title=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update title error!" + e.getMessage());

            }
        }
    }

    @Override
    public void updateText(int id, String text) {
        {
            String sql = "UPDATE note SET text=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, text);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update text error!" + e.getMessage());

            }
        }
    }

    @Override
    public void updateFontSize(int id, int fontSize) {
        {
            String sql = "UPDATE note SET fontSize=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, fontSize);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update font size error!" + e.getMessage());

            }
        }
    }

    @Override
    public void updateFontWeight(int id, int fontWeight) {
        {
            String sql = "UPDATE note SET fontWeight=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, fontWeight);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update font weight error!" + e.getMessage());

            }
        }
    }

    @Override
    public void updateFontStyle(int id, int fontStyle) {
        {
            String sql = "UPDATE note SET fontStyle=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, fontStyle);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update font style error!" + e.getMessage());

            }
        }
    }

    @Override
    public void updateTextColor(int id, int textColor) {
        {
            String sql = "UPDATE note SET textColor=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, textColor);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update text color error!" + e.getMessage());

            }
        }
    }

    @Override
    public void updateBackgroundColor(int id, int backgroundColor) {
        {
            String sql = "UPDATE note SET backgroundColor=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, backgroundColor);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update background color error!" + e.getMessage());

            }
        }
    }

    @Override
    public void updateCreatedDate(int id, String createdDate) {
        {
            String sql = "UPDATE note SET createdDate=? WHERE id=?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, createdDate);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                loadNote();
            } catch (SQLException e) {
                AlertDialogHelper.ErrorDialog("Update created date error!" + e.getMessage());
            }
        }
    }
}
