package com.jerryio.protocol_diagram.config;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;
import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class EnumOption extends Option {

    private final String[] acceptedValues;
    private String value;
    private final String defaultValue;

    public EnumOption(String key, String defaultValue, String... acceptedValues) {
        super(key);
        this.acceptedValues = acceptedValues;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
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
            if (acceptedValue.equals(hint.toLowerCase())) {
                selected = acceptedValue;
                break;
            }
            if (acceptedValue.startsWith(hint.toLowerCase())) {
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

    @Override
    public String getDefault() {
        return defaultValue;
    }

    @Override
    public String getUsageDescription() {
        StringBuilder sb = new StringBuilder();
        for (String acceptedValue : acceptedValues) {
            if (acceptedValue.equals(defaultValue)) {
                sb.append(acceptedValue.toUpperCase());
            } else {
                sb.append(acceptedValue);
            }
            sb.append(" | ");
        }

        sb.delete(sb.length() - 3, sb.length());

        return sb.toString();
    }

}
