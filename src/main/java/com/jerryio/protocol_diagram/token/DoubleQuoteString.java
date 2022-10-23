package com.jerryio.protocol_diagram.token;

import com.jerryio.protocol_diagram.util.TokenUtils;

public record DoubleQuoteString(String value, String content) implements Token {
    public static DoubleQuoteString parse(CodePointBuffer buffer) {
        return TokenUtils.doParseQuoteString(buffer, '"', DoubleQuoteString.class);
    }
}
