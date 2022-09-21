package com.jerryio.protocol_diagram.token;

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
