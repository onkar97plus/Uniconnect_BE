package com.uni.connect.model.Enums;

public enum RoommateSearchStatus {
    ACTIVE("Active"),
    IMMEDIATE("Immediate"),
    CLOSED("Closed");

    private final String displayName;

    RoommateSearchStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
