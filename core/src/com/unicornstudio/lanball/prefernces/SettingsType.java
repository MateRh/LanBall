package com.unicornstudio.lanball.prefernces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public enum SettingsType {
    VIDEO(Gdx.app.getPreferences("videoSettings")),
    CONTROL(Gdx.app.getPreferences("controlSettings")),
    SERVER(Gdx.app.getPreferences("serverSettings")),
    AUDIO(Gdx.app.getPreferences("audioSettings")),
    GLOABL(Gdx.app.getPreferences("globalSettings"));

    private Preferences preference;

    SettingsType(Preferences preference) {
        this.preference = preference;
    }

    public Preferences getPreference() {
        return preference;
    }
}