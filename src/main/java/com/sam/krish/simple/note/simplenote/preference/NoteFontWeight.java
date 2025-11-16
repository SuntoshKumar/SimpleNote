package com.sam.krish.simple.note.simplenote.preference;

public enum NoteFontWeight {
   REGULAR, BOLD, EXTRA_BOLD;

    public int getIndex() {
        return this.ordinal();
    }

    public static NoteFontWeight fromIndex(int index) {
        NoteFontWeight[] values = NoteFontWeight.values();
        if (index < 0 || index >= values.length) {
            return NoteFontWeight.REGULAR;
        }
        return values[index];
    }

    public static int getIndexByName(String name) {
        for (NoteFontWeight weight : NoteFontWeight.values()) {
            if (weight.name().equalsIgnoreCase(name)) {
                return weight.ordinal();
            }
        }
        return NoteFontWeight.REGULAR.ordinal();
    }

    private String getFontWeightValue(NoteFontWeight weight) {
        return switch (weight) {
            case BOLD -> "bold";
            case EXTRA_BOLD -> "900";
            default -> "normal";
        };
    }
}
