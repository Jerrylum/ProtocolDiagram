package com.jerryio.protocol_diagram.command;

import java.util.Arrays;
import java.util.List;

import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public abstract class Command {

    private final String name;
    private final String usage;
    private final String description;

    public Command(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public HandleResult handle(CommandLine line) {
        if (getName().equalsIgnoreCase(line.name()))
            return handle(line.params());
        else
            return NOT_HANDLED;
    }

    public abstract HandleResult handle(List<Parameter> params);

    public abstract void execute();

    public static List<Command> getAvailableCommands() {
        return Arrays.asList(
                new AddCommand(),
                new ClearCommand(),
                new ConfigListCommand(),
                new ConfigCommand(),
                new DeleteCommand(),
                new HelpCommand(),
                new InsertCommand(),
                new ListCommand(),
                new MoveCommand(),
                new QuitCommand(),
                new RenameCommand(),
                new ResizeCommand(),
                new ViewCommand());
    }

}
