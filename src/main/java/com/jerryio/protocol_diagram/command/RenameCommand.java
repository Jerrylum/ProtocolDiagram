package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class RenameCommand extends Command {

    public int paramIndex;
    public String paramNewName;

    public RenameCommand() {
        super("rename", "<index> <new-name>", "Rename the specified field");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 2)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 2)
            return TOO_MANY_ARGUMENTS;

        Parameter paramIndex = params.get(0);
        if (!paramIndex.isNumber() || paramIndex.getInt() <= 0)
            return fail("Index must be a positive integer.");

        Parameter paramNewName = params.get(1);
        if (!paramNewName.isString())
            return fail("New name must be a string.");

        this.paramIndex = paramIndex.getInt();
        this.paramNewName = paramNewName.getString();

        // TODO check if index is valid

        execute();

        // return success("Renamed field from \"" + ???? + "\" to \"" + ???? + "\".");
        return success("Renamed field.");
    }

    @Override
    public void execute() {
        // TODO rename field
    }
    
}
