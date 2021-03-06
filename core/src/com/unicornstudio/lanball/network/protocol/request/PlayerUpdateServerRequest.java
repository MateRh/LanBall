package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
