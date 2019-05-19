package com.unicornstudio.lanball.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.core.GameListener;
import com.unicornstudio.lanball.network.client.ClientSynchronizerService;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class GameListenerService {

    private List<GameListener> gameListeners = new ArrayList<>();

    @Inject
    public GameListenerService(ClientSynchronizerService clientSynchronizerService) {
        gameListeners.add(clientSynchronizerService);
    }

    public List<GameListener> getGameListeners() {
        return gameListeners;
    }
}
