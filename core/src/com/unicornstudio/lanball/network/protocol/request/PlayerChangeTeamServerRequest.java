package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerChangeTeamServerRequest implements NetworkObject {

    private Integer id;

    private TeamType teamType;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_CHANGE_TEAM;
    }
}
