package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.Data;

import java.util.Set;

@Data
public class CreateRemotePlayersNetworkObject implements NetworkObject {

    private Set<RemotePlayerServerRequest> objects;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.CREATE_REMOTE_PLAYERS;
    }
}
