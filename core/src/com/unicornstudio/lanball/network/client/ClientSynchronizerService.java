package com.unicornstudio.lanball.network.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.EntitiesService;
import com.unicornstudio.lanball.GameListener;
import com.unicornstudio.lanball.model.Player;

@Singleton
public class ClientSynchronizerService implements GameListener {

    @Inject
    private ClientService clientService;

    @Inject
    private EntitiesService entitiesService;

    @Override
    public void update() {
        if (clientService.isConnected()) {
            clientService.sendRequest(
                    ClientRequestBuilder.createPlayerUpdateRequest(
                            (Player) entitiesService.getEntity("player")
                    )
            );
        }
    }

}
