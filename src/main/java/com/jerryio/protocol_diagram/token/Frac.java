package com.jerryio.protocol_diagram.token;

public record Frac(String value) implements Token {
    public static Frac parse(CodePointBuffer buffer) {
        buffer.savepoint();

        StringBuilder sb = new StringBuilder();

        DecimalPoint d = DecimalPoint.parse(buffer);
        if (d == null) {
            return buffer.rollbackAndReturn(null);
        }
        sb.append(d.value());

        boolean hasDigit = false;
        Digit d0t9;
        while ((d0t9 = Digit.parse(buffer)) != null) {
            sb.append(d0t9.value());
            hasDigit = true;
        }

        if (!hasDigit) {
            return buffer.rollbackAndReturn(null);
        }

        return buffer.commitAndReturn(new Frac(sb.toString()));
    }
}
