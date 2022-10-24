package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class SaveCommand extends Command {

    public String paramPath;

    public SaveCommand() {
        super("save", "[path]", "Save the diagram to the specified path");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 1)
            return TOO_MANY_ARGUMENTS;

        String path = null;
        if (params.size() == 1) {
            Parameter paramPath = params.get(0);
            if (!paramPath.isString())
                return fail("Path must be a string.");

            path = paramPath.getString();
        }

        if (path == null) {
            if (Main.handler.getSourceFilePath() == null)
                return fail("You are not editing a file. Please specify a path to save the file.");

            path = Main.handler.getSourceFilePath();
        }

        this.paramPath = path;

        return Main.handler.save(path);
    }

}
