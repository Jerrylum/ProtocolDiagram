package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class SaveCommand extends Command {

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
            if (FileSystem.mountedFile == null)
                return fail("No file mounted.");

            path = FileSystem.mountedFile;
        } else {
            if (!path.endsWith(".json"))
                path += ".json";
        }

        HandleResult result = FileSystem.save(path, Main.diagram);
        if (result.success())
            FileSystem.mountedFile = path;

        return result;
    }

}
