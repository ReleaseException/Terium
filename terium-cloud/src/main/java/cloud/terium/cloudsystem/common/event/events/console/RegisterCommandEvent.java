package cloud.terium.cloudsystem.common.event.events.console;

import cloud.terium.cloudsystem.cluster.ClusterStartup;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.command.Command;
import cloud.terium.common.networking.packet.console.PacketPlayOutRegisterCommand;
import cloud.terium.teriumapi.TeriumAPI;
import cloud.terium.common.event.Event;
import lombok.Getter;

@Getter
public class RegisterCommandEvent extends Event {

    private final Command command;

    public RegisterCommandEvent(Command command) {
        this.command = command;
        if (ClusterStartup.getCluster() != null)
            TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutRegisterCommand(command));
    }
}