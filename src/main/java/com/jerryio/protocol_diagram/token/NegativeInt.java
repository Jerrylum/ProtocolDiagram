package com.jerryio.protocol_diagram.token;

public record NegativeInt(String value) implements Token {
    public static NegativeInt parse(CodePointBuffer buffer) {
        buffer.savepoint();

        StringBuilder sb = new StringBuilder();

        Minus m = Minus.parse(buffer);
        if (m == null) {
            return buffer.rollbackAndReturn(null);
        }
        sb.append(m.value());

        PositiveInt p = PositiveInt.parse(buffer);
        if (p == null) {
            return buffer.rollbackAndReturn(null);
        }
        sb.append(p.value());

        return buffer.commitAndReturn(new NegativeInt(sb.toString()));
    }
}
