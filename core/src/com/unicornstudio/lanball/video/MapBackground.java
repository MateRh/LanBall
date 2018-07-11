package com.unicornstudio.lanball.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.map.model.Background;
import com.unicornstudio.lanball.map.model.BackgroundType;

public class MapBackground extends Actor {

    private final Texture texture;
    private int x;
    private int y;

    public MapBackground(Background background) {
        texture = provideTexture(background.getWidth(), background.getHeight());
        x = (Gdx.graphics.getWidth() - background.getWidth())/2;
        y = (Gdx.graphics.getHeight() - background.getHeight())/2;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, x, y);
    }

    private Texture provideTexture(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(BackgroundType.GRASS.getColor());
        pixmap.fillRectangle(0, 0, width, height);
        return new Texture(pixmap);
    }
}
