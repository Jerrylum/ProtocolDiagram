package com.jerryio.protocol_diagram.config;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class RangeOption extends Option {

    private int min;
    private int max;
    private int value;

    public RangeOption(String key, int defaultValue, int min, int max) {
        super(key);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
    }

    @Override
    public HandleResult setValue(Parameter value) {
        if (value.isNumber() && !value.isDouble()) {
            return setValue(value.getInt());
        } else {
            return fail("The value must be an integer.");
        }
    }

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

    public Integer getValue() {
        return value;
    }

}
