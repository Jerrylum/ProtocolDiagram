package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Field;
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
        if (!paramIndex.isNumber() || paramIndex.getInt() < 0)
            return fail("Index start from zero.");

        Parameter paramNewName = params.get(1);
        if (!paramNewName.isString())
            return fail("New name must be a string.");

        this.paramIndex = paramIndex.getInt();
        this.paramNewName = paramNewName.getString();

        if (this.paramIndex >= Main.diagram.size())
            return fail("Index out of range.");

        Field f = Main.diagram.getField(this.paramIndex);
        String oldName = f.getName();

        execute();

        return success("Renamed field from \"" + oldName + "\" to \"" + f.getName() + "\".\n\n" + Main.diagram);
    }

    @Override
    public void execute() {
        Main.diagram.getField(paramIndex).setName(paramNewName);
    }

}
