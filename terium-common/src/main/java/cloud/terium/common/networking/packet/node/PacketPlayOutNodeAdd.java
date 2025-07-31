package cloud.terium.common.networking.packet.node;

import cloud.terium.common.networking.Packet;

import java.net.InetSocketAddress;

public record PacketPlayOutNodeAdd(String name, InetSocketAddress address, long memory,
                                   boolean connected) implements Packet {
}