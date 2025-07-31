package cloud.terium.cloudsystem.node.service.group;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.group.PacketPlayOutCreateLobbyGroup;
import cloud.terium.common.networking.packet.group.PacketPlayOutCreateProxyGroup;
import cloud.terium.common.networking.packet.group.PacketPlayOutCreateServerGroup;
import cloud.terium.common.networking.packet.group.PacketPlayOutGroupDelete;
import cloud.terium.common.node.INode;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import cloud.terium.common.templates.ITemplate;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.services.groups.ICloudServiceGroupFactory;
import cloud.terium.common.services.impl.DefaultLobbyGroup;
import cloud.terium.common.services.impl.DefaultProxyGroup;
import cloud.terium.common.services.impl.DefaultServerGroup;

import java.util.List;

public class ServiceGroupFactory implements ICloudServiceGroupFactory {

    @Override
    public ICloudServiceGroup createLobbyGroup(String name, String groupTitle, INode node, List<ITemplate> templates, String version, boolean maintenance, boolean isStatic, int maximumPlayers, int memory, int minimalServices, int maximalServices) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateLobbyGroup(name, groupTitle, node.getName(), templates.stream().map(ITemplate::getName).toList(), version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices));
        return new DefaultLobbyGroup(name, groupTitle, node, templates, version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices);
    }

    @Override
    public ICloudServiceGroup createProxyGroup(String name, String groupTitle, INode node, List<ITemplate> templates, String version, boolean maintenance, boolean isStatic, int port, int maximumPlayers, int memory, int minimalServices, int maximalServices) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateProxyGroup(name, groupTitle, node.getName(), templates.stream().map(ITemplate::getName).toList(), version, maintenance, isStatic, port, maximumPlayers, memory, minimalServices, maximalServices));
        return new DefaultProxyGroup(name, groupTitle, node, templates, version, maintenance, isStatic, port, maximumPlayers, memory, minimalServices, maximalServices);
    }

    @Override
    public ICloudServiceGroup createServerGroup(String name, String groupTitle, INode node, List<ITemplate> templates, String version, boolean maintenance, boolean isStatic, int maximumPlayers, int memory, int minimalServices, int maximalServices) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateServerGroup(name, groupTitle, node.getName(), templates.stream().map(ITemplate::getName).toList(), version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices));
        return new DefaultServerGroup(name, groupTitle, node, templates, version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices);
    }

    @Override
    public void deleteServiceGroup(ICloudServiceGroup cloudServiceGroup) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutGroupDelete(cloudServiceGroup.getGroupName()));
    }
}
