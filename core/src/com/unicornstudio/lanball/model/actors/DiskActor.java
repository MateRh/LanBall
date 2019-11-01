package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.util.TextureCreator;

public class DiskActor extends Actor {

    private final Texture texture;

    public DiskActor(int radius, int borderSize, String color) {
        setWidth(radius * 2);
        setHeight(radius * 2);
        texture = TextureCreator.createDiskTexture(radius * 2, color, borderSize);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }

}
