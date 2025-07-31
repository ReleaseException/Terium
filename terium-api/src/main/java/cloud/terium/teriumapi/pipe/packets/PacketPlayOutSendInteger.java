package cloud.terium.teriumapi.pipe.packets;


import cloud.terium.common.networking.Packet;

public record PacketPlayOutSendInteger(int integer) implements Packet {
}
