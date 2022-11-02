package com.jerryio.protocol_diagram.command.commands;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.List;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.command.Command;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.config.Option;
import com.jerryio.protocol_diagram.diagram.IDiagramModifier;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this command is responsible in setting the value of the specified option by its key name
 */
public class ConfigCommand extends Command implements IDiagramModifier {

    /**
     * the key of the specified option
     */
    public String paramKey;

    /**
     * the value will be applied after the execution
     */
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
