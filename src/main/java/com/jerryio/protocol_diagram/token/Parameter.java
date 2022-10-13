package com.jerryio.protocol_diagram.token;

import java.util.Objects;

public class Parameter implements Token {
    private BooleanT bool = null;
    private NumberT number = null;
    private StringT string = null;

    public static Parameter parse(CodePointBuffer buffer) {
        buffer.savepoint();

        int i = 0;
        while (buffer.peek(i) != null && !Token.isDelimiter(buffer.peek(i)))
            i++;
        int firstStop = buffer.getIndex() + i;

        BooleanT bool = BooleanT.parse(buffer);
        if (bool != null)
            return buffer.commitAndReturn(new Parameter(bool));

        NumberT number = NumberT.parse(buffer);
        if (number != null && firstStop == buffer.getIndex())
            return buffer.commitAndReturn(new Parameter(number));

        StringT string = StringT.parse(buffer);
        if (string != null)
            return buffer.commitAndReturn(new Parameter(string));
        else
            return buffer.rollbackAndReturn(null);
    }

    public Parameter(BooleanT bool) {
        this.bool = bool;
    }

    public Parameter(NumberT number) {
        this.number = number;
    }

    public Parameter(StringT string) {
        this.string = string;
    }

    public boolean getBoolean() {
        return bool.bool();
    }

    public int getInt() {
        return number.toInt();
    }

    public double getDouble() {
        return number.toDouble();
    }

    public String getString() {
        return string.content();
    }

    public boolean isBoolean() {
        return bool != null;
    }

    public boolean isNumber() {
        return number != null;
    }

    public boolean isDouble() {
        return number != null && number.isDouble();
    }

    public boolean isString() {
        return string != null;
    }

    @Override
    public String toString() {
        if (isBoolean())
            return bool.bool() + "";
        else if (isNumber())
            return isDouble() ? number.toDouble() + "" : number.toInt() + "";
        else
            return getString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Parameter)) {
            return false;
        }

        Parameter other = (Parameter) obj;

        return Objects.equals(other.string, string) &&
                Objects.equals(other.number, number) &&
                Objects.equals(other.bool, bool);
    }

}
