package com.unicornstudio.lanball.audio;

public enum SoundType {
    KICK("sounds/kick.wav");

    private String path;

    SoundType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
