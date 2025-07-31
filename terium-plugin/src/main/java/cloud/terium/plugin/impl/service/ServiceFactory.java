package cloud.terium.plugin.impl.service;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.service.PacketPlayOutCreateService;
import cloud.terium.common.services.ICloudServiceFactory;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import cloud.terium.common.templates.ITemplate;

import java.util.HashMap;
import java.util.List;

public class ServiceFactory implements ICloudServiceFactory {

    @Override
    public void createService(ICloudServiceGroup serviceGroup) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceGroup.getGroupName(), -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), serviceGroup.getTemplates().stream().map(ITemplate::getName).toList(), new HashMap<>(), "group_only"));
    }

    @Override
    public void createService(ICloudServiceGroup serviceGroup, List<ITemplate> templates) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceGroup.getGroupName(), -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), new HashMap<>(), "group_with_templates"));
    }

    @Override
    public void createService(String serviceName, ICloudServiceGroup serviceGroup, List<ITemplate> templates, int serviceId, int maxPlayers, int memory) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, serviceId, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, maxPlayers, memory, serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), new HashMap<>(), "full"));
    }

    @Override
    public void createService(String serviceName, ICloudServiceGroup serviceGroup, List<ITemplate> templates, int serviceId, int maxPlayers, int memory, HashMap<String, Object> hashMap) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, serviceId, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, maxPlayers, memory, serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), hashMap, "full"));
    }

    @Override
    public void createService(String serviceName, ICloudServiceGroup serviceGroup) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), serviceGroup.getTemplates().stream().map(ITemplate::getName).toList(), new HashMap<>(), "group_with_custom_name"));
    }

    @Override
    public void createService(String serviceName, ICloudServiceGroup serviceGroup, List<ITemplate> templates) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateService(serviceName, -1, serviceGroup.hasPort() ? serviceGroup.getPort() : -1, serviceGroup.getMaxPlayers(), serviceGroup.getMemory(), serviceGroup.getGroupNode().getName(), serviceGroup.getGroupName(), templates.stream().map(ITemplate::getName).toList(), new HashMap<>(), "group_template_and_custom_name"));
    }
}
