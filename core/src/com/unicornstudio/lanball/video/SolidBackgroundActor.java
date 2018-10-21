package com.unicornstudio.lanball.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.utils.TextureCreator;

public class SolidBackgroundActor extends Actor {

    private final Texture texture;

    public SolidBackgroundActor(String color) {
        texture = TextureCreator.createSolidColorTexture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), color);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0);
    }

}
