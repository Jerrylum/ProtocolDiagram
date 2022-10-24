package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class ViewCommand extends Command {

    public ViewCommand() {
        super("view", "", "Print out the diagram");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        return success("\n" + Main.diagram);
    }

}
