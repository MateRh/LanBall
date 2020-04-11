package com.unicornstudio.lanball.network.model.protocol;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BallContact implements NetworkObject {

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.BALL_CONTACT;
    }

}
