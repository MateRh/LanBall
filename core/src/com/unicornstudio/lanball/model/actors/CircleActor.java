package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.util.TextureCreator;

public class CircleActor extends Actor {

    private final Texture texture;

    public CircleActor(int radius, int lineThickness, String color) {
        setWidth(radius * 2);
        setHeight(radius * 2);
        texture = TextureCreator.createCircleTexture(radius * 2, lineThickness, color);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }
    
}
