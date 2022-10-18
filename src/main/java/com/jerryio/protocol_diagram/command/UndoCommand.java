package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class UndoCommand extends Command {

    public UndoCommand() {
        super("undo", "", "Undo the last action");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        ICancellable command = Main.handler.undo();
        if (command == null)
            return fail("Nothing to undo");
        else
            return success("Undo " + ((Command)command).getName());
    }

}
