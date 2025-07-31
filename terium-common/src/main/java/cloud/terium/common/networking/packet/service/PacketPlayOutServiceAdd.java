package cloud.terium.common.networking.packet.service;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.node.INode;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import cloud.terium.common.templates.ITemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public record PacketPlayOutServiceAdd(String serviceName, int serviceId, int port, int maxPlayers, int memory,
                                      String node, String serviceGroup, List<String> templates,
                                      HashMap<String, Object> propertyCache) implements Packet {

    public Optional<ICloudServiceGroup> parsedServiceGroup() {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceGroupProvider().getServiceGroupByName(serviceGroup);
    }

    public Optional<INode> parsedNode() {
        return TeriumCommon.getTeriumFramework().getProvider().getNodeProvider().getNodeByName(node);
    }

    public List<ITemplate> parsedTemplates() {
        return TeriumCommon.getTeriumFramework().getProvider().getTemplateProvider().getAllTemplates().stream().map(ITemplate::getName).filter(templates::contains).toList().stream().map(s -> TeriumCommon.getTeriumFramework().getProvider().getTemplateProvider().getTemplateByName(s).orElseGet(null)).toList();
    }
}