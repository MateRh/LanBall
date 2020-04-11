package com.unicornstudio.lanball.network.client;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.unicornstudio.lanball.model.Ball;
import com.unicornstudio.lanball.model.Entity;
import com.unicornstudio.lanball.model.Player;
import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.protocol.PlayerJoinClientRequest;
import com.unicornstudio.lanball.network.protocol.request.BallContactRequest;
import com.unicornstudio.lanball.network.protocol.request.BallUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.GameStartClientRequest;
import com.unicornstudio.lanball.network.protocol.request.GateContactClientRequest;
import com.unicornstudio.lanball.network.protocol.request.MapLoadClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerChangeTeamClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKeyPressClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerKickBallClientRequest;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateClientRequest;
import com.unicornstudio.lanball.network.protocol.request.SelectBoxUpdateClientRequest;
import com.unicornstudio.lanball.network.server.dto.PlayerRole;
import com.unicornstudio.lanball.util.CompressionUtil;

public class ClientRequestBuilder {

    public static PlayerJoinClientRequest createPlayerJoinRequest(Integer networkProtocolVersion, String name, PlayerRole role) {
        PlayerJoinClientRequest request = new PlayerJoinClientRequest();
        request.setNetworkProtocolVersion(networkProtocolVersion);
        request.setName(name);
        request.setRole(role);
        return request;
    }

    public static PlayerUpdateClientRequest createPlayerUpdateRequest(Player player) {
        Vector2 position = getPosition(player);
        Vector2 velocity = getVelocity(player);
        return new PlayerUpdateClientRequest(position.x, position.y, velocity.x, velocity.y);
    }

    public static BallUpdateClientRequest createBallUpdateClientRequest(Ball ball) {
        Vector2 position = getPosition(ball);
        Vector2 velocity = getVelocity(ball);
        return new BallUpdateClientRequest(position.x, position.y, velocity.x, velocity.y);
    }

    public static MapLoadClientRequest createMapLoadClientRequest(FileHandle mapFile) {
        return new MapLoadClientRequest(
                CompressionUtil.compress(mapFile.readString()));
    }

    public static PlayerChangeTeamClientRequest createPlayerChangeTeamClientRequest(Integer id, TeamType type) {
        return new PlayerChangeTeamClientRequest(id, type);
    }

    public static GameStartClientRequest createGameStartClientRequest() {
        return new GameStartClientRequest();
    }

    public static SelectBoxUpdateClientRequest createSelectBoxUpdateClientRequest(String selectBoxName, Integer selectedIndex) {
        return new SelectBoxUpdateClientRequest(selectBoxName, selectedIndex);
    }

    public static PlayerKickBallClientRequest createPlayerKickBallClientRequest(Integer playerId, Vector2 force, Vector2 point) {
        return new PlayerKickBallClientRequest(playerId, force.x, force.y, point.x, point.y);
    }

    public static GateContactClientRequest createGateContactClientRequest(TeamType teamType) {
        return new GateContactClientRequest(teamType);
    }

    public static PlayerKeyPressClientRequest createPlayerKeyPressClientRequest(Integer playerId) {
        return new PlayerKeyPressClientRequest(playerId);
    }

    public static BallContactRequest createBallContactRequest() {
        return new BallContactRequest();
    }

    private static Vector2 getPosition(Entity entity) {
        return entity.getPhysicsEntity().getBody().getPosition();
    }

    private static Vector2 getVelocity(Entity entity) {
        return entity.getPhysicsEntity().getBody().getLinearVelocity();
    }
}
