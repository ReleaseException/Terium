package cloud.terium.plugin.impl.console;

import cloud.terium.common.TeriumCommon;
import cloud.terium.common.networking.packet.console.PacketPlayOutSendConsole;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.command.IConsoleProvider;
import cloud.terium.common.command.LogType;

public class ConsoleProvider implements IConsoleProvider {

    @Override
    public void sendConsole(String message, LogType logType) {
        TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutSendConsole(message, logType));
    }
}
