package cloud.terium.common.networking.packet;

import cloud.terium.common.networking.Packet;

public record PacketPlayOutCheckVersion(String version) implements Packet {
}