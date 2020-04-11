package com.unicornstudio.lanball.network.model.protocol.server;


import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;

public class GameStartServer implements NetworkObject {

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.START_GAME;
    }
}
