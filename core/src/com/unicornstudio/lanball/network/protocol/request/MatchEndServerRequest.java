package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import com.unicornstudio.lanball.network.server.dto.MatchEndReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchEndServerRequest implements NetworkObject {

    private MatchEndReason endReason;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.MATCH_END;
    }
}
