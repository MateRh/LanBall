package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WorldBackground extends Actor {

    private final Texture texture;
    private int x;
    private int y;

    public WorldBackground(String color) {
        x = 0;
        y = 0;
        texture = provideTexture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, x, y);
    }

    private Texture provideTexture(int width, int height, String color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.valueOf(color));
        pixmap.fillRectangle(0, 0, width, height);
        return new Texture(pixmap);
    }
}
