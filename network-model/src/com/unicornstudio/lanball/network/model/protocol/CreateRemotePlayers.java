package com.unicornstudio.lanball.network.model.protocol;

import com.unicornstudio.lanball.network.model.protocol.server.RemotePlayerServer;
import lombok.Data;

import java.util.Set;

@Data
public class CreateRemotePlayers implements NetworkObject {

    private Set<RemotePlayerServer> objects;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.CREATE_REMOTE_PLAYERS;
    }
}
