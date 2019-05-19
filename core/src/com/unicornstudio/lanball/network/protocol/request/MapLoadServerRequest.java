package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapLoadServerRequest implements NetworkObject {

    private byte[] mapData;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.MAP_LOAD;
    }
}
