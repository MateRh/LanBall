package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreUpdateRequest implements NetworkObject {

    private TeamType teamType;

    private Integer score;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.SCORE_UPDATE;
    }
}
