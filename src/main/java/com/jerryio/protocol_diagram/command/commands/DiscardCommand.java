package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this command is responsible in clearing all fields and eliminating all history
 */
public class DiscardCommand extends Command {

    public DiscardCommand() {
        super("discard", "", "Discard all changes and start a new diagram");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        Main.handler.newDiagram();

        return success("Now editing a new diagram.");
    }

}
