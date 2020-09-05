package com.unicornstudio.lanball.network.client;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.network.model.enumeration.PlayerRole;
import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.network.model.protocol.BallContact;
import com.unicornstudio.lanball.network.model.protocol.client.GameStartClient;
import com.unicornstudio.lanball.network.model.protocol.client.GateContactClient;
import com.unicornstudio.lanball.network.model.protocol.client.MapLoadClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerChangeTeamClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerJoinClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerKeyPressClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerKickBallClient;
import com.unicornstudio.lanball.network.model.protocol.client.PlayerUpdateClient;
import com.unicornstudio.lanball.network.model.protocol.client.SelectBoxUpdateClient;
import com.unicornstudio.lanball.network.model.protocol.common.BallUpdate;
import com.unicornstudio.lanball.util.CompressionUtil;

public class ClientRequestBuilder {

    public static PlayerJoinClient createPlayerJoinRequest(Integer networkProtocolVersion, String name, PlayerRole role) {
        PlayerJoinClient request = new PlayerJoinClient();
        request.setNetworkProtocolVersion(networkProtocolVersion);
        request.setName(name);
        request.setRole(role);
        return request;
    }

    public static PlayerUpdateClient createPlayerUpdateRequest(Player player) {
        Vector2 position = getPosition(player);
        Vector2 velocity = getVelocity(player);
        return new PlayerUpdateClient(position.x, position.y, velocity.x, velocity.y);
    }

    public static BallUpdate createBallUpdateClientRequest(Ball ball) {
        Vector2 position = getPosition(ball);
        Vector2 velocity = getVelocity(ball);
        return new BallUpdate(position.x, position.y, velocity.x, velocity.y);
    }

    public static MapLoadClient createMapLoadClientRequest(FileHandle mapFile) {
        return new MapLoadClient(
                CompressionUtil.compress(mapFile.readString()));
    }

    public static PlayerChangeTeamClient createPlayerChangeTeamClientRequest(Integer id, TeamType type) {
        return new PlayerChangeTeamClient(id, type);
    }

    public static GameStartClient createGameStartClientRequest() {
        return new GameStartClient();
    }

    public static SelectBoxUpdateClient createSelectBoxUpdateClientRequest(String selectBoxName, Integer selectedIndex) {
        return new SelectBoxUpdateClient(selectBoxName, selectedIndex);
    }

    public static PlayerKickBallClient createPlayerKickBallClientRequest(Integer playerId, Vector2 force, Vector2 point) {
        return new PlayerKickBallClient(playerId, force.x, force.y, point.x, point.y);
    }

    public static GateContactClient createGateContactClientRequest(TeamType teamType) {
        return new GateContactClient(teamType);
    }

    public static PlayerKeyPressClient createPlayerKeyPressClientRequest(Integer playerId) {
        return new PlayerKeyPressClient(playerId);
    }

    public static BallContact createBallContactRequest() {
        return new BallContact();
    }

    private static Vector2 getPosition(Entity entity) {
        return entity.getPhysicsEntity().getBody().getPosition();
    }

    private static Vector2 getVelocity(Entity entity) {
        return entity.getPhysicsEntity().getBody().getLinearVelocity();
    }
}
