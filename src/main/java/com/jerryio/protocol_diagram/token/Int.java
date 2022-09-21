package com.jerryio.protocol_diagram.token;

public record Int(String value) implements Token {
    public static Int parse(CodePointBuffer buffer) {
        buffer.savepoint();

        StringBuilder sb = new StringBuilder();

        Zero z = Zero.parse(buffer);
        if (z != null) {
            sb.append(z.value());
            return buffer.commitAndReturn(new Int(sb.toString()));
        } else {
            PositiveInt p = PositiveInt.parse(buffer);

            if (p == null) {
                return buffer.rollbackAndReturn(null);
            }

            sb.append(p.value());

            return buffer.commitAndReturn(new Int(sb.toString()));
        }
    }
}
