package com.jerryio.protocol_diagram.token;

public record SingleQuoteString(String value, String content) implements Token {
    public static SingleQuoteString parse(CodePointBuffer buffer) {
        return Token.doParseQuoteString(buffer, '\'', SingleQuoteString.class);
    }
}
