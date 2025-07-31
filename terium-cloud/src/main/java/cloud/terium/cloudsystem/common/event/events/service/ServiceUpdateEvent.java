package cloud.terium.cloudsystem.common.event.events.service;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.service.PacketPlayOutUpdateService;
import cloud.terium.common.services.ServiceState;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class ServiceUpdateEvent extends Event {

    private final String cloudService;
    private final int players;
    private final double memory;
    private final ServiceState serviceState;
    private final boolean locked;
    private final HashMap<String, Object> propertyCache;

    public ServiceUpdateEvent(String cloudService, int players, double memory, ServiceState serviceState, boolean locked, HashMap<String, Object> propertyCache) {
        this.cloudService = cloudService;
        this.players = players;
        this.memory = memory;
        this.serviceState = serviceState;
        this.locked = locked;
        this.propertyCache = propertyCache;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutUpdateService(cloudService, players, memory, serviceState, locked, propertyCache));
    }
}