package com.unicornstudio.lanball.prefernces;

import com.badlogic.gdx.Gdx;

public class VideoSettings {

    private int width = 1392;

    private int height = 783;

    private boolean fullScreen = false;

    public void apply() {
        if (!fullScreen) {
            Gdx.graphics.setWindowedMode(width, height);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[Gdx.graphics.getDisplayModes().length - 1]);
        }
    }
}
