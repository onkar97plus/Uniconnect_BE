package com.uni.connect.model.Enums;

public enum Ethnicity {
    PUNJABI("Punjabi"),
    GUJARATI("Gujarati"),
    HARYANVI("Haryanvi"),
    MARWADI("Marwadi"),
    TELUGU("Telugu"),
    OTHER("Other");

    private final String displayName;

    Ethnicity(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
