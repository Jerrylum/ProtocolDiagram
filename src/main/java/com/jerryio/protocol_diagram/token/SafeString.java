package com.jerryio.protocol_diagram.token;

public record SafeString(String content) implements Token {
    public static SafeString parse(CodePointBuffer buffer) {
        StringBuffer sb = new StringBuffer();

        String word = buffer.readSafeChunk();
        if (word.equals(""))
            return null;
        sb.append(word);

        while (buffer.hasNext() && buffer.peek() != ':' && buffer.peek() != ',') {
            sb.append(buffer.next());
            sb.append(buffer.readSafeChunk());
        }

        return new SafeString(sb.toString().trim());
    }
}
