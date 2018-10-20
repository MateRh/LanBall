package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import lombok.Data;

@Data
public class PlayerUpdateNetworkObject implements NetworkObject {

    private Float positionX;

    private Float positionY;

    private Float velocityX;

    private Float velocityY;

}
