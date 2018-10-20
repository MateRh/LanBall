package com.unicornstudio.lanball.network.protocol.response;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import lombok.Data;

@Data
public class PlayersListResponse {

    private List<PlayerDto> players;

}
