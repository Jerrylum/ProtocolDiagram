package com.jerryio.protocol_diagram.token;

import com.jerryio.protocol_diagram.util.TokenUtils;

public record DecimalPoint(char value) implements Token {
    public static DecimalPoint parse(CodePointBuffer buffer) {
        return TokenUtils.doParseCodepoint(buffer, new char[] { '.' }, DecimalPoint.class);
    }
}
