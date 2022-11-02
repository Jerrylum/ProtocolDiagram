package com.jerryio.protocol_diagram.token;

/**
 * this record is a safer version of the universalized string literal, contains only the inner-value of
 * both double-quoted string and single-quoted string, however, if the inner-value of the string literal
 * is empty, the static parse function of this record yields the constant null instead.
 */
public record SafeString(String content) implements Token {
    public static SafeString parse(CodePointBuffer buffer) {
        StringBuffer sb = new StringBuffer();

        String word = buffer.readSafeChunk();
        if (word.equals(""))
            return null;
        sb.append(word);
        // while (buffer.hasNext() && Token.isSafeDelimiter(buffer.peek())) {

        while (buffer.hasNext() && buffer.peek() != ':' && buffer.peek() != ',') {
            sb.append(buffer.next());
            sb.append(buffer.readSafeChunk());
        }

        return new SafeString(sb.toString().trim());
    }
}
