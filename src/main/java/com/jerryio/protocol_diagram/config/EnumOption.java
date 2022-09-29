package com.jerryio.protocol_diagram.config;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class EnumOption extends Option {

    private String[] acceptedValues;
    private String value;

    public EnumOption(String key, String defaultValue, String... acceptedValues) {
        super(key);
        this.value = defaultValue;
        this.acceptedValues = acceptedValues;
    }

    @Override
    public HandleResult setValue(Parameter value) {
        if (value.isString()) {
            return setValue(value.getString());
        } else {
            return fail("The value \"" + value + "\" is not accepted.");
        }
    }

    public HandleResult setValue(String hint) {
        String selected = null;
        for (String acceptedValue : acceptedValues) {
            if (acceptedValue.startsWith(hint)) {
                if (selected != null) {
                    return fail("Ambiguous value \"" + hint + "\".");
                }
                selected = acceptedValue;
            }
        }

        if (selected == null) {
            return fail("Unknown value \"" + hint + "\".");
        } else {
            String oldValue = this.value;
            if (oldValue.equals(selected)) {
                return fail("It is already \"" + selected + "\".");
            } else {
                this.value = selected;
                return success("Set \"" + getKey() + "\" from \"" + oldValue + "\" to \"" + selected + "\".");
            }
        }
    }

    public String getValue() {
        return value;
    }

}
