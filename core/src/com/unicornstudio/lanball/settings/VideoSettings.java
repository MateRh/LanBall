package com.unicornstudio.lanball.settings;

import com.badlogic.gdx.Gdx;

public class VideoSettings implements Settings {

    private int width = 1366;

    private int height = 768;

    private boolean fullScreen = false;


    @Override
    public void apply() {
        if (!fullScreen) {
            Gdx.graphics.setWindowedMode(width, height);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[Gdx.graphics.getDisplayModes().length-1]);
        }
    }
}
