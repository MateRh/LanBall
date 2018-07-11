package com.unicornstudio.lanball.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unicornstudio.lanball.map.model.Map;
import com.unicornstudio.lanball.video.renders.Renderer;

public class MapScene implements Renderer {

    private final Map map;

    private Stage stage;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public MapScene(Map map) {
        this.map = map;
        initialize();
    }

    private void initialize() {
        stage = new Stage(new ScreenViewport(getCamera()));
    }

    private OrthographicCamera getCamera() {
        return new OrthographicCamera(map.getWidth(), map.getHeight());
    }

    @Override
    public void create() {

    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, map.getBackground().getWidth(), map.getBackground().getHeight(), Color.BLUE, Color.RED, Color.BROWN, Color.CORAL);
        shapeRenderer.end();
        stage.act();
        stage.draw();
    }
}
