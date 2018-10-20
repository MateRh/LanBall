package com.unicornstudio.lanball.network.client;

import com.esotericsoftware.kryonet.Client;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.unicornstudio.lanball.network.common.Ports;
import com.unicornstudio.lanball.network.common.NetworkClassRegisterer;
import com.unicornstudio.lanball.network.protocol.JoinReportNetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObject;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class ClientService {

    @Inject
    private ClientListener clientListener;

    private final static String SEPARATOR = ":";

    @Getter
    private Client client;

    public ClientService() {
        client = new Client();
        client.start();
    }

    public void connect(String address) {
        String[] hostArray = StringUtils.split(address, SEPARATOR);
        if (hostArray.length > 1) {
            connect(hostArray[0], Integer.parseInt(hostArray[1]));
        }
    }

    public void disconnect() {
        if (isConnected()) {
            client.stop();
        }
    }

    public List<String> getServers() {
        return Ports.getList().parallelStream().map(this::scanPort).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void sendRequest(NetworkObject networkObject) {
        client.sendUDP(networkObject);
    }

    private String scanPort(int port) {
        return Optional.ofNullable(client.discoverHost(port, 50))
                .map(address -> address.getHostAddress() + SEPARATOR + port)
                .orElse(null);
    }

    private void connect(String host, int port) {
        client.start();
        try {
            client.connect(5000, host, port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        NetworkClassRegisterer.register(this.client.getKryo());
        sendJoinReportRequest();
        client.addListener(clientListener);
    }

    private void sendJoinReportRequest() {
        JoinReportNetworkObject request = new JoinReportNetworkObject();
        request.setName(RandomStringUtils.randomAlphabetic(8));
        sendRequest(request);
    }

}
