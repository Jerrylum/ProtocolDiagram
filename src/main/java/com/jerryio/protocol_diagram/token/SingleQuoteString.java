package com.jerryio.protocol_diagram.token;

import com.jerryio.protocol_diagram.util.TokenUtils;

public record SingleQuoteString(String value, String content) implements Token {
    public static SingleQuoteString parse(CodePointBuffer buffer) {
        return TokenUtils.doParseQuoteString(buffer, '\'', SingleQuoteString.class);
    }
}
