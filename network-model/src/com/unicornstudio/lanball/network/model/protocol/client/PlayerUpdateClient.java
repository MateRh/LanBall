package com.unicornstudio.lanball.network.model.protocol.client;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerUpdateClient implements NetworkObject {

    private Float positionX;

    private Float positionY;

    private Float velocityX;

    private Float velocityY;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_UPDATE;
    }
}
