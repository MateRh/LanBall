package com.unicornstudio.lanball.prefernces;

public class SettingsAdapter {

    public static String getString(SettingsType settingsType, String key) {
        return settingsType.getPreference().getString(key);
    }

    public static Boolean getBoolean(SettingsType settingsType, String key) {
        return settingsType.getPreference().getBoolean(key);
    }

    public static Integer getInteger(SettingsType settingsType, String key) {
        return settingsType.getPreference().getInteger(key);
    }

    public static Float getFloat(SettingsType settingsType, String key) {
        return settingsType.getPreference().getFloat(key);
    }

    public static void putString(SettingsType settingsType, String key, String value) {
        settingsType.getPreference().putString(key, value);
    }

    public static void putBoolean(SettingsType settingsType, String key, boolean value) {
        settingsType.getPreference().putBoolean(key, value);
    }

    public static void putInteger(SettingsType settingsType, String key, Integer value) {
        settingsType.getPreference().putInteger(key, value);
    }

    public static void putFloat(SettingsType settingsType, String key, Float value) {
        settingsType.getPreference().putFloat(key, value);
    }

}
