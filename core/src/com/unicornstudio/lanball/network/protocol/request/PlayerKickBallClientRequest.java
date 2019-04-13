package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerKickBallClientRequest implements NetworkObject {

    private Integer playerId;

    private Float forceX;

    private Float forceY;

    private Float pointX;

    private Float pointY;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.BALL_KICK;
    }
}
