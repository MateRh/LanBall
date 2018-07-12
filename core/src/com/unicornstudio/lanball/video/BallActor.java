package com.unicornstudio.lanball.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.map.settings.Ball;

public class BallActor extends Actor {

    private final Texture texture;

    public BallActor(Ball ball) {
        setX((Gdx.graphics.getWidth() - ball.getSize())/2);
        setY((Gdx.graphics.getHeight() - ball.getSize())/2);
        texture = provideTexture(ball.getSize(), ball.getColor());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }

    private Texture provideTexture(int size, String color) {
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf(color));
        pixmap.setFilter(Pixmap.Filter.BiLinear);
        pixmap.fillCircle(size/2, size/2, (size/2)-1);
        return new Texture(pixmap);
    }
}
