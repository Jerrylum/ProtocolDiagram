package com.jerryio.protocol_diagram.token;

import java.util.ArrayList;
import java.util.List;

public record CommandLine(String name, List<Parameter> params) implements Token {
    /**
     * a static utility function that parses the CodePointBuffer, which is a wrapper
     * of a string, and returns a CommandLine object, which is a wrapper that
     * separated from the raw CodePointBuffer to two stuff, a command prefix and the
     * params right after the command prefix.
     * 
     * via this process, it could assist the program to distinguish each commands
     * and tell the differences.
     * 
     * @param buffer
     * @return
     */
    public static CommandLine parse(CodePointBuffer buffer) {
        buffer.readDelimiter();

        String cname = buffer.readChunk();
        if (cname.isEmpty())
            return null;

        List<Parameter> params = new ArrayList<>();
        while (true) {
            buffer.readDelimiter();
            Parameter p = Parameter.parse(buffer);
            if (p == null)
                break;
            params.add(p);
        }

        if (buffer.hasNext())
            return null;
        else
            return new CommandLine(cname, params);
    }
}
