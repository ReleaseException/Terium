package cloud.terium.common.networking.packet.console;

import cloud.terium.common.command.Command;
import cloud.terium.common.networking.Packet;

public record PacketPlayOutRegisterCommand(Command command) implements Packet {

    @Override
    public Command command() {
        return new Command(command.getCommand(), command.getDescription(), command.getAliases()) {
            @Override
            public void execute(String[] args) {
                command.execute(args);
            }
        };
    }
}