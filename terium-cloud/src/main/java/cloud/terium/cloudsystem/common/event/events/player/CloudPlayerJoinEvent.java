package cloud.terium.cloudsystem.common.event.events.player;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerJoin;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CloudPlayerJoinEvent extends Event {

    private final UUID cloudPlayer;

    public CloudPlayerJoinEvent(UUID cloudPlayer) {
        this.cloudPlayer = cloudPlayer;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerJoin(cloudPlayer));
    }
}
