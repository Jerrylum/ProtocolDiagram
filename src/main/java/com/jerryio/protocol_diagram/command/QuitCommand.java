package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class QuitCommand extends Command {
    
    public QuitCommand() {
        super("quit", "", "Quit the program");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        execute();

        return HANDLED;
    }

    @Override
    public void execute() {
        // TODO quit program
    }

}
