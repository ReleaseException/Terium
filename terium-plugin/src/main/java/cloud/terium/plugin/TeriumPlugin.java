package cloud.terium.plugin;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.PacketPlayOutCheckVersion;
import cloud.terium.common.networking.packet.service.PacketPlayOutSuccessfullyServiceStarted;
import cloud.terium.common.services.*;
import cloud.terium.plugin.impl.config.ConfigManager;
import cloud.terium.plugin.impl.console.CommandFactory;
import cloud.terium.plugin.impl.console.ConsoleProvider;
import cloud.terium.plugin.impl.event.EventProvider;
import cloud.terium.plugin.impl.module.ModuleProvider;
import cloud.terium.plugin.impl.node.NodeProvider;
import cloud.terium.plugin.impl.pipe.TeriumNetworking;
import cloud.terium.plugin.impl.entity.CloudPlayerProvider;
import cloud.terium.plugin.impl.service.ServiceFactory;
import cloud.terium.plugin.impl.service.ServiceProvider;
import cloud.terium.plugin.impl.service.group.ServiceGroupFactory;
import cloud.terium.plugin.impl.service.group.ServiceGroupProvider;
import cloud.terium.plugin.impl.template.TemplateFactory;
import cloud.terium.plugin.impl.template.TemplateProvider;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.ICloudFactory;
import cloud.terium.common.ICloudProvider;
import cloud.terium.common.command.IConsoleProvider;
import cloud.terium.common.command.ICommandFactory;
import cloud.terium.common.event.IEventProvider;
import cloud.terium.common.module.IModuleProvider;
import cloud.terium.common.networking.IDefaultTeriumNetworking;
import cloud.terium.common.node.INode;
import cloud.terium.common.node.INodeProvider;
import cloud.terium.common.player.ICloudPlayerProvider;
import cloud.terium.common.services.groups.ICloudServiceGroupFactory;
import cloud.terium.common.services.groups.ICloudServiceGroupProvider;
import cloud.terium.common.templates.ITemplateFactory;
import cloud.terium.common.templates.ITemplateProvider;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Getter
public final class TeriumPlugin extends TeriumAPI {

    @Getter
    private static TeriumPlugin instance;
    private final ConfigManager configManager;
    // Service
    private final ServiceFactory serviceFactory;
    private final ServiceProvider serviceProvider;
    // Service group
    private final ServiceGroupFactory serviceGroupFactory;
    private final ServiceGroupProvider serviceGroupProvider;
    // Node
    private final NodeProvider nodeProvider;
    // Template
    private final TemplateFactory templateFactory;
    private final TemplateProvider templateProvider;
    // Console
    private final ConsoleProvider consoleProvider;
    private final CommandFactory commandFactory;
    // Player
    private final CloudPlayerProvider cloudPlayerProvider;
    // Network
    private final TeriumNetworking teriumNetworking;
    // Event
    private final EventProvider eventProvider;
    // Module
    private final ModuleProvider moduleProvider;
    // Utils
    private final String thisName;

    public TeriumPlugin() {
        super();
        instance = this;
        this.configManager = new ConfigManager();
        this.teriumNetworking = new TeriumNetworking();
        this.serviceFactory = new ServiceFactory();
        this.serviceProvider = new ServiceProvider();
        this.serviceGroupFactory = new ServiceGroupFactory();
        this.serviceGroupProvider = new ServiceGroupProvider();
        this.nodeProvider = new NodeProvider();
        this.templateFactory = new TemplateFactory();
        this.templateProvider = new TemplateProvider();
        this.consoleProvider = new ConsoleProvider();
        this.commandFactory = new CommandFactory();
        this.cloudPlayerProvider = new CloudPlayerProvider();
        this.eventProvider = new EventProvider();
        this.moduleProvider = new ModuleProvider();

        thisName = System.getProperty("servicename");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                teriumNetworking.sendPacket(new PacketPlayOutSuccessfullyServiceStarted(getProvider().getThisService().getServiceName(), getProvider().getThisNode().getName()));
                teriumNetworking.sendPacket(new PacketPlayOutCheckVersion(getProvider().getVersion()));
                TeriumCommon.getTeriumFramework().getProvider().getThisService().setServiceState(ServiceState.ONLINE);
                TeriumCommon.getTeriumFramework().getProvider().getThisService().update();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getProvider().getThisService().setUsedMemory(usedMemory());
                        getProvider().getThisService().update();
                    }
                }, 0, 2000);
            }
        }, 1500);
    }

    @Override
    public ICloudProvider getProvider() {
        return new ICloudProvider() {
            @Override
            public ICloudService getThisService() {
                return getServiceProvider().getServiceByName(thisName).orElseGet(null);
            }

            @Override
            public INode getThisNode() {
                return getNodeProvider().getNodeByName(System.getProperty("servicenode")).orElseGet(null);
            }

            @Override
            public ICloudServiceProvider getServiceProvider() {
                return serviceProvider;
            }

            @Override
            public ICloudServiceGroupProvider getServiceGroupProvider() {
                return serviceGroupProvider;
            }

            @Override
            public ICloudPlayerProvider getCloudPlayerProvider() {
                return cloudPlayerProvider;
            }

            @Override
            public IConsoleProvider getConsoleProvider() {
                return consoleProvider;
            }

            @Override
            public IEventProvider getEventProvider() {
                return eventProvider;
            }

            @Override
            public IModuleProvider getModuleProvider() {
                return moduleProvider;
            }

            @Override
            public INodeProvider getNodeProvider() {
                return nodeProvider;
            }

            @Override
            public ITemplateProvider getTemplateProvider() {
                return templateProvider;
            }

            @Override
            public IDefaultTeriumNetworking getTeriumNetworking() {
                return teriumNetworking;
            }

            @Override
            public String getVersion() {
                return "1.5-OXYGEN";
            }
        };
    }

    @Override
    public ICloudFactory getFactory() {
        return new ICloudFactory() {
            @Override
            public ICloudServiceFactory getServiceFactory() {
                return serviceFactory;
            }

            @Override
            public ICloudServiceGroupFactory getServiceGroupFactory() {
                return serviceGroupFactory;
            }

            @Override
            public ITemplateFactory getTemplateFactory() {
                return templateFactory;
            }

            @Override
            public ICommandFactory getCommandFactory() {
                return commandFactory;
            }
        };
    }

    public long usedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
    }

    public @NotNull Optional<ICloudService> getFallback(final Player player) {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getAllServices().stream()
                .filter(service -> service.getServiceState().equals(ServiceState.ONLINE))
                .filter(service -> !service.getServiceGroup().getServiceType().equals(ServiceType.Proxy))
                .filter(service -> service.getServiceGroup().getServiceType().equals(ServiceType.Lobby))
                .filter(service -> !service.isLocked())
                .filter(service -> (player.getCurrentServer().isEmpty()
                        || !player.getCurrentServer().get().getServerInfo().getName().equals(service.getServiceName())))
                .min(Comparator.comparing(ICloudService::getOnlinePlayers));
    }

    public String getPrefix() {
        return configManager.getJson().get("prefix").getAsString();
    }
}