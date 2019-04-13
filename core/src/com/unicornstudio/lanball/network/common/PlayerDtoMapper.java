package com.unicornstudio.lanball.network.common;

import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.network.protocol.request.RemotePlayerServerRequest;

public class PlayerDtoMapper {

    public static PlayerDto createPlayer(RemotePlayerServerRequest request) {
        PlayerDto player = new PlayerDto();
        player.setId(request.getId());
        player.setName(request.getName());
        player.setTeamType(request.getTeamType());
        player.setRemotePlayer(request.isRemotePlayer());
        return player;
    }

}
