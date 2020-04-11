package com.unicornstudio.lanball.network.model.protocol;

import com.unicornstudio.lanball.network.model.PlayerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlayersListNetworkObject implements NetworkObject {

    private List<PlayerDto> players;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.PLAYERS_LIST;
    }
}
