package com.unicornstudio.lanball.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.map.MapBackgroundType;
import com.unicornstudio.lanball.map.world.GroundPlane;
import com.unicornstudio.lanball.map.world.SizeDto;

public class MapBackground extends Actor {

    private final Texture texture;
    private int x;
    private int y;

    public MapBackground(SizeDto sizeDto, GroundPlane foreground) {
        texture = provideTexture(sizeDto.getWidth(), sizeDto.getHeight(), MapBackgroundType.valueOf(foreground.getTexture()), foreground.getColor());
        //x = (Gdx.graphics.getWidth() - sizeDto.getWidth())/2;
        //y = (Gdx.graphics.getHeight() - sizeDto.getHeight())/2;
        x = 0;
        y = 0;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, x, y);
    }

    private Texture provideTexture(int width, int height, MapBackgroundType type, String color) {
        return createScaledTexture(new Pixmap(Gdx.files.internal(type.getTexture())), width, height);
    }

    private Texture createScaledTexture(Pixmap filePixMap, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, filePixMap.getFormat());
        pixmap.drawPixmap(filePixMap, 0, 0, filePixMap.getWidth(), filePixMap.getHeight(),
                0, 0, pixmap.getWidth(), pixmap.getHeight());
        return new Texture(pixmap);
    }
}
