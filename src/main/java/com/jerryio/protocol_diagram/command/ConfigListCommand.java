package com.jerryio.protocol_diagram.command;

import java.util.Collection;
import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.config.Option;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class ConfigListCommand extends Command {

    public ConfigListCommand() {
        super("config", "", "List all settings");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        if (params.size() > 0)
            return NOT_HANDLED; // pass it to ConfigCommand

        Collection<Option> options = Main.diagram.getConfig().getOptions();

        int maxLength = 0;
        for (Option option : options) {
            int length = option.getKey().length();
            if (length > maxLength)
                maxLength = length;
        }

        StringBuilder sb = new StringBuilder();
        for (Option option : options) {
            sb.append("\n");
            sb.append(option.getKey());
            for (int i = 0; i < maxLength - option.getKey().length(); i++)
                sb.append(" ");
            sb.append(" = ");
            sb.append(option.getValue().toString());
        }

        return success(sb.toString());
    }

    @Override
    public void execute() {
        // No need to do anything
    }

}
