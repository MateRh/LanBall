package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.NetworkObject;
import lombok.Data;

@Data
public class CreateRemotePlayerNetworkObject implements NetworkObject {

    private String name;

    private TeamType teamType;

}
