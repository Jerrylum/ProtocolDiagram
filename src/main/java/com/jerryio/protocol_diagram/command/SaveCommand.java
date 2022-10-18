package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.util.FileUtils;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

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
            if (FileSystem.mountedFile == null)
                return fail("You are not editing a file. Please specify a path to save the file.");

            path = FileSystem.mountedFile;
        }

        this.paramPath = path;

        HandleResult result = FileUtils.save(path, Main.diagram);
        if (result.success()) {
            FileSystem.mountedFile = path;
            FileSystem.fileMemento = Main.timeline.getLatestMemento();
            FileSystem.isModified = false;
        }

        return result;
    }

}
