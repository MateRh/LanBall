package com.unicornstudio.lanball.network.protocol.request;

import com.unicornstudio.lanball.network.common.NetworkObject;
import com.unicornstudio.lanball.network.common.NetworkObjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectBoxUpdateClientRequest implements NetworkObject {

    private String selectBoxName;

    private Integer selectedIndex;

    @Override
    public NetworkObjectType getType() {
        return NetworkObjectType.SELECT_BOX_UPDATE;
    }
}
