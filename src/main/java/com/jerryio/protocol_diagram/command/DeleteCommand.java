package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class DeleteCommand extends Command implements ICancellable {

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
        if (!paramIndex.isNumber() || paramIndex.getInt() < 0)
            return fail("Index start from zero.");

        this.paramIndex = paramIndex.getInt();

        if (this.paramIndex >= Main.diagram.size())
            return fail("Index out of range.");

        Field f = Main.diagram.getField(this.paramIndex);

        execute();

        return success("Deleted field \"" + f.getName() + "\".\n\n" + Main.diagram);
    }

    @Override
    public void execute() {
        Main.diagram.removeField(paramIndex);
    }

}
