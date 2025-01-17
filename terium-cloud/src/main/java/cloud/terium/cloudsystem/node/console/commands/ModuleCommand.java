package cloud.terium.cloudsystem.node.console.commands;

import cloud.terium.cloudsystem.node.utils.Logger;
import cloud.terium.cloudsystem.node.NodeStartup;
import cloud.terium.teriumapi.console.LogType;
import cloud.terium.teriumapi.console.command.Command;
import cloud.terium.teriumapi.module.ILoadedModule;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ModuleCommand extends Command {

    public ModuleCommand() {
        super("module", "Manage modules", "modules");
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                NodeStartup.getNode().getModuleProvider().getAllModules().forEach(module -> {
                    Logger.log(module.getName() + "(" + module.getFileName() + ") by " + module.getAuthor() + " version " + module.getVersion() + ".", LogType.INFO);
                });
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("enable")) {
                try {
                    NodeStartup.getNode().getModuleProvider().loadModule("modules//" + args[1]);
                } catch (Exception exception) {
                    Logger.log("A module with this name isn't in the modules folder.");
                }
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("disable")) {
                Optional<ILoadedModule> cloudModule = NodeStartup.getNode().getModuleProvider().getModuleByName(args[1]);
                cloudModule.ifPresentOrElse(module -> {
                    NodeStartup.getNode().getModuleProvider().unloadModule(module);
                    Logger.log("Unloaded '§b" + module.getName() + "§f' by '§b" + module.getAuthor() + "§f' successfully!");
                }, () -> Logger.log("A module with this name isn't loaded"));
            }
            return;
        }

        Logger.log("module list | list of all loaded modules", LogType.INFO);
        Logger.log("module enable [module] | list of all loaded modules", LogType.INFO);
        Logger.log("module disable [module] | list of all loaded modules", LogType.INFO);
    }

    @Override
    public List<String> tabComplete(String[] args) {
        switch (args.length) {
            case 1 -> {
                return Arrays.asList("list", "enable", "disable");
            }

            case 2 -> {
                if (args[0].equalsIgnoreCase("enable")) {
                    return Arrays.stream(new File("modules//").listFiles()).map(File::getName).filter(name -> name.endsWith(".jar")).filter(s -> !NodeStartup.getNode().getModuleProvider().getAllModules().stream().map(ILoadedModule::getFileName).toList().contains(s)).toList();
                }

                if (args[0].equalsIgnoreCase("disable")) {
                    return NodeStartup.getNode().getModuleProvider().getAllModules().stream().map(ILoadedModule::getName).toList();
                }
            }
        }

        return super.tabComplete(args);
    }
}