package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class InsertCommand extends Command {
    
    public int paramIndex;
    public int paramLength;
    public String paramName;

    public InsertCommand() {
        super("insert", "<index> <length> <name>", "Insert a field before the given index");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 3)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 3)
            return TOO_MANY_ARGUMENTS;

        Parameter paramIndex = params.get(0);
        if (!paramIndex.isNumber() || paramIndex.getInt() <= 0)
            return fail("Index must be a positive integer.");

        Parameter paramLength = params.get(1);
        if (!paramLength.isNumber() || paramLength.getInt() <= 0)
            return fail("Length must be a positive integer.");

        Parameter paramName = params.get(2);
        if (!paramName.isString())
            return fail("Name must be a string.");

        this.paramIndex = paramIndex.getInt();
        this.paramLength = paramLength.getInt();
        this.paramName = paramName.getString();

        // TODO check if index is valid

        execute();

        return success("Inserted field \"" + this.paramName + "\".");
    }

    @Override
    public void execute() {
        // TODO insert field
    }

}
