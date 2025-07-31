package cloud.terium.cloudsystem.common.event.events.node;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.node.PacketPlayOutNodeShutdowned;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class NodeShutdownedEvent extends Event {

    private final String node;

    public NodeShutdownedEvent(String node) {
        this.node = node;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutNodeShutdowned(node));
    }
}
