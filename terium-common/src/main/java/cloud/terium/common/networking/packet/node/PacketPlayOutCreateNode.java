package cloud.terium.common.networking.packet.node;

import cloud.terium.common.networking.Packet;

import java.net.InetSocketAddress;

public record PacketPlayOutCreateNode(String name, String key, InetSocketAddress address) implements Packet {
}