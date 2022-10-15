package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.FileSystem;
import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class ExportCommand extends Command {
    
    public String paramPath;

    public ExportCommand() {
        super("export", "<path>", "Export the diagram to a file");
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

        this.paramPath = paramPath.getString();

        return FileSystem.export(this.paramPath, Main.diagram);
    }

}
