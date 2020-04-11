package com.unicornstudio.lanball.network.model.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDisconnect implements NetworkObject {

    private Integer id;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_DISCONNECT;
    }
}
