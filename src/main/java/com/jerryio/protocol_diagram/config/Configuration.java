package com.jerryio.protocol_diagram.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class Configuration {

    private final List<Option> options;

    public Configuration(Option... options) {
        this.options = Arrays.asList(options);
    }

    public HandleResult setValue(String key, Parameter value) {
        Option option = getOption(key);
        if (option == null) {
            return fail("Unknown or ambiguous option \"" + key + "\".");
        } else {
            return option.setValue(value);
        }
    }

    public Object getValue(String key) {
        Option option = getOption(key);
        if (option == null) {
            return null;
        } else {
            return option.getValue();
        }
    }

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

    public Collection<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

}
