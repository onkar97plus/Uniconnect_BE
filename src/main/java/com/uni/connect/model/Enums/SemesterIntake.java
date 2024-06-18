package com.uni.connect.model.Enums;

public enum SemesterIntake {
    FALL("Fall"),
    SPRING("Spring"),
    SUMMER("Summer");

    private final String displayName;

    SemesterIntake(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
