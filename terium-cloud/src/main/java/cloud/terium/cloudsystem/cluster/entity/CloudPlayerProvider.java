package cloud.terium.cloudsystem.cluster.entity;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerAdd;
import cloud.terium.common.networking.packet.player.PacketPlayOutCloudPlayerQuit;
import cloud.terium.common.player.ICloudPlayer;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.player.ICloudPlayerProvider;
import cloud.terium.teriumapi.entity.impl.CloudPlayer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CloudPlayerProvider implements ICloudPlayerProvider {

    private final List<ICloudPlayer> onlinePlayers = new ArrayList<>();

    public void registerPlayer(String username, UUID uniquedId, InetSocketAddress address, String value, String signature, String cloudService) {
        onlinePlayers.add(new CloudPlayer(username, uniquedId, address, value, signature, ClusterStartup.getCluster().getServiceProvider().getServiceByName(cloudService)));
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerAdd(username, uniquedId, address, value, signature, cloudService));
    }

    public void unregisterPlayer(ICloudPlayer cloudPlayer) {
        onlinePlayers.remove(cloudPlayer);
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutCloudPlayerQuit(cloudPlayer.getUniqueId()));
    }

    @Override
    public Optional<ICloudPlayer> getCloudPlayer(String username) {
        return onlinePlayers.stream().filter(player -> player.getUsername().equals(username)).toList().stream().findAny();
    }

    @Override
    public Optional<ICloudPlayer> getCloudPlayer(UUID uniqueId) {
        return onlinePlayers.stream().filter(player -> player.getUniqueId().equals(uniqueId)).toList().stream().findAny();
    }

    @Override
    public List<ICloudPlayer> getOnlinePlayers() {
        return onlinePlayers;
    }
}