package com.jerryio.protocol_diagram.config;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public abstract class Option {

    private String key;

    /**
     * option constructor that set the value of the key of this option, once it set,
     * it will be come readonly afterward
     * 
     * @param key
     */
    public Option(String key) {
        this.key = key;
    }

    /**
     * a getter method that retrieve the readonly key
     * 
     * @return
     */
    public String getKey() {
        return key;
    }

    public abstract HandleResult setValue(Parameter value);

    public abstract Object getValue();

    public abstract Object getDefault();

    public abstract String getUsageDescription();

}
