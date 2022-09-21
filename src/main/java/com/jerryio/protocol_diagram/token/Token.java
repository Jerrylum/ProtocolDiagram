package com.jerryio.protocol_diagram.token;

public interface Token {
    public static Token parse(CodePointBuffer buffer) {
        return null;
    }

    public static boolean isDelimiter(Character c) {
        return c == null || c == ' ';
    }

    public static boolean isSafeDelimiter(Character c) {
        return c == null || c == ' ' || c == ':' || c == ',';
    }

    /* Start internal functions */

    public static Class<?>[] doGetClasses(Class<?>... clazz) {
        return clazz;
    }

    public static <T extends Token> T doNewInstance(Class<T> token_class, Class<?>[] args_types, Object... args) {
        // This is equivalent to "return new TokenClass(a, b, c, d);"
        try {
            return token_class.getDeclaredConstructor(args_types).newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends Token> T doParseCodepoint(CodePointBuffer buffer, char[] accepts, Class<T> token_class) {
        buffer.savepoint();

        Character target = buffer.next();
        for (char c : accepts) {
            if (target == (Character) c) {
                return buffer.commitAndReturn(doNewInstance(token_class, doGetClasses(char.class), (char) c));
            }
        }
        return buffer.rollbackAndReturn(null);
    }

    public static <T extends Token> T doParseQuoteString(CodePointBuffer buffer, char quote, Class<T> token_class) {
        buffer.savepoint();

        StringBuilder value_sb = new StringBuilder();
        StringBuilder content_sb = new StringBuilder();
        boolean escape = false;
        boolean open = true;

        if (buffer.next() != (Character) quote) {
            return buffer.rollbackAndReturn(null);
        }
        value_sb.append(quote);

        while (buffer.hasNext()) {
            char c = buffer.next();

            if (escape) {
                escape = false;
                content_sb.append(c);
                value_sb.append(c);
            } else {
                if (c == '\\') {
                    escape = true;
                    value_sb.append(c);
                } else if (c == quote) {
                    open = false;
                    value_sb.append(c);
                    break;
                } else {
                    value_sb.append(c);
                    content_sb.append(c);
                }
            }
        }

        if (open) {
            return buffer.rollbackAndReturn(null);
        } else {
            return buffer.commitAndReturn(doNewInstance(
                    token_class,
                    doGetClasses(String.class, String.class),
                    value_sb.toString(), content_sb.toString()));
        }
    }
}
