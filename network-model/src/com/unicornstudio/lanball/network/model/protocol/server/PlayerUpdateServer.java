package com.unicornstudio.lanball.network.model.protocol.server;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerUpdateServer implements NetworkObject {

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
