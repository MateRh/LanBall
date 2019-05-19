package com.unicornstudio.lanball.network.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.service.EntitiesService;
import com.unicornstudio.lanball.core.GameListener;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.network.common.GameState;

@Singleton
public class ClientSynchronizerService implements GameListener {

    @Inject
    private ClientService clientService;

    @Inject
    private EntitiesService entitiesService;

    @Inject
    private ClientDataService clientDataService;

    @Override
    public void update() {
        if (!clientDataService.getGameState().equals(GameState.IN_PROGRESS) && !clientDataService.getGameState().equals(GameState.PAUSE)) {
            return;
        }

        if (clientService.isConnected()) {
            clientService.sendRequest(
                    ClientRequestBuilder.createPlayerUpdateRequest(
                            (Player) entitiesService.getEntity("player")
                    )
            );
            if (clientService.isHost()) {
                clientService.sendRequest(
                    ClientRequestBuilder.createBallUpdateClientRequest(
                            (Ball) entitiesService.getEntity("ball")
                    )
                );
            }
        }
    }

}
