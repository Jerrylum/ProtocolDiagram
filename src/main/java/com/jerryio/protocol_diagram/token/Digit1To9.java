package com.jerryio.protocol_diagram.token;

public record Digit1To9(char value) implements Token {
    public static Digit1To9 parse(CodePointBuffer buffer) {
        return Token.doParseCodepoint(buffer, new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9' },
                Digit1To9.class);
    }
}
