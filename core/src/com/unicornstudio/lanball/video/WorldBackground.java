package com.unicornstudio.lanball.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.map.model.Background;
import com.unicornstudio.lanball.map.model.BackgroundType;
import com.unicornstudio.lanball.map.model.Map;

public class WorldBackground extends Actor {

    private final Texture texture;
    private int x;
    private int y;

    public WorldBackground(Map map) {
        texture = provideTexture(map.getWidth(), map.getHeight());
        x = (Gdx.graphics.getWidth() - map.getWidth())/2;
        y = (Gdx.graphics.getHeight() - map.getHeight())/2;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, x, y);
    }

    private Texture provideTexture(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(BackgroundType.WORLD.getColor());
        pixmap.fillRectangle(0, 0, width, height);
        return new Texture(pixmap);
    }
}
