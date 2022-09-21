package com.jerryio.protocol_diagram.token;

import java.util.ArrayList;
import java.util.List;

public record CommandLine(String name, List<Parameter> params) implements Token {
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
