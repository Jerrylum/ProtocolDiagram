package com.jerryio.protocol_diagram.token;

import com.jerryio.protocol_diagram.util.TokenUtils;

/**
 * this record is a data class that wraps the raw string, and the inner value of a single-quoted string literal
 */
public record SingleQuoteString(String value, String content) implements Token {
    public static SingleQuoteString parse(CodePointBuffer buffer) {
        return TokenUtils.doParseQuoteString(buffer, '\'', SingleQuoteString.class);
    }
}
