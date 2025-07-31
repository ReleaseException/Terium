package cloud.terium.common.networking.packet.console;

import cloud.terium.common.command.LogType;
import cloud.terium.common.networking.Packet;

public record PacketPlayOutSendConsole(String message, LogType logType) implements Packet {
}