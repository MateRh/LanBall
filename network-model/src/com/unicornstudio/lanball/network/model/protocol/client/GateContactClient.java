package com.unicornstudio.lanball.network.model.protocol.client;

import com.unicornstudio.lanball.network.model.enumeration.TeamType;
import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GateContactClient implements NetworkObject {

    private TeamType teamType;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.GATE_CONTACT;
    }

}
