package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerKeyPressServerRequest implements NetworkObject {

    private Integer playerId;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.KEY_PRESS;
    }
}
