package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.map.settings.BallSettings;
import com.unicornstudio.lanball.util.TextureCreator;

public class BallActor extends Actor {

    private final Texture texture;

    public BallActor(BallSettings ballSettings) {
        setWidth(ballSettings.getSize());
        setHeight(ballSettings.getSize());
        texture = provideTexture(ballSettings.getSize(), ballSettings.getColor());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    private Texture provideTexture(int size, String color) {
        return TextureCreator.createDiskTexture(size * 3, color, 6);
    }
}
