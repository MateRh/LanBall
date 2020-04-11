package com.unicornstudio.lanball.network.model.protocol.client;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerChangeTeamClient implements NetworkObject {

    private Integer id;

    private TeamType teamType;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYER_CHANGE_TEAM;
    }
}
