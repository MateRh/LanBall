package com.unicornstudio.lanball.video;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unicornstudio.lanball.Game;
import com.unicornstudio.lanball.map.MapBackgroundType;
import com.unicornstudio.lanball.map.world.GroundPlane;
import com.unicornstudio.lanball.map.world.SizeDto;

import java.util.HashMap;
import java.util.Map;

public class MapBackground extends Actor {

    private final static int LINE_SPACE = 50;
    private final static int LINE_THICKNESS = 4;
    private final static int GATE_SIZE = 150;

    private final Texture texture;
    private int x;
    private int y;

    public MapBackground(SizeDto sizeDto, GroundPlane foreground) {
        //texture = provideTexture(sizeDto.getWidth(), sizeDto.getHeight(), MapBackgroundType.valueOf(foreground.getTexture()), foreground.getColor());
        texture = provideTexture(sizeDto.getWidth(), sizeDto.getHeight(), MapBackgroundType.GRASS_PATTERN_ONE, foreground.getColor());
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
        return createTextureFromPattern(new Pixmap(Gdx.files.internal(type.getTexture())), width, height);
    }

    private Texture createScaledTexture(Pixmap filePixMap, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, filePixMap.getFormat());
        pixmap.drawPixmap(filePixMap, 0, 0, filePixMap.getWidth(), filePixMap.getHeight(),
                0, 0, pixmap.getWidth(), pixmap.getHeight());
        return new Texture(pixmap);
    }

    private Texture createTextureFromPattern(Pixmap filePixMap, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, filePixMap.getFormat());
        pixmap.setFilter(Pixmap.Filter.BiLinear);
        fillTexture(pixmap, filePixMap, width, height);
        drawFrame(pixmap);

        drawFieldLines(pixmap);
/*
        pixmap.setColor(1, 1, 1, 0.5f);
        pixmap.fillRectangle(35, 35-3, pixmap.getWidth() - 70, 3);
        pixmap.fillRectangle(35, pixmap.getHeight() - 35, pixmap.getWidth() - 70, 3);

        pixmap.fillRectangle(35, 35, 3, pixmap.getHeight()/2 -100);
        pixmap.fillRectangle(10, pixmap.getHeight()/2 - 70, 3, 135);
        pixmap.fillRectangle(10, pixmap.getHeight()/2 - 70, 25, 3);
        pixmap.fillRectangle(35, pixmap.getHeight()/2 +100 - 35, 3, pixmap.getHeight()/2 -100); */

        //drawRentagle(pixmap, pixmap.getWidth() - 35, 35, 3, pixmap.getHeight() - 70);
        return new Texture(pixmap);
    }

    private void fillTexture(Pixmap pixmap, Pixmap texturePixMap, int width, int height) {
        for (int i = 0; i < Math.ceil(width * 1f / texturePixMap.getWidth()); i++) {
            pixmap.drawPixmap(texturePixMap, i * texturePixMap.getWidth(), 0, 0, 0, texturePixMap.getWidth(), texturePixMap.getHeight());
            for (int j = 0; j < Math.ceil(height * 1f / texturePixMap.getHeight()); j++) {
                pixmap.drawPixmap(texturePixMap, i * texturePixMap.getWidth(), j * texturePixMap.getHeight(), 0, 0, texturePixMap.getWidth(), texturePixMap.getHeight());
            }
        }
    }

    private void drawFrame(Pixmap pixmap) {
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.drawLine(0, 0, pixmap.getWidth(), 0);
        pixmap.drawLine(0, 0, 1, pixmap.getHeight());
        pixmap.drawLine(0, pixmap.getHeight() - 1, pixmap.getWidth(), pixmap.getHeight() - 1);
        pixmap.drawLine(pixmap.getWidth() - 1, 0, pixmap.getWidth() - 1, pixmap.getHeight());
    }

    private void drawFieldLines(Pixmap pixmap) {
        int width = pixmap.getWidth() - 2 * LINE_SPACE;
        int height = pixmap.getHeight() - 2 * LINE_SPACE;
        int x = LINE_SPACE;
        int y = LINE_SPACE;
        int endX = x + width;
        int endY = y + height;
        int leftGateX = LINE_SPACE / 2;
        int leftGateY = (pixmap.getHeight() - GATE_SIZE) / 2;
        int rightGateX = endX + LINE_SPACE / 2;
        int rightGateY = (pixmap.getHeight() - GATE_SIZE) / 2;

        pixmap.setColor(1, 1, 1, 0.5f);
        fillRectangle(pixmap, x, y, width, LINE_THICKNESS);
        fillRectangle(pixmap, x, y, LINE_THICKNESS, height);
        fillRectangle(pixmap, x, endY, width, LINE_THICKNESS);
        fillRectangle(pixmap, endX, y, LINE_THICKNESS, height);

        fillRectangle(pixmap, leftGateX, leftGateY, LINE_THICKNESS, GATE_SIZE);
        fillRectangle(pixmap, leftGateX, leftGateY, leftGateX, LINE_THICKNESS);
        fillRectangle(pixmap, leftGateX, leftGateY + GATE_SIZE, leftGateX, LINE_THICKNESS);

        fillRectangle(pixmap, rightGateX, rightGateY, LINE_THICKNESS, GATE_SIZE);
        fillRectangle(pixmap, rightGateX - leftGateX, rightGateY, leftGateX, LINE_THICKNESS);
        fillRectangle(pixmap, rightGateX- leftGateX, rightGateY + GATE_SIZE, leftGateX, LINE_THICKNESS);

        fillRectangle(pixmap, x + width/2, y, LINE_THICKNESS, height);

        for (int i = 0; i < 360; i++) {
            for (int j = 0; j < LINE_THICKNESS; j++) {
                Map<String, Integer> map = getForceFromAngle(x + width / 2, y + height / 2, i, GATE_SIZE + j);
                //fillRectangle(pixmap, (int) vector2.x, (int) vector2.y, LINE_THICKNESS, LINE_THICKNESS);
                pixmap.drawPixel(map.get("x"), map.get("y"));
                pixmap.drawPixel(map.get("x") + 1, map.get("y"));
                pixmap.drawPixel(map.get("x") + 1, map.get("y") + 1);
            }
        }
    }

    private void fillRectangle(Pixmap pixmap, int x, int y, int width, int height) {
        int halfLineThickness = LINE_THICKNESS / 2;
        if (width == LINE_THICKNESS) {
            x = x - halfLineThickness;
            y = y - halfLineThickness;
            height = height + LINE_THICKNESS;
        }
        if (height == LINE_THICKNESS) {
            y = y - halfLineThickness;
            x = x - halfLineThickness;
            width = width + LINE_THICKNESS;
        }
        pixmap.fillRectangle(x, y, width, height);
    }


    private Map<String, Integer> getForceFromAngle(int x, int y, float angle, int distance) {
        Map<String, Integer> map = new HashMap<>();
        double a = Math.toRadians(90 - angle);
        double dx = Math.cos(a) * distance;
        double dy = Math.sin(a) * distance;
        map.put("x", x + (int) dx);
        map.put("y", y + (int) dy);
        return map;
    }
}
