package cloud.terium.teriumapi.pipe.packets;

import cloud.terium.common.networking.Packet;

public record PacketPlayOutSendString(String string) implements Packet {
}
