package com.unicornstudio.lanball.network.protocol.request;

import java.util.List;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayersListNetworkObject implements NetworkObject {

    private List<PlayerDto> players;

}
