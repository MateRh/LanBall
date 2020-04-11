package com.unicornstudio.lanball.network.model.protocol.client;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerKeyPressClient implements NetworkObject {

    private Integer playerId;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.KEY_PRESS;
    }
}
