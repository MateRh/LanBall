package com.unicornstudio.lanball.network.common;

import com.esotericsoftware.kryo.Kryo;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.network.protocol.JoinReportNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.CreateRemotePlayerNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.GetPlayersListNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.MotionUpdateNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateNetworkObject;

public class NetworkClassRegisterer {

    public static void register(Kryo kryo) {
        kryo.register(JoinReportNetworkObject.class);
        kryo.register(CreateRemotePlayerNetworkObject.class);
        kryo.register(TeamType.class);
        kryo.register(PlayerDto.class);
        kryo.register(PlayerUpdateNetworkObject.class);
        kryo.register(MotionUpdateNetworkObject.class);
        kryo.register(GetPlayersListNetworkObject.class);
    }
}
