package com.jerryio.protocol_diagram.token;

import com.jerryio.protocol_diagram.util.TokenUtils;

/**
 * this record is used to represent the negative sign and the minus symbol
 */
public record Minus(char value) implements Token {
    public static Minus parse(CodePointBuffer buffer) {
        return TokenUtils.doParseCodepoint(buffer, new char[] { '-' }, Minus.class);
    }
}
