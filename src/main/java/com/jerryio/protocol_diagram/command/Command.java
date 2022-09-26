package com.jerryio.protocol_diagram.command;

import java.util.List;

import com.jerryio.protocol_diagram.token.CommandLine;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public abstract class Command {

    private final String name;
    private final String format;
    private final String description;

    public Command(String name, String format, String description) {
        this.name = name;
        this.format = format;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public String getDescription() {
        return description;
    }

    public HandleResult handle(CommandLine line) {
        if (getName().equalsIgnoreCase(line.name()))
            return handle(line.params());
        else
            return NOT_HANDLED;
    }

    public abstract HandleResult handle(List<Parameter> params);

    public abstract void execute();

}
