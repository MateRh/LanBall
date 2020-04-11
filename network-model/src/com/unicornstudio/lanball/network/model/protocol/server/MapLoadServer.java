package com.unicornstudio.lanball.network.model.protocol.server;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapLoadServer implements NetworkObject {

    private byte[] mapData;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.MAP_LOAD;
    }
}
