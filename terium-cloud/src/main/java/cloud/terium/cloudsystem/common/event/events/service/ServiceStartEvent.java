package cloud.terium.cloudsystem.common.event.events.service;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.service.PacketPlayOutServiceStart;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class ServiceStartEvent extends Event {

    private final String cloudService;
    private final String node;

    public ServiceStartEvent(String cloudService, String node) {
        this.cloudService = cloudService;
        this.node = node;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutServiceStart(cloudService, cloudService));
    }
}