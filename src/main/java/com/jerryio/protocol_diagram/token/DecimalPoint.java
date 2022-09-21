package com.jerryio.protocol_diagram.token;

public record DecimalPoint(char value) implements Token {
    public static DecimalPoint parse(CodePointBuffer buffer) {
        return Token.doParseCodepoint(buffer, new char[] { '.' }, DecimalPoint.class);
    }
}
