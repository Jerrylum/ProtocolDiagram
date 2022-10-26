package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.config.Option;
import com.jerryio.protocol_diagram.diagram.IDiagramModifier;
import com.jerryio.protocol_diagram.token.Parameter;

public class ConfigCommand extends Command implements IDiagramModifier {

    public String paramKey;
    public Parameter paramValue;

    public ConfigCommand() {
        super("config", "<key> <value>", "Change options' value");
    }

    @Override
    public HandleResult handle(List<Parameter> params) {
        String paramKey = params.get(0).getString();
        Option option = Main.diagram.getConfig().getOption(paramKey);

        if (option == null)
            return fail("Unknown or ambiguous option \"" + paramKey + "\".");

        if (params.size() != 2)
            return fail("Usage: config " + option.getKey() + " <" + option.getUsageDescription() + ">");

        this.paramKey = paramKey;
        this.paramValue = params.get(1);

        return option.setValue(this.paramValue);
    }
}
