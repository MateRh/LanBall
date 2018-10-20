package com.unicornstudio.lanball;

import com.badlogic.gdx.Gdx;
import com.google.inject.Singleton;

@Singleton
public class Screen {

    private final static float PIXEL_PER_METER = 10f;

    private final static float METER_PER_PIXEL = 1f/PIXEL_PER_METER;

    private static Integer width = Gdx.graphics.getWidth();

    private static Integer height = Gdx.graphics.getHeight();

    public static Integer getWidth() {
        return width;
    }

    public static Integer getHeight() {
        return height;
    }

    public static Integer getHalfWidth() {
        return width/2;
    }

    public static Integer getHalfHeight() {
        return height/2;
    }

    public static Float getPixelPerMeter() {
        return PIXEL_PER_METER;
    }

    public static Float getMeterPerPixel() {
        return METER_PER_PIXEL;
    }

    public static void setWidth(Integer width) {
        Screen.width = width;
    }

    public static void setHeight(Integer height) {
        Screen.height = height;
    }


}
