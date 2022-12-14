package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this command responsible in resizing a specific field with a new size
 */
public class ResizeCommand extends CancellableCommand {

    /**
     * the index of the affecting field
     */
    public int paramIndex;
    /**
     * the new size will be applied after the execution
     */
    public int paramNewSize;

    public ResizeCommand() {
        super("resize", "<index> <new-size>", "Resize the specified field");
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

        Parameter paramNewSize = params.get(1);
        if (!paramNewSize.isNumber() || paramNewSize.getInt() <= 0)
            return fail("New size must be a positive integer.");

        this.paramIndex = paramIndex.getInt();
        this.paramNewSize = paramNewSize.getInt();

        if (this.paramIndex >= Main.diagram.size())
            return fail("Index out of range.");

        Field f = Main.diagram.getField(this.paramIndex);
        int oldLength = f.getLength();

        execute();

        return success("Resized field \"" + f.getName() + "\" from " + oldLength + " to " + f.getLength() + ".\n\n"
                + Main.diagram);
    }

    @Override
    public void execute() {
        Main.diagram.getField(paramIndex).setLength(paramNewSize);
    }

}
