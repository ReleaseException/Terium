package cloud.terium.cloudsystem.cluster.console.commands;

import cloud.terium.cloudsystem.cluster.utils.Logger;
import cloud.terium.common.TeriumCommon;
import cloud.terium.common.command.Command;
import cloud.terium.common.command.LogType;
import cloud.terium.common.services.ICloudService;
import cloud.terium.common.templates.ITemplate;
import cloud.terium.teriumapi.TeriumAPI;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CopyCommand extends Command {

    public CopyCommand() {
        super("copy", "Copy service files to a template.");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 2) {
            TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getServiceByName(args[0]).ifPresentOrElse(cloudService -> TeriumCommon.getTeriumFramework().getProvider().getTemplateProvider().getTemplateByName(args[1]).ifPresentOrElse(template -> {
                try {
                    Logger.log("Trying to copy '§b" + cloudService.getServiceName() + "§f' into template '§b" + template.getName() + "§f'...", LogType.INFO);
                    FileUtils.copyDirectory(new File((cloudService.getServiceGroup().isStatic() ? "static/" : "servers/") + cloudService.getServiceName()), template.getPath().toFile());
                    Logger.log("Successfully copied service '§b" + cloudService.getServiceName() + "§f'.", LogType.INFO);
                } catch (IOException ignored) {}
            }, () -> Logger.log("Specific template not found.", LogType.ERROR)), () -> Logger.log("Specific service not found.", LogType.ERROR));

            return;
        }

        Logger.log("copy [service] [template] | execute command on specific service", LogType.INFO);
    }

    @Override
    public List<String> tabComplete(String[] args) {
        if (args.length == 1)
            return TeriumCommon.getTeriumFramework().getProvider().getServiceProvider().getAllServices().stream().map(ICloudService::getServiceName).toList();

        if (args.length == 2)
            return TeriumCommon.getTeriumFramework().getProvider().getTemplateProvider().getAllTemplates().stream().map(ITemplate::getName).toList();

        return super.tabComplete(args);
    }
}