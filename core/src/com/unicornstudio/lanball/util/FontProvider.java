package com.unicornstudio.lanball.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontProvider {

    public static BitmapFont provide(String font, int size, Color color) {
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + font + ".ttf"));
        BitmapFont bitmapFont = freeTypeFontGenerator.generateFont(provideFontParameter(size, color));
        freeTypeFontGenerator.dispose();
        return bitmapFont;
    }

    public static BitmapFont provide(String font, int size, Color color, float borderWidth) {
        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + font + ".ttf"));
        BitmapFont bitmapFont = freeTypeFontGenerator.generateFont(provideFontParameter(size, color, borderWidth));
        freeTypeFontGenerator.dispose();
        return bitmapFont;
    }

    private static FreeTypeFontGenerator.FreeTypeFontParameter provideFontParameter(int size, Color color) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        return parameter;
    }
    private static FreeTypeFontGenerator.FreeTypeFontParameter provideFontParameter(int size, Color color, float borderWidth) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        parameter.borderWidth = borderWidth;
        parameter.borderColor = Color.BLACK;
        return parameter;
    }

}
