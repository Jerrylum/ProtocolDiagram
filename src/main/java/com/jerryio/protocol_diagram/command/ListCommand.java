package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class ListCommand extends Command {

    public ListCommand() {
        super("list", "", "List all fields in the diagram");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return TOO_MANY_ARGUMENTS;

        if (Main.diagram.size() == 0) {
            return success("There is no field in the diagram.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("There are ");
            sb.append(Main.diagram.size());
            sb.append(" fields in the diagram:");

            for (int i = 0; i < Main.diagram.size(); i++) {
                Field f = Main.diagram.getField(i);
                sb.append("\n");
                sb.append(i);
                sb.append(" - ");
                sb.append(f.getName());
                sb.append(": ");
                sb.append(f.getLength());
            }
            return success(sb.toString());
        }

    }

}
