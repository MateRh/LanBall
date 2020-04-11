package com.unicornstudio.lanball.network.model.protocol.server;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerKickBallServer implements NetworkObject {

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
