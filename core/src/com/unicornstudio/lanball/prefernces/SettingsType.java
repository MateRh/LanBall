package com.unicornstudio.lanball.prefernces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public enum SettingsType {
    VIDEO(Gdx.app.getPreferences("Video settings")),
    CONTROL(Gdx.app.getPreferences("Control settings")),
    SERVER(Gdx.app.getPreferences("Server settings")),
    AUDIO(Gdx.app.getPreferences("Audio settings")),
    GLOABL(Gdx.app.getPreferences("Global settings"));

    private Preferences preference;

    SettingsType(Preferences preference) {
        this.preference = preference;
    }

    public Preferences getPreference() {
        return preference;
    }
}