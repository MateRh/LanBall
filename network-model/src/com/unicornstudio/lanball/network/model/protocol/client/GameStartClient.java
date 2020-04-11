package com.unicornstudio.lanball.network.model.protocol.client;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;

public class GameStartClient implements NetworkObject {

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.START_GAME;
    }
}
