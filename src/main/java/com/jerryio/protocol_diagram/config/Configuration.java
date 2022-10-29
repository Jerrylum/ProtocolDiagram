package com.jerryio.protocol_diagram.config;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class Configuration {

    private final List<Option> options;

    public Configuration(Option... options) {
        this.options = Arrays.asList(options);
    }

    /**
     * a method that sets the value of specified option from this configuration by the given key and value
     * @param key
     * @param value
     * @return HandleResult
     */
    public HandleResult setValue(String key, Parameter value) {
        Option option = getOption(key);
        if (option == null) {
            return fail("Unknown or ambiguous option \"" + key + "\".");
        } else {
            return option.setValue(value);
        }
    }

    /**
     * a getter method that retrieve the value of option by the specified key
     * @param key
     * @return Object
     */
    public Object getValue(String key) {
        Option option = getOption(key);
        if (option == null) {
            return null;
        } else {
            return option.getValue();
        }
    }

    /**
     * a find method that lookup the matching option based on the given key
     * @param key
     * @return Option
     */
    public Option getOption(String key) {
        Option selected = null;
        for (Option option : options) {
            if (option.getKey().equals(key.toLowerCase())) {
                selected = option;
                break;
            }
            if (option.getKey().startsWith(key.toLowerCase())) {
                if (selected != null) {
                    return null;
                }
                selected = option;
            }
        }

        return selected;
    }

    /**
     * a pure function that return a readonly clone of the options list
     * @return Collection<Option>
     */
    public Collection<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

}
