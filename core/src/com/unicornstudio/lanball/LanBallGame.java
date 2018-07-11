package com.unicornstudio.lanball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unicornstudio.lanball.io.mappers.MapMapper;
import com.unicornstudio.lanball.map.MapScene;
import com.unicornstudio.lanball.map.model.Map;
import com.unicornstudio.lanball.settings.VideoSettings;
import com.unicornstudio.lanball.video.MapBackground;
import com.unicornstudio.lanball.video.WorldBackground;
import com.unicornstudio.lanball.video.renders.Renderer;
import com.unicornstudio.lanball.video.renders.Renderers;

import javax.inject.Inject;


public class LanBallGame extends ApplicationAdapter {

	private final Renderers renderers;

	private Stage stage;

	@Inject
	public LanBallGame(Renderers renderers) {
		this.renderers = renderers;
	}


	@Override
	public void create () {
	    //VisUI.load();
		new VideoSettings().apply();
        FileHandle fileHandle = new FileHandle("C:\\Users\\Mate_\\Desktop\\haxmaps_152434564783.hbs");
        Map map = MapMapper.map(fileHandle).orElse(null);
        stage = new Stage(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
        stage.getCamera().position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        stage.getCamera().view.scl(2);
        Renderers.getRenderers().forEach(
                Renderer::create
        );
        //new MapChooser().show(stage);

        Renderers.add(new MapScene(map));
        stage.addActor(new WorldBackground(map));
        stage.addActor(new MapBackground(map.getBackground()));
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		super.render();
		stage.act();
		stage.draw();
   //     Renderers.getRenderers().forEach(
      //          Renderer::render
     //   );
	}
	
	@Override
	public void dispose () {

	}
}
