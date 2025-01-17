package cloud.terium.cloudsystem.node.service;

import cloud.terium.cloudsystem.TeriumCloud;
import cloud.terium.cloudsystem.node.NodeStartup;
import cloud.terium.networking.packet.node.PacketPlayOutNodeUpdate;
import cloud.terium.teriumapi.service.ICloudService;
import cloud.terium.teriumapi.service.ICloudServiceProvider;
import cloud.terium.teriumapi.service.ServiceState;
import cloud.terium.teriumapi.service.ServiceType;
import cloud.terium.teriumapi.service.group.ICloudServiceGroup;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CloudServiceProvider implements ICloudServiceProvider {

    private final HashMap<String, ICloudService> cloudServiceCache;
    private final HashMap<ICloudServiceGroup, List<Integer>> cloudServiceIdCache;

    public CloudServiceProvider() {
        this.cloudServiceCache = new LinkedHashMap<>();
        this.cloudServiceIdCache = new LinkedHashMap<>();
    }

    public void startServiceCheck() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (TeriumCloud.getTerium().getCloudUtils().isRunning() && gloablUsedMemory() < NodeStartup.getNode().getNodeConfig().memory()) {
                    NodeStartup.getNode().getServiceGroupProvider().getAllServiceGroups().stream().filter(serviceGroup -> serviceGroup.getGroupNode().getName().equals(NodeStartup.getNode().getProvider().getThisNode().getName())).forEach(group -> {
                        if (getServicesByGroupName(group.getGroupName()).size() < group.getMaxServices() &&
                                getServicesByGroupName(group.getGroupName()).stream().filter(iCloudService -> iCloudService.getServiceState().equals(ServiceState.ONLINE) ||
                                        iCloudService.getServiceState().equals(ServiceState.PREPARING)).toList().size() < group.getMinServices()) {
                            NodeStartup.getNode().getServiceFactory().createService(group);
                        }
                    });
                }

                NodeStartup.getNode().getThisNode().setUsedMemory(gloablUsedMemory());
                NodeStartup.getNode().getThisNode().update();
            }
        }, 0, 1000);
    }

    public void startServiceStopCheck() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (TeriumCloud.getTerium().getCloudUtils().isRunning()) {
                    NodeStartup.getNode().getServiceGroupProvider().getAllServiceGroups().forEach(group -> {
                        if (getServicesByGroupName(group.getGroupName()).size() > group.getMinServices()) {
                            getServicesByGroupName(group.getGroupName()).stream().filter(iCloudService -> iCloudService.getServiceState().equals(ServiceState.ONLINE) && iCloudService.getOnlinePlayers() == 0).sorted(Comparator.comparing(ICloudService::getServiceId).reversed()).findFirst().ifPresent(ICloudService::shutdown);
                        }
                    });
                }
            }
        }, 0, 60000);
    }

    public int getFreeServiceId(ICloudServiceGroup cloudServiceGroup) {
        AtomicInteger integer = new AtomicInteger(1);
        for (int i = 0; i < getServicesByGroupName(cloudServiceGroup.getGroupName()).size(); i++) {
            if (cloudServiceIdCache.get(cloudServiceGroup).contains(integer.get())) {
                integer.getAndIncrement();
            }
        }
        return integer.get();
    }

    public void createEmptyListForGroup(ICloudServiceGroup serviceGroup) {
        cloudServiceIdCache.put(serviceGroup, new LinkedList<>());
    }

    public void putServiceId(ICloudServiceGroup serviceGroup, int id) {
        cloudServiceIdCache.get(serviceGroup).add(id);
    }

    public void removeServiceId(ICloudServiceGroup serviceGroup, int id) {
        cloudServiceIdCache.get(serviceGroup).remove((Object) id);
    }

    public long gloablUsedMemory() {
        AtomicLong globalUsedMemory = new AtomicLong();
        NodeStartup.getNode().getServiceProvider().getAllServices().stream().filter(cloudService -> cloudService.getServiceNode().getName().equals(NodeStartup.getNode().getThisNode().getName())).forEach(cloudService -> globalUsedMemory.getAndAdd(cloudService.getMaxMemory()));

        return globalUsedMemory.get();
    }

    public void addService(ICloudService cloudService) {
        if (!cloudServiceCache.containsKey(cloudService.getServiceName()))
            cloudServiceCache.put(cloudService.getServiceName(), cloudService);
    }

    public void removeService(ICloudService cloudService) {
        cloudServiceCache.remove(cloudService.getServiceName(), cloudService);
    }

    public HashMap<ICloudServiceGroup, List<Integer>> getCloudServiceIdCache() {
        return cloudServiceIdCache;
    }

    @Override
    public Optional<ICloudService> getServiceByName(String serviceName) {
        return Optional.ofNullable(cloudServiceCache.get(serviceName));
    }

    @Override
    public List<ICloudService> getServicesByGroupName(String serviceGroup) {
        return cloudServiceCache.values().stream().filter(cloudService -> cloudService.getServiceGroup().getGroupName().equals(serviceGroup)).toList();
    }

    @Override
    public List<ICloudService> getServicesByGroupTitle(String groupTitle) {
        return cloudServiceCache.values().stream().filter(cloudService -> cloudService.getServiceGroup().getGroupTitle().equals(groupTitle)).toList();
    }

    @Override
    public List<ICloudService> getAllLobbyServices() {
        return cloudServiceCache.values().stream().filter(cloudService -> cloudService.getServiceGroup().getServiceType().equals(ServiceType.Lobby)).toList();
    }

    @Override
    public List<ICloudService> getAllServices() {
        return cloudServiceCache.values().stream().toList();
    }
}
