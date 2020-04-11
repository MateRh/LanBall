package com.unicornstudio.lanball.server.builder;

import com.unicornstudio.lanball.network.model.Player;
import com.unicornstudio.lanball.network.model.PlayerDto;
import com.unicornstudio.lanball.network.model.protocol.PlayersListNetworkObject;

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
