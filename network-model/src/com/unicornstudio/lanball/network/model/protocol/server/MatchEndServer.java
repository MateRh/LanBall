package com.unicornstudio.lanball.network.model.protocol.server;

import com.unicornstudio.lanball.network.model.enumeration.MatchEndReason;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchEndServer implements NetworkObject {

    private MatchEndReason endReason;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.MATCH_END;
    }
}
