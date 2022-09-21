package com.jerryio.protocol_diagram.token;

public record PositiveInt(String value) {
    public static PositiveInt parse(CodePointBuffer buffer) {
        buffer.savepoint();

        StringBuilder sb = new StringBuilder();

        Digit1To9 d1t9 = Digit1To9.parse(buffer);
        if (d1t9 == null) {
            return buffer.rollbackAndReturn(null);
        }
        sb.append(d1t9.value());

        Digit d0t9;
        while ((d0t9 = Digit.parse(buffer)) != null) {
            sb.append(d0t9.value());
        }

        return buffer.commitAndReturn(new PositiveInt(sb.toString()));
    }
}
