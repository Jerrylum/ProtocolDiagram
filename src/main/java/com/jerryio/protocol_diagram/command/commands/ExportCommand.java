package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.util.FileUtils;

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

        return FileUtils.export(this.paramPath, Main.diagram);
    }

}
