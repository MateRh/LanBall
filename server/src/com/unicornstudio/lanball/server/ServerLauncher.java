package com.unicornstudio.lanball.server;

import com.unicornstudio.lanball.network.model.Ports;
import com.unicornstudio.lanball.server.rest.PublicIpClient;
import com.unicornstudio.lanball.server.service.ArgumentsService;
import com.unicornstudio.lanball.server.service.ServerService;
import com.unicornstudio.lanball.server.util.LogUtil;

import java.util.Arrays;

public class ServerLauncher {


    public static void main(String[] arg) {
        int port = Ports.getList().stream().findFirst().orElse(0);
        LogUtil.printConsole("Starting server with port: " + port);
        new ServerService().start(port);
        new PublicIpClient().asyncPrintPublicIp(port);
        System.out.println(Arrays.toString(arg));
        new ArgumentsService().parse(arg);
    }

}
