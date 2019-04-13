package com.unicornstudio.lanball.prefernces;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.unicornstudio.lanball.rest.NickNameGeneratorClient;

import static com.unicornstudio.lanball.prefernces.SettingsKeys.*;

public class DefaultSettings {

    private static final float CURRENT_SETTINGS_VERSION = 0.01f;
    private static final int DEFAULT_WIDTH = 1454;
    private static final int DEFAULT_HEIGHT = 1000;
    private static final boolean DEFAULT_FULL_SCREEN = false;
    private static final int DEFAULT_TIME_LIMIT = 4;
    private static final int DEFAULT_SCORE_LIMIT = 4;

    public static void generate() {
        Preferences globalPreferences = SettingsType.GLOABL.getPreference();
        if (!globalPreferences.contains(SETTINGS_VERSION) ||
                (globalPreferences.contains(SETTINGS_VERSION) && globalPreferences.getFloat(SETTINGS_VERSION) < CURRENT_SETTINGS_VERSION)) {
            generateGlobalSettings();
            generateVideoSettings();
            generateControlSettings();
            generateServerSettings();
        }
    }

    private static void generateGlobalSettings() {
        Preferences preferences = SettingsType.GLOABL.getPreference();
        preferences.putFloat(SETTINGS_VERSION, CURRENT_SETTINGS_VERSION);
        putString(preferences, NICKNAME, new NickNameGeneratorClient().getNickName());
        preferences.flush();
    }

    private static void generateVideoSettings() {
        Preferences preferences = SettingsType.VIDEO.getPreference();
        putInteger(preferences, WIDTH, DEFAULT_WIDTH);
        putInteger(preferences, HEIGHT, DEFAULT_HEIGHT);
        putBoolean(preferences, FULL_SCREEN, DEFAULT_FULL_SCREEN);
        preferences.flush();
    }

    private static void generateControlSettings() {
        Preferences preferences = SettingsType.CONTROL.getPreference();
        putInteger(preferences, UP_CONTROL, Input.Keys.W);
        putInteger(preferences, UP_CONTROL_ALTERNATIVE, Input.Keys.UP);
        putInteger(preferences, DOWN_CONTROL, Input.Keys.S);
        putInteger(preferences, DOWN_CONTROL_ALTERNATIVE, Input.Keys.DOWN);
        putInteger(preferences, LEFT_CONTROL, Input.Keys.A);
        putInteger(preferences, LEFT_CONTROL_ALTERNATIVE, Input.Keys.LEFT);
        putInteger(preferences, RIGHT_CONTROL, Input.Keys.D);
        putInteger(preferences, RIGHT_CONTROL_ALTERNATIVE, Input.Keys.RIGHT);
        putInteger(preferences, SHOT_CONTROL, Input.Keys.SPACE);
        putInteger(preferences, SHOT_CONTROL_ALTERNATIVE, Input.Keys.CONTROL_RIGHT);
        preferences.flush();
    }

    private static void generateServerSettings() {
        Preferences preferences = SettingsType.SERVER.getPreference();
        putInteger(preferences, TIME_LIMIT, DEFAULT_TIME_LIMIT);
        putInteger(preferences, SCORE_LIMIT, DEFAULT_SCORE_LIMIT);
        preferences.flush();
    }

    private static void putString(Preferences preferences, String key, String defaultValue) {
        preferences.putString(key, preferences.getString(key, defaultValue));
    }

    private static void putBoolean(Preferences preferences, String key, boolean defaultValue) {
        preferences.putBoolean(key, preferences.getBoolean(key, defaultValue));
    }

    private static void putInteger(Preferences preferences, String key, Integer defaultValue) {
        preferences.putInteger(key, preferences.getInteger(key, defaultValue));
    }

    private static void putFloat(Preferences preferences, String key, Float defaultValue) {
        preferences.putFloat(key, preferences.getFloat(key, defaultValue));
    }

}
