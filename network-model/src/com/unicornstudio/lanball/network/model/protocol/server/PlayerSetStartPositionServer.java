package com.unicornstudio.lanball.network.model.protocol.server;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.Data;

@Data
public class PlayerSetStartPositionServer implements NetworkObject {

    private boolean remote;

    private Integer id;

    private TeamType teamType;

    private Integer positionId;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.START_POSITION;
    }
}
