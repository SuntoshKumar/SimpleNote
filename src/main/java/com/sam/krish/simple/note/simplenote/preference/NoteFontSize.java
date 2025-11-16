package com.sam.krish.simple.note.simplenote.preference;

public enum NoteFontSize {
    DEFAULT(14), SMALL(10), NORMAL(12), MEDIUM(16), LARGE(18), EXTRA_LARGE(22);

    int size;

    NoteFontSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    private int getFontSizeValue(NoteFontSize size) {
        return switch (size) {
            case SMALL -> 12;
            case NORMAL -> 14;
            case MEDIUM -> 18;
            case LARGE -> 22;
            case EXTRA_LARGE -> 26;
            default -> 14;
        };
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return this.ordinal();
    }

    public static NoteFontSize fromIndex(int index) {
        NoteFontSize[] values = NoteFontSize.values();
        if (index < 0 || index >= values.length) {
            return NoteFontSize.DEFAULT;
        }
        return values[index];
    }

    public static int getIndexByName(String name) {
        for (NoteFontSize size : NoteFontSize.values()) {
            if (size.name().equalsIgnoreCase(name)) {
                return size.ordinal();
            }
        }
        return NoteFontSize.DEFAULT.ordinal();
    }
}
