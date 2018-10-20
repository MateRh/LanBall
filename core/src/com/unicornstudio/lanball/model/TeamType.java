package com.unicornstudio.lanball.model;

public enum TeamType {
    SPECTATORS(0),
    TEAM1(1),
    TEAM2(2);

    private final int type;

    TeamType(int type) {
        this.type = type;
    }
}
