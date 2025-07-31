package cloud.terium.teriumapi.service.group;

import cloud.terium.common.networking.packet.group.PacketPlayOutCreateLobbyGroup;
import cloud.terium.common.networking.packet.group.PacketPlayOutCreateProxyGroup;
import cloud.terium.common.networking.packet.group.PacketPlayOutCreateServerGroup;
import cloud.terium.common.services.groups.ICloudServiceGroup;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.node.INode;
import cloud.terium.common.services.ServiceType;
import cloud.terium.common.services.impl.DefaultLobbyGroup;
import cloud.terium.common.services.impl.DefaultProxyGroup;
import cloud.terium.common.services.impl.DefaultServerGroup;
import cloud.terium.common.templates.ITemplate;

import java.util.List;

public class ServiceGroupBuilder {

    private final String name;
    private final ServiceType serviceType;
    private String groupTitle = "Default service group";
    private INode node = TeriumAPI.getTeriumFramework().getProvider().getThisNode();
    private List<ITemplate> templates;
    private String version = "paper-1.19.3";
    private int port = 0;
    private boolean maintenance = true;
    private boolean isStatic = false;
    private int maximumPlayers = 20;
    private int memory = 512;
    private int minimalServices = 1;
    private int maximalServices = 2;

    public ServiceGroupBuilder(String name, ServiceType serviceType) {
        this.name = name;
        this.serviceType = serviceType;
    }

    public ServiceGroupBuilder setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
        return this;
    }

    public ServiceGroupBuilder setNode(INode node) {
        this.node = node;
        return this;
    }

    public ServiceGroupBuilder setTemplates(List<ITemplate> templates) {
        this.templates = templates;
        return this;
    }

    public ServiceGroupBuilder setVersion(String version) {
        this.version = version;
        return this;
    }

    public ServiceGroupBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public ServiceGroupBuilder setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
        return this;
    }

    public ServiceGroupBuilder setStatic(boolean aStatic) {
        this.isStatic = aStatic;
        return this;
    }

    public ServiceGroupBuilder setMaximumPlayers(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
        return this;
    }

    public ServiceGroupBuilder setMemory(int memory) {
        this.memory = memory;
        return this;
    }

    public ServiceGroupBuilder setMinimalServices(int minimalServices) {
        this.minimalServices = minimalServices;
        return this;
    }

    public ServiceGroupBuilder setMaximalServices(int maximalServices) {
        this.maximalServices = maximalServices;
        return this;
    }

    public ICloudServiceGroup build() {
        if (name == null)
            throw new NullPointerException("name cannot be null");
        if (serviceType == null)
            throw new NullPointerException("cloud service type cannot be null");
        if(templates == null)
            templates = List.of(TeriumAPI.getTeriumFramework().getFactory().getTemplateFactory().createTemplate(name));

        ICloudServiceGroup cloudServiceGroup = null;
        switch (serviceType) {
            case Proxy -> {
                TeriumAPI.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateProxyGroup(name, groupTitle, node.getName(), templates.stream().map(ITemplate::getName).toList(), version, maintenance, isStatic, port, maximumPlayers, memory, minimalServices, maximalServices));
                cloudServiceGroup = new DefaultProxyGroup(name, groupTitle, node, templates, version, maintenance, isStatic, port, maximumPlayers, memory, minimalServices, maximalServices);
            }
            case Lobby -> {
                TeriumAPI.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateLobbyGroup(name, groupTitle, node.getName(), templates.stream().map(ITemplate::getName).toList(), version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices));
                cloudServiceGroup = new DefaultLobbyGroup(name, groupTitle, node, templates, version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices);
            }
            case Server -> {
                TeriumAPI.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCreateServerGroup(name, groupTitle, node.getName(), templates.stream().map(ITemplate::getName).toList(), version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices));
                cloudServiceGroup = new DefaultServerGroup(name, groupTitle, node, templates, version, maintenance, isStatic, maximumPlayers, memory, minimalServices, maximalServices);
            }
        }
        return cloudServiceGroup;
    }
}