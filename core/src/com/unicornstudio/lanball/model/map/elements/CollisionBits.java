package com.unicornstudio.lanball.model.map.elements;

public enum CollisionBits {
     BIT_PLAYER_TEAM1((short) 0x0001),
     BIT_PLAYER_TEAM2((short) 0x0002),
     BIT_PLAYER_TEAM1_AND_TEAM2((short) (0x0001 | 0x0002)),
     BIT_BALL((short)  0x0004),
     BIT_PLAYER_BOUND((short)  0x0008),
     BIT_BALL_BOUND((short) 0x0016);

    private short bit;

    CollisionBits(short bit) {
        this.bit = bit;
    }

    public short getBit() {
        return bit;
    }

}
