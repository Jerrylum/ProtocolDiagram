package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class MoveCommand extends Command {
    
    public int paramIndex;
    public int paramTargetIndex;

    public MoveCommand() {
        super("move", "<index> <target>", "Move the specified field to the position before the target index");
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

        Parameter paramTargetIndex = params.get(1);
        if (!paramTargetIndex.isNumber() || paramTargetIndex.getInt() <= 0)
            return fail("Target index must be a positive integer.");

        this.paramIndex = paramIndex.getInt();
        this.paramTargetIndex = paramTargetIndex.getInt();

        // TODO check if index and target index is valid

        execute();

        // return result("Moved field \"" + ???? + "\" to the end.");
        // return result("Moved field \"" + ???? + "\" before \"" + ??? + "\".");
        return success("Moved field.");
    }

    @Override
    public void execute() {
        // TODO move field
    }

}
