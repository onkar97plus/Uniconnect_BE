package com.uni.connect.model.Enums;

import lombok.Getter;

@Getter
public enum RoommateSearchStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String displayName;

    RoommateSearchStatus(String displayName) {
        this.displayName = displayName;
    }

}
