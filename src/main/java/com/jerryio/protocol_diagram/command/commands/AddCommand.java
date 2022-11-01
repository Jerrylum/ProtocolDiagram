package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this command is responsible for adding fields into the diagram instance
 */
public class AddCommand extends CancellableCommand {

    /**
     * the length of the to-be added field
     */
    public int paramLength;
    
    /**
     * the name of the to-be added field
     */
    public String paramName;

    /**
     * this command is responsible for adding fields into the diagram instance
     */
    public AddCommand() {
        super("add", "<length> <name>", "Add a field to the end of the diagram");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() < 2)
            return TOO_FEW_ARGUMENTS;
        if (params.size() > 2)
            return TOO_MANY_ARGUMENTS;

        Parameter paramLength = params.get(0);
        if (!paramLength.isNumber() || paramLength.getInt() <= 0)
            return fail("Length must be a positive integer.");

        Parameter paramName = params.get(1);
        if (!paramName.isString())
            return fail("Name must be a string.");

        this.paramLength = paramLength.getInt();
        this.paramName = paramName.getString();

        execute();

        return success("Added field \"" + this.paramName + "\".\n\n" + Main.diagram);
    }

    @Override
    public void execute() {
        Main.diagram.addField(new Field(paramName, paramLength));
    }

}
