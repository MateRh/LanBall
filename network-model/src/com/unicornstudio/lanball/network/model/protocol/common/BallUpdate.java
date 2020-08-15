package com.unicornstudio.lanball.network.model.protocol.common;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BallUpdate implements NetworkObject {

    private Float positionX;

    private Float positionY;

    private Float velocityX;

    private Float velocityY;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.BALL_UPDATE;
    }
}
