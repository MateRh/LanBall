package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.util.TextureCreator;

public class RectangleActor extends Actor {

    private final Texture texture;

    public RectangleActor(int width, int height, String color) {
        setWidth(width);
        setHeight(height);
        texture = TextureCreator.createSolidRectangleTexture(width, height, color);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }
    
}
