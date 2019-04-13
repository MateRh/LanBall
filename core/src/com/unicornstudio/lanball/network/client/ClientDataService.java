package com.unicornstudio.lanball.network.client;

import com.google.inject.Singleton;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.GameState;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.CRC32;

@Setter
@Getter
@Singleton
public class ClientDataService {

    private Map<TeamType, List<PlayerDto>> players;

    private Map<TeamType, CRC32> playersCRC;

    private PlayerDto remotePlayer;

    private GameState gameState = GameState.IN_LOBBY;

    private Integer timeLimitSelectBoxIndex;

    private Integer scoreLimitSelectBoxIndex;

    private Integer timeLimit;

    public ClientDataService() {
        players = new HashMap<>();
        players.put(TeamType.SPECTATORS, new ArrayList<>());
        players.put(TeamType.TEAM1, new ArrayList<>());
        players.put(TeamType.TEAM2, new ArrayList<>());
        playersCRC = new HashMap<>();
        playersCRC.put(TeamType.SPECTATORS, createCrcOfList(TeamType.SPECTATORS));
        playersCRC.put(TeamType.TEAM1, createCrcOfList(TeamType.TEAM1));
        playersCRC.put(TeamType.TEAM2, createCrcOfList(TeamType.TEAM2));
    }

    public void setTimeLimitSelectBoxIndex(Integer timeLimitSelectBoxIndex) {
        this.timeLimitSelectBoxIndex = timeLimitSelectBoxIndex;
        timeLimit = (timeLimitSelectBoxIndex + 1) * 1000;
    }

    public void addPlayer(PlayerDto player, TeamType teamType) {
        players.get(teamType).add(player);
        playersCRC.replace(teamType, createCrcOfList(teamType));
        if (player.isRemotePlayer()) {
            remotePlayer = player;
        }
    }

    public void removePlayer(PlayerDto player, TeamType teamType) {
        players.get(teamType).remove(player);
        playersCRC.replace(teamType, createCrcOfList(teamType));
    }

    public PlayerDto getPlayerById(Integer id) {
        return players.values().stream()
                .map(
                        playerDtos -> playerDtos.stream()
                                .filter(player -> player.getId() == id)
                                .findFirst()
                                .orElse(null)
                ).filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public void changePlayerTeam(PlayerDto player, TeamType previousTeam, TeamType newTeamType) {
        System.out.println("-----------------------");
        System.out.println(players.get(previousTeam));
        removePlayer(player, previousTeam);
        System.out.println(players.get(previousTeam));
        System.out.println(players.get(newTeamType));
        addPlayer(player, newTeamType);
        System.out.println(players.get(newTeamType));
    }

    public List<PlayerDto> getPlayersByTeam(TeamType teamType) {
        return players.get(teamType);
    }

    public CRC32 getPlayersListCrcByTeam(TeamType teamType) {
        return playersCRC.get(teamType);
    }

    private CRC32 createCrcOfList(TeamType teamType) {
        CRC32 crc = new CRC32();
        crc.update(getPlayersByTeam(teamType).toString().getBytes());
        return crc;
    }

}
