package com.uni.connect.model.Enums;

public enum RoomPreference {
    PRIVATE("Private"),
    SHARED("Shared");

    private final String displayName;

    RoomPreference(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
