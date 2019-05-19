package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.model.TeamType;
import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GateContactClientRequest implements NetworkObject {

    private TeamType teamType;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.GATE_CONTACT;
    }

}
