package cloud.terium.common.networking.packet.player;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.player.ICloudPlayer;
import cloud.terium.common.services.ICloudService;

import java.util.Optional;
import java.util.UUID;

public record PacketPlayOutCloudPlayerConnect(UUID cloudPlayer, String cloudService) implements Packet {

    public Optional<ICloudPlayer> parsedCloudPlayer() {
        return TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getCloudPlayer(cloudPlayer);
    }

    public Optional<ICloudService> parsedCloudService() {
        return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(cloudService);
    }
}