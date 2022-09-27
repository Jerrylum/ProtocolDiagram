package com.jerryio.protocol_diagram.token;

public class Parameter implements Token {
    private BooleanT bool = null;
    private NumberT number = null;
    private StringT string = null;

    public static Parameter parse(CodePointBuffer buffer) {
        buffer.savepoint();

        int i = 0;
        while (buffer.peek(i) != null && buffer.peek(i) != ' ')
            i++;
        int firstStop = buffer.getIndex() + i;

        BooleanT bool = BooleanT.parse(buffer);
        if (bool != null && firstStop == buffer.getIndex())
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
    public boolean equals(Object p) {
        if (p == null) {
            System.out.println("false1");
            return false;
        }

        if (p.getClass() != Parameter.class) {
            System.out.println("false2");
            return false;
        }

        Parameter temp = (Parameter) p;
        if(temp.bool == null && bool != null || temp.bool != null && bool == null) {
            return false;
        }
        if(temp.number == null && number != null || temp.number != null && number == null) {
            return false;
        }
        if(temp.string == null && string != null || temp.string != null && string == null) {
            return false;
        }
        // System.out.println(temp.bool==bool);
        // System.out.println(temp.number==number);
        // System.out.println(temp.string.equals(string));
        //return Object.equals(temp.bool, bool) && Object.equals(temp.number, number) && Object.equals(temp.string, string);

        return  ((temp.bool == null && bool == null)||temp.bool.equals(bool)) 
        && ((temp.number == null && number == null)||temp.number.equals(number)) 
        && ((temp.string == null && string == null)||temp.string.equals(string));
    }

}
