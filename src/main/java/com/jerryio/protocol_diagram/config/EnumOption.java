package com.jerryio.protocol_diagram.config;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

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

    /**
     * a wrapper method that sets the value of the parameters, the value of the parameter is requires to be string
     * @param Parameter
     * @return HandleResult
     */
    @Override
    public HandleResult setValue(Parameter value) {
        if (value.isString()) {
            return setValue(value.getString());
        } else {
            return fail("The value \"" + value + "\" is not accepted.");
        }
    }

    /**
     * a method that takes the hint from user, auto-complete the hint with the most accurate-accepted value,
     * it will fail when the hint is too ambiguous that shares the prefix more than one accepted values,
     * cannot predict the value, and already set with the same value.
     * @param hint
     * @return HandleResult
     */
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

    /**
     * a getter method that retrieve the value of this enum option
     * @return String
     */
    public String getValue() {
        return value;
    }

    /**
     * a getter method that retrieve the default value of this enum option
     * @return String
     */
    @Override
    public String getDefault() {
        return defaultValue;
    }

    /**
     * a method that retrieve a manual statement for this enum option
     * @return String
     */
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
