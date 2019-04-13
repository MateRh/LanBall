package com.unicornstudio.lanball.network.server.util;


import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.network.protocol.request.PlayersListNetworkObject;
import com.unicornstudio.lanball.network.server.dto.Player;

import java.util.Set;
import java.util.stream.Collectors;

public class ServerResponseBuilder {

    public static PlayersListNetworkObject createPlayersListNetworkObject(Set<Player> playerList) {
        PlayersListNetworkObject playersListNetworkObject = new PlayersListNetworkObject();
        playersListNetworkObject.setPlayers(playerList.stream().map(ServerResponseBuilder::createPlayerDto).collect(Collectors.toList()));
        return playersListNetworkObject;
    }

    private static PlayerDto createPlayerDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(player.getName());
        playerDto.setTeamType(player.getTeamType());
        return playerDto;
    }

}
