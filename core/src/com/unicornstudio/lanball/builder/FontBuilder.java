package com.unicornstudio.lanball.builder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontBuilder {

    private String name;

    private String format = "ttf";

    private int size = 16;
    
    private boolean mono;

    private FreeTypeFontGenerator.Hinting hinting = FreeTypeFontGenerator.Hinting.AutoMedium;
    
    private Color color = Color.WHITE;

    private float gamma = 1.8f;

    private int renderCount = 2;

    private float borderWidth = 0;

    private Color borderColor = Color.BLACK;

    private boolean borderStraight = false;

    private int shadowOffsetX = 0;

    private int shadowOffsetY = 0;

    private Color shadowColor = new Color(0, 0, 0, 0.75f);

    private int spaceX, spaceY;

    private boolean kerning = true;

    private boolean genMipMaps = false;

    public FontBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FontBuilder format(String format) {
        this.format = format;
        return this;
    }

    public FontBuilder size(int size) {
        this.size = size;
        return this;
    }

    public FontBuilder mono(boolean mono) {
        this.mono = mono;
        return this;
    }

    public FontBuilder hinting(FreeTypeFontGenerator.Hinting hinting) {
        this.hinting = hinting;
        return this;
    }

    public FontBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public FontBuilder gamma(float gamma) {
        this.gamma = gamma;
        return this;
    }

    public FontBuilder renderCount(int renderCount) {
        this.renderCount = renderCount;
        return this;
    }

    public FontBuilder borderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public FontBuilder borderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public FontBuilder borderStraight(boolean borderStraight) {
        this.borderStraight = borderStraight;
        return this;
    }

    public FontBuilder shadowOffsetX(int shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
        return this;
    }

    public FontBuilder shadowOffsetY(int shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
        return this;
    }

    public FontBuilder shadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public FontBuilder spaceX(int spaceX) {
        this.spaceX = spaceX;
        return this;
    }

    public FontBuilder spaceY(int spaceY) {
        this.spaceY = spaceY;
        return this;
    }

    public FontBuilder kerning(boolean kerning) {
        this.kerning = kerning;
        return this;
    }

    public FontBuilder genMipMaps(boolean genMipMaps) {
        this.genMipMaps = genMipMaps;
        return this;
    }

    public BitmapFont build() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.mono = mono;
        parameter.hinting = hinting;
        parameter.color = color;
        parameter.gamma = gamma;
        parameter.renderCount = renderCount;
        parameter.borderWidth = borderWidth;
        parameter.borderColor = borderColor;
        parameter.borderStraight = borderStraight;
        parameter.shadowOffsetX = shadowOffsetX;
        parameter.shadowOffsetY = shadowOffsetY;
        parameter.shadowColor = shadowColor;
        parameter.spaceX = spaceX;
        parameter.spaceY = spaceY;
        parameter.kerning = true;
        parameter.genMipMaps = false;
        return new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + name + "." + format))
                .generateFont(parameter);
    }

}
