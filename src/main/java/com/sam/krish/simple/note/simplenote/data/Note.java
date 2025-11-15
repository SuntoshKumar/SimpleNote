package com.sam.krish.simple.note.simplenote.data;

public class Note {
    private int id;
    private String title;
    private String text;
    private String createdDate;
    private int fontSize;
    private int fontWeight;
    private int fontStyle;
    private int textColor;
    private int backgroundColor;

    public Note() {
    }

    public Note(int id, String title, String text, String createdDate) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdDate = createdDate;
    }

    public Note(int id, String title, String text, String createdDate, int fontSize, int fontWeight, int fontStyle, int textColor, int backgroundColor) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdDate = createdDate;
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(int fontWeight) {
        this.fontWeight = fontWeight;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
