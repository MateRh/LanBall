package com.unicornstudio.lanball.network.protocol;

import com.unicornstudio.lanball.network.common.NetworkObject;
import lombok.Data;

@Data
public class JoinReportNetworkObject implements NetworkObject {

    private String name;

}
