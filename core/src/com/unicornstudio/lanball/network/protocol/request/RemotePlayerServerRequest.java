package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.Data;

@Data
public class RemotePlayerServerRequest implements NetworkObject {

    private Integer id;

    private String name;

    private TeamType teamType;

    private boolean remotePlayer;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.REMOTE_PLAYER;
    }
}
