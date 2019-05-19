package com.unicornstudio.lanball.network.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.common.Ports;
import com.unicornstudio.lanball.network.common.NetworkClassRegisterer;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.dto.Host;
import com.unicornstudio.lanball.network.server.dto.PlayerRole;
import com.unicornstudio.lanball.prefernces.SettingsKeys;
import com.unicornstudio.lanball.prefernces.SettingsType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class ClientService {

    private final static String SEPARATOR = ":";
    private final static int CONNECTION_TIMEOUT = 5000;
    private final static int DISCOVERY_TIMEOUT = 100;

    @Inject
    private ClientListener clientListener;

    @Getter
    private Client client;

    @Getter
    private boolean host = false;

    public ClientService() {
        client = new Client();
        NetworkClassRegisterer.register(client.getKryo());
        Log.set(Log.LEVEL_ERROR);
    }

    public boolean connect(String address, PlayerRole role) {
        String[] hostArray = StringUtils.split(address, SEPARATOR);
        if (hostArray.length > 1) {
            return connect(hostArray[0], Integer.parseInt(hostArray[1]), role);
        }
        return false;
    }

    public void disconnect() {
        if (isConnected()) {
            client.stop();
        }
    }

    public List<Host> getServers() {
        return Ports.getList().parallelStream()
                .map(this::scanPort)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void sendRequest(NetworkObject networkObject) {
        client.sendUDP(networkObject);
    }

    public void sendRequestTCP(NetworkObject networkObject) {
        client.sendTCP(networkObject);
    }

    private Host scanPort(int port) {
        return Optional.ofNullable(client.discoverHost(port, DISCOVERY_TIMEOUT))
                .map(address -> new Host(address.getHostAddress() + SEPARATOR + port, address.getHostName()))
                .orElse(null);
    }

    private boolean connect(String host, int port, PlayerRole role) {
        client.start();
        client.addListener(clientListener);
        try {
            client.connect(CONNECTION_TIMEOUT, host, port, port);
        } catch (IOException e) {
            return false;
        }
        sendJoinReportRequest(role);
        return true;
    }

    public void sendJoinReportRequest(PlayerRole role) {
        if (PlayerRole.HOST.equals(role)) {
            host = true;
        }
        sendRequest(ClientRequestBuilder.createPlayerJoinRequest(SettingsType.GLOABL.getPreference().getString(SettingsKeys.NICKNAME), role));
    }

}
