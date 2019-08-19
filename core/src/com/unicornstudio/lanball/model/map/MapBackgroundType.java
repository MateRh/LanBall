package com.unicornstudio.lanball.model.map;

public enum MapBackgroundType {
    GRASS_PATTERN_ONE("images/grass_pattern_1.png"),
    GRASS_STRIPES_PATTERN("images/grass_stripes.png"),
    GRASS_STRIPES_PATTERN_x2("images/grass_stripes_x2.png");

    private final String texture;

    MapBackgroundType(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }
}
