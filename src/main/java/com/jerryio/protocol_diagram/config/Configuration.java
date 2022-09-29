package com.jerryio.protocol_diagram.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

public class Configuration {

    private final List<Option> options = Arrays.asList(
            new RangeOption("bit", 32, 1, 128),
            new EnumOption(
                    "diagram-style", "utf8",
                    "utf8", "utf8-header", "utf8-corner", "ascii", "ascii-verbose"),
            new EnumOption(
                    "header-style", "trim",
                    "none", "trim", "full"),
            new BooleanOption("left-space-placeholder", false));

    public HandleResult setValue(String key, Parameter value) {
        Option selected = null;
        for (Option option : options) {
            if (option.getKey().startsWith(key)) {
                if (selected != null) {
                    return fail("Ambiguous option \"" + key + "\".");
                }
                selected = option;
            }
        }

        if (selected == null) {
            return fail("Unknown option \"" + key + "\".");
        } else {
            return selected.setValue(value);
        }
    }

    public Object getValue(String key) {
        Option selected = null;
        for (Option option : options) {
            if (option.getKey().startsWith(key)) {
                if (selected != null) {
                    return null;
                }
                selected = option;
            }
        }

        if (selected == null) {
            return null;
        } else {
            return selected.getValue();
        }
    }

    public Collection<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

}
