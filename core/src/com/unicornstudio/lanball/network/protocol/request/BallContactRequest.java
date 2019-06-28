package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BallContactRequest implements NetworkObject {

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.BALL_CONTACT;
    }

}
