package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;

public class GameStartClientRequest implements NetworkObject {

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.START_GAME;
    }
}
