package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class RedoCommand extends Command {
    
    public RedoCommand() {
        super("redo", "", "Redo the last action");
    }
    
    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;
        
        CancellableCommand command = Main.handler.redo();
        if (command == null)
            return fail("Nothing to redo");
        else
            return success("Redo " + command.getName());
    }
    
}
