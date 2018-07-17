package com.unicornstudio.lanball.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unicornstudio.lanball.map.MapDto;
import com.unicornstudio.lanball.video.renders.Renderer;

public class MapScene implements Renderer {

    private final MapDto mapDto;

    private Stage stage;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public MapScene(MapDto mapDto) {
        this.mapDto = mapDto;
        initialize();
    }

    private void initialize() {
        stage = new Stage(new ScreenViewport(getCamera()));
    }

    private OrthographicCamera getCamera() {
        return new OrthographicCamera(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    @Override
    public void create() {

    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, mapDto.getWorld().getSize().getWidth(), mapDto.getWorld().getSize().getHeight(), Color.BLUE, Color.RED, Color.BROWN, Color.CORAL);
        shapeRenderer.end();
        stage.act();
        stage.draw();
    }
}
