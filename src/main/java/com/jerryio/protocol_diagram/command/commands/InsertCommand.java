package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this command responsible in adding new field in to the diagram with specified index
 */
public class InsertCommand extends CancellableCommand {

    /**
     * the index of position that will be injected the new field
     */
    public int paramIndex;
    /**
     * the length of the to-be created field
     */
    public int paramLength;
    /**
     * the name of the to-be created field
     */
    public String paramName;

    public InsertCommand() {
        super("insert", "<index> <length> <name>", "Insert a field at the given index");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 3)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 3)
            return TOO_MANY_ARGUMENTS;

        Parameter paramIndex = params.get(0);
        if (!paramIndex.isNumber() || paramIndex.getInt() < 0)
            return fail("Index start from zero.");

        Parameter paramLength = params.get(1);
        if (!paramLength.isNumber() || paramLength.getInt() <= 0)
            return fail("Length must be a positive integer.");

        Parameter paramName = params.get(2);
        if (!paramName.isString())
            return fail("Name must be a string.");

        this.paramIndex = paramIndex.getInt();
        this.paramLength = paramLength.getInt();
        this.paramName = paramName.getString();

        if (this.paramIndex >= Main.diagram.size())
            return fail("Index out of range.");

        execute();

        String msg;

        if (this.paramIndex == 0)
            msg = "Inserted field \"" + this.paramName + "\" to the beginning.";
        else
            msg = "Inserted field \"" + this.paramName + "\" after \""
                    + Main.diagram.getField(this.paramIndex - 1).getName() + "\".";

        return success(msg + "\n\n" + Main.diagram);
    }

    @Override
    public void execute() {
        Main.diagram.insertField(paramIndex, new Field(paramName, paramLength));
    }

}
