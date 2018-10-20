package com.unicornstudio.lanball.network.server;

import com.unicornstudio.lanball.network.protocol.request.CreateRemotePlayerNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.MotionUpdateNetworkObject;

public class ServerRequestBuilder {

    public static CreateRemotePlayerNetworkObject createRemotePlayerRequest(Player player) {
        CreateRemotePlayerNetworkObject request = new CreateRemotePlayerNetworkObject();
        request.setName(player.getName());
        request.setTeamType(player.getTeamType());
        return request;
    }

    public static MotionUpdateNetworkObject createMotionUpdateRequest(Player player) {
        MotionUpdateNetworkObject request = new MotionUpdateNetworkObject();
        request.setPositionX(player.getPositionX());
        request.setPositionY(player.getPositionY());
        request.setVelocityX(player.getVelocityX());
        request.setVelocityY(player.getVelocityY());
        return request;
    }

}
