package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class DeleteCommand extends Command {

    public int paramIndex;
    
    public DeleteCommand() {
        super("delete", "<index>", "Remove the specified field from the diagram");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 1)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 1)
            return TOO_MANY_ARGUMENTS;

        Parameter paramIndex = params.get(0);
        if (!paramIndex.isNumber() || paramIndex.getInt() <= 0)
            return fail("Index must be a positive integer.");

        this.paramIndex = paramIndex.getInt();

        // TODO check if index is valid

        execute();

        // return result("Deleted field \"" + ???? + "\".");
        return success("Deleted field.");
    }

    @Override
    public void execute() {
        // TODO delete field
    }
    
}
