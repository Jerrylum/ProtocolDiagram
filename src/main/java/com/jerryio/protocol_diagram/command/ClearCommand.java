package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class ClearCommand extends Command {

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
