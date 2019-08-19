package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDisconnectRequest implements NetworkObject {

    private Integer id;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_DISCONNECT;
    }
}
