package cloud.terium.cloudsystem.common.event.events.console;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.command.LogType;
import cloud.terium.common.networking.packet.console.PacketPlayOutSendConsole;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class SendConsoleEvent extends Event {

    private final String message;
    private final LogType logType;

    public SendConsoleEvent(String message, LogType logType) {
        this.message = message;
        this.logType = logType;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutSendConsole(message, logType));
    }
}