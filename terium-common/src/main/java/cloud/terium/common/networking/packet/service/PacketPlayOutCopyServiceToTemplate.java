package cloud.terium.common.networking.packet.service;


import cloud.terium.common.networking.Packet;

public record PacketPlayOutCopyServiceToTemplate(String service, String template) implements Packet {
}