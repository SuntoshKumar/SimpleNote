package com.sam.krish.simple.note.simplenote.preference;

import javafx.scene.paint.Color;

public enum NoteColor {

    DEFAULT("#000000", "#FFFFFF"),
    RED("D32F2F","FFEBEE"),
    PINK("C2185B","FCE4EC"),
    PURPLE("7B1FA2","F3E5F5"),
    BLUE("1976D2","E3F2FD"),
    GREEN("388E3C","E8F5E9"),
    YELLOW("FBC02D","FFFDE7"),
    ORANGE("F57C00","FFF3E0"),
    GREY("455A64","ECEFF1");

    private final String textHex;
    private final String backgroundHex;

    NoteColor(String textHex, String backgroundHex) {
        this.textHex = textHex;
        this.backgroundHex = backgroundHex;
    }

    public Color getTextColor() {
        return Color.web("#" + textHex);
    }

    public Color getBackgroundColor() {
        return Color.web("#" + backgroundHex);
    }

    public String getTextHex() {
        return textHex;
    }

    public String getBackgroundHex() {
        return backgroundHex;
    }

    public int getIndex() {
        return this.ordinal();
    }

    public static NoteColor fromIndex(int index) {
        NoteColor[] values = NoteColor.values();
        if (index < 0 || index >= values.length) {
            return NoteColor.DEFAULT;
        }
        return values[index];
    }

    public static int getIndexByName(String name) {
        for (NoteColor color : NoteColor.values()) {
            if (color.name().equalsIgnoreCase(name)) {
                return color.ordinal();
            }
        }
        return NoteColor.DEFAULT.ordinal();
    }
}
