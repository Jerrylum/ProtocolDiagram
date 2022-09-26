package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class HelpCommand extends Command {
    
    public HelpCommand() {
        super("help", "", "Show help message");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        StringBuilder sb = new StringBuilder();

        sb.append("Usage:");

        int maxLength = 0;
        for (Command command : Command.getAvailableCommands()) {
            int length = command.getName().length() + command.getUsage().length();
            if (length > maxLength)
                maxLength = length;
        }

        for (Command command : Command.getAvailableCommands()) {
            sb.append("\n");
            sb.append(command.getName());
            sb.append(" ");
            sb.append(command.getUsage());
            for (int i = 0; i < maxLength - command.getName().length() - command.getUsage().length(); i++)
                sb.append(" ");
            sb.append("  - ");
            sb.append(command.getDescription());
        }

        return success(sb.toString());
    }

    @Override
    public void execute() {
        // No need to do anything
    }

}
