package cloud.terium.common.command;

public interface ICommandFactory {

    /**
     * Register a command.
     *
     * @param command
     */
    void registerCommand(Command command);
}