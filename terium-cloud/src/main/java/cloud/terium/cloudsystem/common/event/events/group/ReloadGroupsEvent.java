package cloud.terium.cloudsystem.common.event.events.group;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.group.PacketPlayOutGroupsReload;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;

public class ReloadGroupsEvent extends Event {

    public ReloadGroupsEvent() {
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutGroupsReload());
    }
}