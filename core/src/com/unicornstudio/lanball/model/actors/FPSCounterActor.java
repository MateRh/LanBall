package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FPSCounterActor extends Actor {

    private BitmapFont font = new BitmapFont();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (getZIndex() != Integer.MAX_VALUE) {
            toFront();
        }
        font.draw(
                batch,
                "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);
    }
}
