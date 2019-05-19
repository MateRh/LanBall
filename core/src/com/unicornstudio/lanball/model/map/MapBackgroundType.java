package com.unicornstudio.lanball.model.map;

public enum MapBackgroundType {
    GRASS_PATTERN_ONE("grass_pattern_1.png");

    private final String texture;

    MapBackgroundType(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}
