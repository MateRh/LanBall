package com.unicornstudio.lanball.network.model.protocol.client;

import com.unicornstudio.lanball.network.model.protocol.NetworkObject;
import com.unicornstudio.lanball.network.model.protocol.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectBoxUpdateClient implements NetworkObject {

    private String selectBoxName;

    private Integer selectedIndex;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.SELECT_BOX_UPDATE;
    }
}
