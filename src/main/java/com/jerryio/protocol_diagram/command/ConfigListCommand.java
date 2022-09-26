package com.jerryio.protocol_diagram.command;

import java.util.List;

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

        return HANDLED; // TODO list all settings
    }

    @Override
    public void execute() {
        // No need to do anything
    }

}
