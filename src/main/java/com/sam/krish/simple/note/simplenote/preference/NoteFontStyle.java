package com.sam.krish.simple.note.simplenote.preference;

public enum NoteFontStyle {
    NORMAL, ITALIC;

    public int getIndex() {
        return this.ordinal();
    }

    public static NoteFontStyle fromIndex(int index) {
        NoteFontStyle[] values = NoteFontStyle.values();
        if (index < 0 || index >= values.length) {
            return NoteFontStyle.NORMAL;
        }
        return values[index];
    }

    public static int getIndexByName(String name) {
        for (NoteFontStyle style : NoteFontStyle.values()) {
            if (style.name().equalsIgnoreCase(name)) {
                return style.ordinal();
            }
        }
        return NoteFontStyle.NORMAL.ordinal();
    }
}
