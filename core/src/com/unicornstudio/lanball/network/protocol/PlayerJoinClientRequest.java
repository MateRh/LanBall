package com.unicornstudio.lanball.network.protocol;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import com.unicornstudio.lanball.network.server.dto.PlayerRole;
import lombok.Data;

@Data
public class PlayerJoinClientRequest implements NetworkObject {

    private Integer id;

    private String name;

    private PlayerRole role;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_JOIN;
    }
}
