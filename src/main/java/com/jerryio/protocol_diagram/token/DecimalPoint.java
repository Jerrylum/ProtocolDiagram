package com.jerryio.protocol_diagram.token;

import com.jerryio.protocol_diagram.util.TokenUtils;

/**
 * this record is a data class that memorizes the character of a decimal point, which theoretically only be '.'
 */
public record DecimalPoint(char value) implements Token {
    public static DecimalPoint parse(CodePointBuffer buffer) {
        return TokenUtils.doParseCodepoint(buffer, new char[] { '.' }, DecimalPoint.class);
    }
}
