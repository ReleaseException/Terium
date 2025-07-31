package cloud.terium.common.networking.packet.service;


import cloud.terium.common.networking.Packet;

public record PacketPlayOutServiceExecuteCommand(String cloudService, String command) implements Packet {
}