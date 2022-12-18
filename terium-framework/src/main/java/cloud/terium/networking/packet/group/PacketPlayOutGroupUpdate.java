package cloud.terium.networking.packet.group;

import cloud.terium.teriumapi.network.Packet;

public record PacketPlayOutGroupUpdate(String servicegroup, boolean maintenance, int maxPlayers,
                                       int memory, int minServices, int maxServices) implements Packet {
}