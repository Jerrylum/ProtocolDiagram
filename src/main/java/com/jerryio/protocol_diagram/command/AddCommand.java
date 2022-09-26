package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class AddCommand extends Command {

    public int paramLength;
    public String paramName;

    public AddCommand() {
        super("add", "<length> <name>", "Add a field to the end of the diagram");
    }

    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 2)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 2)
            return TOO_MANY_ARGUMENTS;

        Parameter paramLength = params.get(0);
        if (!paramLength.isNumber() || paramLength.getInt() <= 0)
            return result("Length must be a positive integer.");

        Parameter paramName = params.get(1);
        if (!paramName.isString())
            return result("Name must be a string.");

        this.paramLength = paramLength.getInt();
        this.paramName = paramName.getString();

        execute();

        return result("Added field \"" + this.paramName + "\".");
    }

    public void execute() {
        // TODO add field
    }

}
