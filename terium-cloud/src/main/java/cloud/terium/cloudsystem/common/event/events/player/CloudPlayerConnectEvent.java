package cloud.terium.cloudsystem.common.event.events.player;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerConnect;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CloudPlayerConnectEvent extends Event {

    private final UUID cloudPlayer;
    private final String cloudService;

    public CloudPlayerConnectEvent(UUID cloudPlayer, String cloudService) {
        this.cloudPlayer = cloudPlayer;
        this.cloudService = cloudService;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerConnect(cloudPlayer, cloudService));
    }
}
