package com.jerryio.protocol_diagram.util;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Token;

public class TokenUtils {

    /**
     * a utility function that checks whether the character is delimiter
     * (null | ' ')
     * 
     * @param c
     * @return
     */
    public static boolean isDelimiter(Character c) {
        return c == null || c == ' ';
    }

    /**
     * a utility function that checks whether the character is safe delimiter
     * (null | ' ' | ':' | ',')
     * 
     * @param c
     * @return
     */
    public static boolean isSafeDelimiter(Character c) {
        return c == null || c == ' ' || c == ':' || c == ',';
    }

    /* Start internal functions */

    /**
     * a utility function that transforms a arguments of classes to an array of
     * classes
     * 
     * @param clazz
     * @return
     */
    public static Class<?>[] doGetClasses(Class<?>... clazz) {
        return clazz;
    }

    /**
     * a utility function that creates new instance T via the inputted parameter
     * class T, an array of parameter types of the T's constructor, and an array of
     * parameter values of the T's constructor
     * 
     * @param <T>
     * @param token_class
     * @param args_types
     * @param args
     * @return
     */
    public static <T extends Token> T doNewInstance(Class<T> token_class, Class<?>[] args_types, Object... args) {
        // This is equivalent to "return new TokenClass(a, b, c, d);"
        try {
            return token_class.getDeclaredConstructor(args_types).newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * a utility function that reads the code point buffer, returns a Token instance
     * typed with T which given by the parameter class T if the code point buffer
     * does match one of our specified acceptable characters, else return null
     * 
     * @param <T>
     * @param buffer
     * @param accepts
     * @param token_class
     * @return
     */
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

    /**
     * an utility function that reads the code point buffer and return a Token
     * instance usually typed with SingleQuotedString or DoubleQuotedString
     * 
     * @param <T>
     * @param buffer
     * @param quote
     * @param token_class
     * @return
     */
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
