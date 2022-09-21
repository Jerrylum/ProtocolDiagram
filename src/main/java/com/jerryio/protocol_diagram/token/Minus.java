package com.jerryio.protocol_diagram.token;

public record Minus(char value) implements Token {
    public static Minus parse(CodePointBuffer buffer) {
        return Token.doParseCodepoint(buffer, new char[] { '-' }, Minus.class);
    }
}
