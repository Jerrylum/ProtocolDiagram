package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class MoveCommand extends Command {

    public int paramIndex;
    public int paramTargetIndex;

    public MoveCommand() {
        super("move", "<index> <target>", "Move the specified field from one position to another");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 2)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 2)
            return TOO_MANY_ARGUMENTS;

        Parameter paramIndex = params.get(0);
        if (!paramIndex.isNumber() || paramIndex.getInt() < 0)
            return fail("Index start from zero.");

        Parameter paramTargetIndex = params.get(1);
        if (!paramTargetIndex.isNumber() || paramTargetIndex.getInt() < 0)
            return fail("Target index start from zero.");

        this.paramIndex = paramIndex.getInt();
        this.paramTargetIndex = paramTargetIndex.getInt();

        if (this.paramIndex >= Main.diagram.size())
            return fail("Index out of range.");

        if (this.paramTargetIndex >= Main.diagram.size())
            return fail("Target index out of range.");

        if (this.paramIndex == this.paramTargetIndex)
            return fail("Index and target index cannot be the same.");

        Field f = Main.diagram.getField(this.paramIndex);
        String msg;

        if (this.paramTargetIndex == 0)
            msg = "Moved field \"" + f.getName() + "\" to the beginning.";
        else if (this.paramTargetIndex == Main.diagram.size() - 1)
            msg = "Moved field \"" + f.getName() + "\" to the end.";
        else
            msg = "Moved field \"" + f.getName() + "\" after \""
                    + Main.diagram.getField(this.paramTargetIndex - 1).getName() + "\".";

        execute();

        return success(msg + "\n\n" + Main.diagram);
    }

    @Override
    public void execute() {
        Main.diagram.moveField(paramIndex, paramTargetIndex);
    }

}
