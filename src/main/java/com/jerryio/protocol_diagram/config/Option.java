package com.jerryio.protocol_diagram.config;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public abstract class Option {

    private String key;

    public Option(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public abstract HandleResult setValue(Parameter value);

    public abstract Object getValue();

    public abstract Object getDefault();

    public abstract String getUsageDescription();

}
