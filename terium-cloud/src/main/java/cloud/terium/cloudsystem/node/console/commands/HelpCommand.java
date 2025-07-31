package cloud.terium.cloudsystem.node.console.commands;

import cloud.terium.cloudsystem.node.utils.Logger;
import cloud.terium.cloudsystem.node.NodeStartup;
import cloud.terium.common.command.Command;
import cloud.terium.common.command.LogType;

import java.util.Arrays;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "See all commands with description and aliases.", "?");
    }

    @Override
    public void execute(String[] args) {
        NodeStartup.getNode().getCommandManager().getCommandList().forEach(command -> Logger.log(command.getCommand() + (command.getAliases().length >= 1 ? Arrays.asList(command.getAliases()) : "") + " - " + command.getDescription(), LogType.INFO));
    }
}