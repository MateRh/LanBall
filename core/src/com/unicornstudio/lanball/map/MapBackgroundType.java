package com.unicornstudio.lanball.map;

public enum MapBackgroundType {
    GRASS("grass.png");

    private final String texture;

    MapBackgroundType(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}
