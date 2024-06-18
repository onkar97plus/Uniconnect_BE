package com.uni.connect.model.Enums;

public enum EatingPreference {
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    NONVEGETARIAN("Non vegetarian"),
    EGGETARIAN("Eggetarian"),
    OTHER("Other");

    private final String displayName;

    EatingPreference(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
