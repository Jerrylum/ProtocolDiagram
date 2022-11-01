package com.jerryio.protocol_diagram.command;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.Arrays;
import java.util.List;

import com.jerryio.protocol_diagram.command.commands.*;
import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this abstract class consists the information of a basic command, such as the
 * prefix, description, and the usage of the command.
 */
public abstract class Command {

    /**
     * the name of the command
     */
    private final String name;
    /**
     * the basic usage of the command
     */
    private final String usage;
    /**
     * the description of the command
     */
    private final String description;

    /**
     * a constructor that takes three values, name, usage, and description and
     * assign them into the instance variables
     * 
     * @param name        the name of the command
     * @param usage       the usage of the command
     * @param description the description of the command
     */
    public Command(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    /**
     * a getter method that returns the name of this command
     * 
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * a getter method that returns the usage of this command
     * 
     * @return String
     */
    public String getUsage() {
        return usage;
    }

    /**
     * a getter method that returns the description of this command
     * 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * a method that determines whether the current command instance matches the
     * prefix of the line,
     * if yes, then a list of parameters will be created and will be passed to the
     * abstract function `handle` that will be implemented by the descendent
     * classes,
     * if no, then return a `HandleResult` not_handled
     * 
     * @param line the command line that holds the prefix and the parameters
     * @return HandleResult
     */
    public final HandleResult handle(CommandLine line) {
        if (getName().equalsIgnoreCase(line.name()))
            return handle(line.params());
        else
            return NOT_HANDLED;
    }

    /**
     * a abstract method that will be implemented later on by the descendent
     * classes, by the logic of the command handling,
     * this method would only be invoked when the command instance matches the
     * prefix of the input command, it will be passed
     * a list of parameters for providing the context for the command handling, once
     * it is finished, it is required to return
     * a `HandleResult` instance for indicating the state of the command outcome
     * 
     * @param params a list of parameters
     * @return HandleResult
     */
    public abstract HandleResult handle(List<Parameter> params);

    /**
     * a utility method that returns a list of all available commands in the program
     * 
     * @return List
     */
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
