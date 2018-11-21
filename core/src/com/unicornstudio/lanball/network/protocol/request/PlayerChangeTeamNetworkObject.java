package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.NetworkObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerChangeTeamNetworkObject implements NetworkObject {

    private String playerName;

    private TeamType teamType;

}
