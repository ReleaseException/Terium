package cloud.terium.teriumapi.pipe.packets;

import cloud.terium.common.networking.Packet;

public record PacketPlayOutSendLong(long integer) implements Packet {
}
