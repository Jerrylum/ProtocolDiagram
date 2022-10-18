package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.util.FileUtils;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

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

        if (FileSystem.isModified) {
            return fail("Unsaved changes. Please save the diagram first or use \"discard\" before the operation.");
        }

        String path = paramPath.getString();
        this.paramPath = path;

        Diagram diagram = FileUtils.load(path);
        if (diagram == null) {
            return fail("Failed to load diagram from " + path);
        } else {
            Main.diagram = diagram;
            Main.timeline.reset();
            FileSystem.mountedFile = path;
            FileSystem.fileMemento = Main.timeline.getLatestMemento();
            FileSystem.isModified = false;
            return success("Now editing " + path + "\n\n" + diagram.toString());
        }
    }
}
