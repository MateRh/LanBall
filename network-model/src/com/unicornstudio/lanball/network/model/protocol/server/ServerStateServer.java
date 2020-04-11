package com.unicornstudio.lanball.network.model.protocol.server;

import com.unicornstudio.lanball.network.model.enumeration.GameState;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.Data;

import java.util.Set;

@Data
public class ServerStateServer implements NetworkObject {

    private GameState gameState;

    private byte[] mapData;

    private Set<RemotePlayerServer> players;

    private Integer timeLimitSelectBoxIndex;

    private Integer scoreLimitSelectBoxIndex;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.SERVER_STATE;
    }
}
