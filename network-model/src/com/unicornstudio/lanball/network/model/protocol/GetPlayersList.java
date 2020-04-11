package com.unicornstudio.lanball.network.model.protocol;

public class GetPlayersList implements NetworkObject {

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.GET_PLAYERS_LIST;
    }

}
