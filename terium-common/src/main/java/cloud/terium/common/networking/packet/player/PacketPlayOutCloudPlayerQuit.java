package cloud.terium.common.networking.packet.player;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.Packet;
import cloud.terium.common.player.ICloudPlayer;

import java.util.Optional;
import java.util.UUID;

public record PacketPlayOutCloudPlayerQuit(UUID cloudPlayer) implements Packet {

    public Optional<ICloudPlayer> parsedCloudPlayer() {
        return TeriumCommon.getTeriumFramework().getProvider().getCloudPlayerProvider().getCloudPlayer(cloudPlayer);
    }
}