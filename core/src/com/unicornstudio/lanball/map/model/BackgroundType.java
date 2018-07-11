package com.unicornstudio.lanball.map.model;

import com.badlogic.gdx.graphics.Color;

public enum BackgroundType {
    GRASS("#6e935c"),
    WORLD("#6d8e55");

    private final Color color;

    BackgroundType(String color) {
        this.color = Color.valueOf(color);
    }

    public Color getColor() {
        return color;
    }
}
