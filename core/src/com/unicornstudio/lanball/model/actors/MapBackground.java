package com.unicornstudio.lanball.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.model.map.MapBackgroundType;
import com.unicornstudio.lanball.model.map.world.GroundPlane;
import com.unicornstudio.lanball.model.map.world.SizeDto;

import java.util.HashMap;
import java.util.Map;

public class MapBackground extends Actor {

    public final static int LINE_SPACE = 50;
    public final static int GATE_SIZE = 150;

    private final Texture texture;

    public MapBackground(SizeDto sizeDto, GroundPlane foreground) {
        texture = provideTexture(sizeDto.getWidth(), sizeDto.getHeight(), MapBackgroundType.GRASS_STRIPES_PATTERN_x2, foreground.getColor());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, 0, 0);
    }

    private Texture provideTexture(int width, int height, MapBackgroundType type, String color) {
        return createTextureFromPattern(new Pixmap(Gdx.files.internal(type.getTexture())), width, height);
    }

    private Texture createTextureFromPattern(Pixmap filePixMap, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, filePixMap.getFormat());
        pixmap.setFilter(Pixmap.Filter.BiLinear);
        fillTexture(pixmap, filePixMap, width, height);


        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private void fillTexture(Pixmap pixmap, Pixmap texturePixMap, int width, int height) {
        for (int i = 0; i < Math.ceil(width * 1f / texturePixMap.getWidth()); i++) {
            pixmap.drawPixmap(texturePixMap, i * texturePixMap.getWidth(), 0, 0, 0, texturePixMap.getWidth(), texturePixMap.getHeight());
            for (int j = 0; j < Math.ceil(height * 1f / texturePixMap.getHeight()); j++) {
                pixmap.drawPixmap(texturePixMap, i * texturePixMap.getWidth(), j * texturePixMap.getHeight(), 0, 0, texturePixMap.getWidth(), texturePixMap.getHeight());
            }
        }
    }

}
