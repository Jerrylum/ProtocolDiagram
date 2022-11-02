package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this command responsible in popping the undo stack and pushing the popped history
 * into the redo stack.
 */
public class UndoCommand extends Command {

    public UndoCommand() {
        super("undo", "", "Undo the last action");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        CancellableCommand command = Main.handler.undo();
        if (command == null)
            return fail("Nothing to undo");
        else
            return success("Undo " + command.getName());
    }

}
