package com.jerryio.protocol_diagram.token;

public record Zero(char value) implements Token {
    public static Zero parse(CodePointBuffer buffer) {
        return Token.doParseCodepoint(buffer, new char[] { '0' }, Zero.class);
    }
}
