package com.jerryio.protocol_diagram.token;

public record BooleanT(String value, boolean bool) implements Token {
    public static BooleanT parse(CodePointBuffer buffer) {
        buffer.savepoint();

        String s = buffer.readChunk();
        
        if (s.equals("True") || s.equals("true") || s.equals("t"))
            return buffer.commitAndReturn(new BooleanT(s, true));
        else if (s.equals("False") || s.equals("false") || s.equals("f"))
            return buffer.commitAndReturn(new BooleanT(s, false));
        else
            return buffer.rollbackAndReturn(null);
    }
}
