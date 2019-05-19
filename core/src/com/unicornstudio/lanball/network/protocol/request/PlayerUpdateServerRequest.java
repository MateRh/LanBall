package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.Data;

@Data
public class PlayerUpdateServerRequest implements NetworkObject {

    private boolean remote;

    private Integer id;

    private Float positionX;

    private Float positionY;

    private Float velocityX;

    private Float velocityY;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_UPDATE;
    }
}
