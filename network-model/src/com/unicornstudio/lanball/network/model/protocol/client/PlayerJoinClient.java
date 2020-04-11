package com.unicornstudio.lanball.network.model.protocol.client;

import com.unicornstudio.lanball.network.model.enumeration.PlayerRole;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.Data;

@Data
public class PlayerJoinClient implements NetworkObject {

    private Integer networkProtocolVersion;

    private Integer id;

    private String name;

    private PlayerRole role;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_JOIN;
    }
}
