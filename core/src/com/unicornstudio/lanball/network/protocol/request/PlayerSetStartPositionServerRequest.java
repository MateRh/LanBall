package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.Data;

@Data
public class PlayerSetStartPositionServerRequest implements NetworkObject {

    private boolean remote;

    private Integer id;

    private TeamType teamType;

    private Integer positionId;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.START_POSITION;
    }
}
