package com.unicornstudio.lanball.network.model.protocol.server;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.Data;

@Data
public class RemotePlayerServer implements NetworkObject {

    private Integer id;

    private String name;

    private TeamType teamType;

    private boolean remotePlayer;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.REMOTE_PLAYER;
    }
}
