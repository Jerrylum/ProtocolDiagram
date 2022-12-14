package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class LoadCommand extends Command {

    public String paramPath;

    public LoadCommand() {
        super("load", "<path>", "Load the diagram from the specified path");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 1)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 1)
            return TOO_MANY_ARGUMENTS;

        Parameter paramPath = params.get(0);
        if (!paramPath.isString())
            return fail("Path must be a string.");

        if (Main.handler.isModified()) {
            return fail("Unsaved changes. Please save the diagram first or use \"discard\" before the operation.");
        }

        String path = paramPath.getString();
        this.paramPath = path;

        HandleResult result = Main.handler.load(path);
        if (!result.success()) {
            return result;
        } else {
            return success("Now editing " + path + "\n\n" + Main.diagram.toString());
        }
    }
}
