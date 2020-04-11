package com.unicornstudio.lanball.network.model.protocol;

import com.unicornstudio.lanball.network.model.PlayerDto;
import lombok.Data;

import java.util.List;

@Data
public class PlayersListResponse {

    private List<PlayerDto> players;

}
