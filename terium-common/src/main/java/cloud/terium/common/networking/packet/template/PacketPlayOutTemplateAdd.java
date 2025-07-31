package cloud.terium.common.networking.packet.template;

import cloud.terium.common.networking.Packet;

public record PacketPlayOutTemplateAdd(String name, String path) implements Packet {
}