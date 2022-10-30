package com.jerryio.protocol_diagram.command;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.Arrays;
import java.util.List;

import com.jerryio.protocol_diagram.command.commands.*;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.token.Parameter;

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

    public final HandleResult handle(CommandLine line) {
        if (getName().equalsIgnoreCase(line.name()))
            return handle(line.params());
        else
            return NOT_HANDLED;
    }

    public abstract HandleResult handle(List<Parameter> params);

    public static List<Command> getAvailableCommands() {
        return Arrays.asList(
                new AddCommand(),
                new ClearCommand(),
                new ConfigListCommand(),
                new ConfigCommand(),
                new DeleteCommand(),
                new DiscardCommand(),
                new ExportCommand(),
                new HelpCommand(),
                new InsertCommand(),
                new ListCommand(),
                new LoadCommand(),
                new MoveCommand(),
                new QuitCommand(),
                new RedoCommand(),
                new RenameCommand(),
                new ResizeCommand(),
                new SaveCommand(),
                new UndoCommand(),
                new ViewCommand());
    }

}
