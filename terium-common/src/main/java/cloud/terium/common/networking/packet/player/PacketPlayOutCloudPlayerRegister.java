package cloud.terium.common.networking.packet.player;

import cloud.terium.common.networking.Packet;

import java.net.InetSocketAddress;
import java.util.UUID;

public record PacketPlayOutCloudPlayerRegister(String username, UUID uniquedId, InetSocketAddress address, String value,
                                               String signature, String cloudService) implements Packet {
}