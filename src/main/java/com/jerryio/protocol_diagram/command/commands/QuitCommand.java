package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class QuitCommand extends Command {

    public QuitCommand() {
        super("quit", "", "Quit the program");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 1)
            return TOO_MANY_ARGUMENTS;

        boolean isForceQuit = params.size() == 1 &&
                params.get(0).isString() &&
                params.get(0).getString().equals("force");

        if (!Main.handler.isModified() || isForceQuit)
            throw new RuntimeException(); // Quit the program
        else
            return fail("Unsaved changes. Please save the diagram first or use \"quit force\" to quit without saving.");
    }

}
