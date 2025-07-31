package cloud.terium.cloudsystem.common.event.events.group;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.group.PacketPlayOutGroupUpdate;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class GroupUpdateEvent extends Event {

    private final String serviceGroup;

    public GroupUpdateEvent(String serviceGroup, String node,
                            String version,
                            int maximumPlayers,
                            boolean maintenance,
                            boolean isStatic,
                            int memory,
                            int minimalServices,
                            int maximalServices) {
        this.serviceGroup = serviceGroup;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutGroupUpdate(serviceGroup, node, version, maximumPlayers, maintenance, isStatic, memory, minimalServices, maximalServices));
    }
}