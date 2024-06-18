package com.uni.connect.model.Enums;

public enum Religion {
    CHRISTIANITY("Christianity"),
    ISLAM("Islam"),
    JUDAISM("Judaism"),
    HINDUISM("Hinduism"),
    BUDDHISM("Buddhism"),
    SIKHISM("Sikhism"),
    OTHER("Other");

    private final String displayName;

    Religion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
