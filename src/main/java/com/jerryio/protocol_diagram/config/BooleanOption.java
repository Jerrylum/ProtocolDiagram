package com.jerryio.protocol_diagram.config;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class BooleanOption extends Option {

    private boolean value;
    private final boolean defaultValue;

    public BooleanOption(String key, boolean defaultValue) {
        super(key);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    @Override
    public HandleResult setValue(Parameter value) {
        if (value.isBoolean()) {
            return setValue(value.getBoolean());
        } else {
            return fail("The value must be a boolean.");
        }
    }

    public HandleResult setValue(boolean value) {
        boolean oldValue = this.value;
        if (oldValue == value) {
            return fail("It is already " + value + ".");
        } else {
            this.value = value;
            return success("Set \"" + getKey() + "\" from " + oldValue + " to " + value + ".");
        }
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public Boolean getDefault() {
        return defaultValue;
    }

    @Override
    public String getUsageDescription() {
        return defaultValue ? "TRUE | false" : "true | FALSE";
    }
}
