package com.unicornstudio.lanball.network.model.protocol;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreUpdate implements NetworkObject {

    private TeamType teamType;

    private Integer score;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.SCORE_UPDATE;
    }
}
