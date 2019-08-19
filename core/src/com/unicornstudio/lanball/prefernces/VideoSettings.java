package com.unicornstudio.lanball.prefernces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;
import java.util.stream.Stream;
import com.badlogic.gdx.Graphics.DisplayMode;
import org.apache.commons.lang3.StringUtils;

public class VideoSettings {

    private int width = 1392;

    private int height = 783;

    public void apply() {
        if (SettingsType.VIDEO.getPreference().contains(SettingsKeys.DISPLAY_MODE)) {
            apply(SettingsAdapter.getString(SettingsType.VIDEO, SettingsKeys.DISPLAY_MODE), SettingsAdapter.getBoolean(SettingsType.VIDEO, SettingsKeys.FULL_SCREEN));
        } else {
            Gdx.graphics.setWindowedMode(width, height);
        }
    }

    public void apply(String displayModeString, boolean fullScreen) {
        DisplayMode displayMode = getDisplayModeFromString(displayModeString);
        if (displayMode != null) {
            System.out.println("DISPLAYMODE: " + displayMode);
            if (!fullScreen) {
                Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);
            } else {
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        } else {
            Gdx.graphics.setWindowedMode(width, height);
        }
    }

    public Array<String> getDisplayModes() {
        return new Array<>(
                Stream.of(Gdx.graphics.getDisplayModes(Gdx.graphics.getMonitor()))
                        .filter(displayMode -> displayMode.width >= 1280)
                        .sorted(Comparator.comparingInt(displayMode -> displayMode.width))
                        .map(displayMode -> displayMode.width + "x" + displayMode.height + " " + displayMode.refreshRate + "hz")
                        .toArray(String[]::new));
    }

    public DisplayMode getDisplayModeFromString(String string) {
        String[] displayModeElements = StringUtils.split(string.replace("hz", "").replace(" ", "x"), "x");
        return Stream.of(Gdx.graphics.getDisplayModes(Gdx.graphics.getMonitor()))
                .filter(displayMode -> displayMode.width == Integer.valueOf(displayModeElements[0]) &&
                        displayMode.height == Integer.valueOf(displayModeElements[1]) &&
                        displayMode.refreshRate == Integer.valueOf(displayModeElements[2]))
                .findFirst()
                .orElse(null);
    }

}
