package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.Data;

import java.util.Set;

@Data
public class ServerStateServerRequest implements NetworkObject {

    private GameState gameState;

    private Set<RemotePlayerServerRequest> players;

    private Integer timeLimitSelectBoxIndex;

    private Integer scoreLimitSelectBoxIndex;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.SERVER_STATE;
    }
}
