package com.jerryio.protocol_diagram.token;

/**
 * this record is a universalized string literal, contains only the inner-value of both double-quoted string and single-quoted string
 */
public record StringT(String content) implements Token {
    public static StringT parse(CodePointBuffer buffer) {
        Character c = buffer.peek();
        String content;

        if (c == null) {
            return null;
        } else if (c == '"') {
            DoubleQuoteString d = DoubleQuoteString.parse(buffer);
            if (d == null)
                return null;
            content = d.content();
        } else if (c == '\'') {
            SingleQuoteString s = SingleQuoteString.parse(buffer);
            if (s == null)
                return null;
            content = s.content();
        } else {
            content = buffer.readChunk();
        }
        return new StringT(content);
    }
}
