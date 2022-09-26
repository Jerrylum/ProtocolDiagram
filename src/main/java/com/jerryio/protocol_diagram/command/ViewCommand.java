package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class ViewCommand extends Command {
    
    public ViewCommand() {
        super("view", "", "Print out the diagram");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        return HANDLED; // TODO print out the diagram
    }

    @Override
    public void execute() {
        // No need to do anything
    }

}
