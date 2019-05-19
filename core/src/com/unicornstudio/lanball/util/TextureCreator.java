package com.unicornstudio.lanball.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureCreator {

    public static Texture createCircleTexture(int size, String color, int borderSize) {
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmap.setFilter(Pixmap.Filter.BiLinear);
        pixmap.setColor(Color.BLACK);
        pixmap.fillCircle(size / 2, size / 2, (size / 2) - 1);
        pixmap.setColor(Color.valueOf(color));
        pixmap.fillCircle(size / 2, size / 2, Math.round((size / 2f) - borderSize));
        return new Texture(pixmap);
    }

    public static Texture createSolidColorTexture(int width, int height, String color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf(color));
        pixmap.fillRectangle(0, 0, width, height);
        return new Texture(pixmap);
    }

}
