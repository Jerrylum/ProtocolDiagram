package com.jerryio.protocol_diagram.token;

public record Digit(char value) implements Token {
    public static Digit parse(CodePointBuffer buffer) {
        return Token.doParseCodepoint(buffer, new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' },
                Digit.class);
    }
}
