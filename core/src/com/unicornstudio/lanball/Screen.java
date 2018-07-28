package com.unicornstudio.lanball;

public class Screen {

    private final static float PIXEL_PER_METER = 10f;

    private final static float METER_PER_PIXEL = 1f/PIXEL_PER_METER;

    private static Integer width;

    private static Integer height;

    public static Integer getWidth() {
        return width;
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

    public static Integer getHeight() {
        return height;
    }

    public static void setHeight(Integer height) {
        Screen.height = height;
    }


}
