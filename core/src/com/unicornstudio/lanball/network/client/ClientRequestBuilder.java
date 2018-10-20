package com.unicornstudio.lanball.network.client;

import com.badlogic.gdx.math.Vector2;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.network.protocol.JoinReportNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateNetworkObject;

public class ClientRequestBuilder {

    public static JoinReportNetworkObject createJoinReportRequest(String name) {
        JoinReportNetworkObject request = new JoinReportNetworkObject();
        request.setName(name);
        return request;
    }

    public static PlayerUpdateNetworkObject createPlayerUpdateRequest(Player player) {
        Vector2 position = player.getPhysicsEntity().getBody().getPosition();
        Vector2 velocity = player.getPhysicsEntity().getBody().getLinearVelocity();
        PlayerUpdateNetworkObject request = new PlayerUpdateNetworkObject();
        request.setPositionX(position.x);
        request.setPositionY(position.y);
        request.setVelocityX(velocity.x);
        request.setVelocityY(velocity.y);
        return request;
    }

}
