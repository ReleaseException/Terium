package cloud.terium.cloudsystem.cluster.console.commands;

import cloud.terium.cloudsystem.cluster.utils.Logger;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.command.Command;
import cloud.terium.common.command.LogType;
import cloud.terium.common.networking.packet.service.PacketPlayOutServiceExecuteCommand;
import cloud.terium.common.services.ICloudService;
import cloud.terium.teriumapi.TeriumAPI;

import java.util.List;

public class ExecuteCommand extends Command {

    public ExecuteCommand() {
        super("execute", "Run a command on a specific service");
    }

    @Override
    public void execute(String[] args) {
        if(args.length >= 2) {
            TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(args[0]).ifPresentOrElse(cloudService -> {
                StringBuilder builder = new StringBuilder();

                for (int i = 1; i != args.length; i++) {
                    builder.append(args[i]).append(" ");
                }

                TeriumCommon.getTeriumFramework().getProvider().getTeriumNetworking().sendPacket(new PacketPlayOutServiceExecuteCommand(cloudService.getServiceName(), builder.toString()));
            }, () -> Logger.log("Specific service not found.", LogType.ERROR));

            return;
        }

        Logger.log("execute [service] [command] | execute command on specific service", LogType.INFO);
    }

    @Override
    public List<String> tabComplete(String[] args) {
        if (args.length == 1) {
            return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getAllServices().stream().map(ICloudService::getServiceName).toList();
        }

        return super.tabComplete(args);
    }
}
