package com.unicornstudio.lanball.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.unicornstudio.lanball.io.KeyboardInput;
import com.unicornstudio.lanball.model.map.MapService;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.server.ServerService;
import com.unicornstudio.lanball.service.EntitiesService;
import com.unicornstudio.lanball.service.GameListenerService;
import com.unicornstudio.lanball.service.WorldService;
import com.unicornstudio.lanball.service.StageService;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Getter
@Singleton
public class Game {

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private KeyboardInput keyboardInput;

    @Inject
    private WorldService worldService;

    @Inject
    private MapService mapService;

    @Inject
    private StageService stageService;

    @Inject
    private PhysicsTimeStep physicsTimeStep;

    @Inject
    private GameListenerService gameListenerService;

    @Inject
    private ServerService serverService;

    @Inject
    private ClientService clientService;

    public void render () {
        Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        if (worldService.isCreated()) {
            physicsTimeStep.processStep(worldService.getWorld());
            entitiesService.synchronizeEntitiesPosition();

            keyboardInput.onInput();
            for (GameListener gameListener : gameListenerService.getGameListeners()) {
                gameListener.update();
            }
        }
    }
}
