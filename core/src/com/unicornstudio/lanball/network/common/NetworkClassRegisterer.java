package com.unicornstudio.lanball.network.common;

import com.esotericsoftware.kryo.Kryo;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.dto.PlayerDto;
import com.unicornstudio.lanball.network.protocol.PlayerJoinClientRequest;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.GameStartClientRequest;
import com.unicornstudio.lanball.network.protocol.request.GameStartServerRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadClientRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallServerRequest;
import com.unicornstudio.lanball.network.protocol.request.RemotePlayerServerRequest;
import com.unicornstudio.lanball.network.protocol.request.CreateRemotePlayersNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.GetPlayersListNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayersListNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateServerRequest;
import com.unicornstudio.lanball.network.protocol.request.ServerStateServerRequest;
import com.unicornstudio.lanball.network.server.dto.Ball;
import com.unicornstudio.lanball.network.server.dto.PlayerRole;

import java.util.ArrayList;
import java.util.HashSet;

public class NetworkClassRegisterer {

    public static void register(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(PlayerJoinClientRequest.class);
        kryo.register(RemotePlayerServerRequest.class);
        kryo.register(CreateRemotePlayersNetworkObject.class);
        kryo.register(ServerStateServerRequest.class);
        kryo.register(TeamType.class);
        kryo.register(PlayerDto.class);
        kryo.register(PlayerUpdateClientRequest.class);
        kryo.register(PlayerUpdateServerRequest.class);
        kryo.register(GetPlayersListNetworkObject.class);
        kryo.register(PlayersListNetworkObject.class);
        kryo.register(PlayerChangeTeamClientRequest.class);
        kryo.register(PlayerChangeTeamServerRequest.class);
        kryo.register(HashSet.class);
        kryo.register(PlayerRole.class);
        kryo.register(Ball.class);
        kryo.register(BallUpdateServerRequest.class);
        kryo.register(BallUpdateClientRequest.class);
        kryo.register(MapLoadServerRequest.class);
        kryo.register(MapLoadClientRequest.class);
        kryo.register(GameState.class);
        kryo.register(GameStartClientRequest.class);
        kryo.register(GameStartServerRequest.class);
        kryo.register(SelectBoxUpdateClientRequest.class);
        kryo.register(SelectBoxUpdateServerRequest.class);
        kryo.register(PlayerKickBallClientRequest.class);
        kryo.register(PlayerKickBallServerRequest.class);
    }
}
