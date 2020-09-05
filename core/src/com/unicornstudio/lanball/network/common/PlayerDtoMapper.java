package com.unicornstudio.lanball.network.common;


import com.unicornstudio.lanball.network.model.PlayerDto;
import com.unicornstudio.lanball.network.model.protocol.server.RemotePlayerServer;

public class PlayerDtoMapper {

    public static PlayerDto createPlayer(RemotePlayerServer request) {
        PlayerDto player = new PlayerDto();
        player.setId(request.getId());
        player.setName(request.getName());
        player.setTeamType(request.getTeamType());
        player.setRemotePlayer(request.isRemotePlayer());
        return player;
    }

}
