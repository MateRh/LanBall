package com.unicornstudio.lanball.network.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.client.ClientService;
import com.unicornstudio.lanball.network.common.NetworkClassRegisterer;
import com.unicornstudio.lanball.network.protocol.JoinReportNetworkObject;
import com.unicornstudio.lanball.network.protocol.request.PlayerUpdateNetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObject;
import lombok.Getter;

import java.io.IOException;
import java.util.stream.Stream;

@Singleton
public class ServerService {

    @Inject
    private ClientService clientService;

    private final Server server;

    private boolean running = false;

    @Getter
    private final ServerData data = new ServerData();

    public ServerService() {
        this.server = new Server();
        NetworkClassRegisterer.register(this.server.getKryo());
        bindEvents();
    }

    public void start(int port) {
        server.start();
        try {
            server.bind(port, port);
            running = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientService.connect("localhost:" + port);
    }

    public void stop() {
        if (running) {
            clientService.disconnect();
            server.stop();
        }
    }

    private void bindEvents() {
        server.addListener(getListener());
    }

    private Listener getListener() {
        return new Listener() {

            public void connected (Connection connection) {
                data.addPlayer(new Player(connection.getID()));
            }

            public void disconnected (Connection connection) {
                data.findById(connection.getID()).ifPresent(data::removePlayer);
            }

            public void received (Connection connection, Object object) {
                if (object instanceof JoinReportNetworkObject) {
                    JoinReportNetworkObject request = (JoinReportNetworkObject) object;
                    data.findById(connection.getID()).ifPresent(player -> player.setName(request.getName()));
                    sendCreateRemotePlayer(connection.getID());
                }
                if (object instanceof PlayerUpdateNetworkObject) {
                    PlayerUpdateNetworkObject request = (PlayerUpdateNetworkObject) object;
                    data.findById(connection.getID()).ifPresent(
                        player -> {
                            player.updatePositionAndVelocity(
                                    request.getPositionX(), request.getPositionY(), request.getVelocityX(), request.getVelocityY()
                            );
                            propagateData(ServerRequestBuilder.createMotionUpdateRequest(player), player);
                        }
                    );
                }
            }

        };
    }

    private void propagateData(NetworkObject networkObject, Player player) {
        data.getPlayers().stream()
                .filter(p -> !p.equals(player))
                .map(p -> mapPlayerToConnection(p.getId()))
                .forEach(connection -> send(connection, networkObject));
    }

    private void sendCreateRemotePlayer(int id) {
        data.findById(id).ifPresent(
            player -> {
                propagateData(ServerRequestBuilder.createRemotePlayerRequest(player), player);
            }
        );
    }

    private Connection mapPlayerToConnection(int id) {
        return getConnections().filter(connection -> connection.getID() == id).findAny().orElse(null);
    }

    private Stream<Connection> getConnections() {
        return Stream.of(server.getConnections());
    }

    private void send(Connection connection, NetworkObject networkObject) {
        if (connection != null) {
            connection.sendUDP(networkObject);
        }
    }

}
