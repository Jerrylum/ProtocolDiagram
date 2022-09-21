package com.jerryio.protocol_diagram.token;

public record DoubleQuoteString(String value, String content) implements Token {
    public static DoubleQuoteString parse(CodePointBuffer buffer) {
        return Token.doParseQuoteString(buffer, '"', DoubleQuoteString.class);
    }
}
