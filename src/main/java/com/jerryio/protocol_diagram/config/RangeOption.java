package com.jerryio.protocol_diagram.config;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class RangeOption extends Option {

    private final int min;
    private final int max;
    private int value;
    private final int defaultValue;

    public RangeOption(String key, int defaultValue, int min, int max) {
        super(key);
        this.min = min;
        this.max = max;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    /**
     * a wrapper method that sets the value of this range option, the value of the parameter is required to be an integer
     * @return HandleResult
     */
    @Override
    public HandleResult setValue(Parameter value) {
        if (value.isNumber() && !value.isDouble()) {
            return setValue(value.getInt());
        } else {
            return fail("The value must be an integer.");
        }
    }

    /**
     * a method that sets the value of this range option, note that if the value is out of range
     * such as less than our minimum requirement or greater than our maximum requirement,
     * a handling failure will be returned, and if the value is set with same value of the old value
     * a failure will also be returned
     * @param value
     * @return HandleResult
     */
    public HandleResult setValue(int value) {
        int oldValue = this.value;
        if (oldValue == value) {
            return fail("It is already " + value + ".");
        } else if (value < min || value > max) {
            return fail("The value must be between " + min + " and " + max + ".");
        } else {
            this.value = value;
            return success("Set \"" + getKey() + "\" from " + oldValue + " to " + value + ".");
        }
    }

    /**
     * a getter method that retrieve the value of this range option
     * @return Integer
     */
    public Integer getValue() {
        return value;
    }

    /**
     * a getter method that return the default value of this range option
     * @return Integer
     */
    @Override
    public Integer getDefault() {
        return defaultValue;
    }

    /**
     * a method that return a manual statement.
     * @return String
     */
    @Override
    public String getUsageDescription() {
        return "min:" + min + " max:" + max + " default:" + defaultValue;
    }

}
