package com.jerryio.protocol_diagram.token;

import com.jerryio.protocol_diagram.util.TokenUtils;

/**
 * this record is a data class that memorizes the character of the zero, which theoretically only be '0'
 */
public record Zero(char value) implements Token {
    public static Zero parse(CodePointBuffer buffer) {
        return TokenUtils.doParseCodepoint(buffer, new char[] { '0' }, Zero.class);
    }
}
