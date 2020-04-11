package com.unicornstudio.lanball.network.model.protocol;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoundReset implements NetworkObject {

    private TeamType startingTeam;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.ROUND_RESET;
    }
}
