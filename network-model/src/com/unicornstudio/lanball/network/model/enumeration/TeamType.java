package com.unicornstudio.lanball.network.model.enumeration;

public enum TeamType {
    SPECTATORS(0),
    TEAM1(1),
    TEAM2(2);

    private final int type;

    TeamType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
