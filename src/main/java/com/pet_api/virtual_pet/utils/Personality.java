package com.pet_api.virtual_pet.utils;

import lombok.Getter;

@Getter
public enum Personality {
    LAZY("Lazy"),
    NORMAL("Normal"),
    SMUG("Smug"),
    PEPPY("Peppy"),
    JOCK("Jock"),
    CRANKY("Cranky"),
    SNOOTY("Snooty");

    private final String personalityName;

    Personality(String personalityName) {
        this.personalityName = personalityName;
    }
}
