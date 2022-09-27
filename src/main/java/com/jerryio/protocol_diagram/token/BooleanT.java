package com.jerryio.protocol_diagram.token;

public record BooleanT(String value, boolean bool) implements Token {
    public static BooleanT parse(CodePointBuffer buffer) {
        buffer.savepoint();

        String s = buffer.readChunk();
        
        if ("true".equalsIgnoreCase(s))
            return buffer.commitAndReturn(new BooleanT(s, true));
        else if ("false".equalsIgnoreCase(s))
            return buffer.commitAndReturn(new BooleanT(s, false));
        else
            return buffer.rollbackAndReturn(null);
    }
}
