package com.jerryio.protocol_diagram.token;

public record NumberT(String value, boolean isPositive, boolean isDouble) implements Token {
    public static NumberT parse(CodePointBuffer buffer) {
        buffer.savepoint();

        StringBuilder sb = new StringBuilder();
        boolean isPositive, isDouble;

        NegativeInt n = NegativeInt.parse(buffer);
        if (n != null) {
            sb.append(n.value());
            isPositive = false;
        } else {
            PositiveInt p = PositiveInt.parse(buffer);
            if (p == null) {
                return buffer.rollbackAndReturn(null);
            }
            sb.append(p.value());
            isPositive = true;
        }

        Frac f = Frac.parse(buffer);
        if (isDouble = (f != null)) {
            sb.append(f.value());
        }

        return buffer.commitAndReturn(new NumberT(sb.toString(), isPositive, isDouble));
    }

    public int toInt() {
        return Integer.parseInt(value);
    }

    public double toDouble() {
        return Double.parseDouble(value);
    }
}
