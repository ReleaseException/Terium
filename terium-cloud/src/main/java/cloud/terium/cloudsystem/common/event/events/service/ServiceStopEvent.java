package cloud.terium.cloudsystem.common.event.events.service;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.service.PacketPlayOutServiceShutdown;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class ServiceStopEvent extends Event {

    private final String cloudService;
    private final String node;

    public ServiceStopEvent(String cloudService, String node) {
        this.cloudService = cloudService;
        this.node = node;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutServiceShutdown(cloudService, node));
    }
}