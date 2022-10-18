package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class ClearCommand extends CancellableCommand {

    public ClearCommand() {
        super("clear", "", "Remove all fields and start again");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        execute();

        return success("Removed all fields.");
    }

    @Override
    public void execute() {
        Main.diagram.clear();
    }

}
