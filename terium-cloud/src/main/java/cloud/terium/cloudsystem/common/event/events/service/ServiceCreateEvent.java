package cloud.terium.cloudsystem.common.event.events.service;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.service.PacketPlayOutCreateService;
import cloud.terium.common.node.INode;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import cloud.terium.common.templates.ITemplate;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Getter
public class ServiceCreateEvent extends Event {

    private final String name;
    private final INode node;
    private final ICloudServiceGroup serviceGroup;
    private final List<ITemplate> templates;
    private final HashMap<String, Object> propertyCache;
    private final int maxPlayers;
    private final int memory;
    private final int serviceId;
    private final int port;
    private final String type;

    public ServiceCreateEvent(String serviceName, int serviceId, int port, int maxPlayers, int memory, INode node, ICloudServiceGroup serviceGroup, List<ITemplate> templates, HashMap<String, Object> propertyCache, String type) {
        this.name = serviceName;
        this.node = node;
        this.serviceGroup = serviceGroup;
        this.templates = templates;
        this.propertyCache = propertyCache;
        this.maxPlayers = maxPlayers;
        this.memory = memory;
        this.serviceId = serviceId;
        this.port = port;
        this.type = type;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, serviceId, port, maxPlayers, memory, node.getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), propertyCache, type));
    }
}