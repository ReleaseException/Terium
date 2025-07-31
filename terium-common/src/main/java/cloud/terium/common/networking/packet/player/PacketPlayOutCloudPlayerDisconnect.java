package cloud.terium.common.networking.packet.player;

import cloud.terium.common.networking.Packet;

import java.util.UUID;

public record PacketPlayOutCloudPlayerDisconnect(UUID cloudPlayer, String message) implements Packet {
}