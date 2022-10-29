package com.jerryio.protocol_diagram.config;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class BooleanOption extends Option {

    private boolean value;
    private final boolean defaultValue;

    public BooleanOption(String key, boolean defaultValue) {
        super(key);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    /**
     * a wrapper method that sets the value of this boolean option, the value of the parameter is required to be an boolean
     * @return HandleResult
     */
    @Override
    public HandleResult setValue(Parameter value) {
        if (value.isBoolean()) {
            return setValue(value.getBoolean());
        } else {
            return fail("The value must be a boolean.");
        }
    }

    /**
     * a method that sets the value of this boolean option, note that if the value
     * is repetitively set with the same value, a failure will be returned
     * @param value
     * @return HandleResult
     */
    public HandleResult setValue(boolean value) {
        boolean oldValue = this.value;
        if (oldValue == value) {
            return fail("It is already " + value + ".");
        } else {
            this.value = value;
            return success("Set \"" + getKey() + "\" from " + oldValue + " to " + value + ".");
        }
    }

    /**
     * a getter method that read the value of this boolean option and return.
     * @return Boolean
     */
    public Boolean getValue() {
        return value;
    }

    /**
     * a getter method that returns the default value of this boolean option
     * @return String
     */
    @Override
    public Boolean getDefault() {
        return defaultValue;
    }

    /**
     * a method that returns a manual statement
     * @return String
     */
    @Override
    public String getUsageDescription() {
        return defaultValue ? "TRUE | false" : "true | FALSE";
    }
}
