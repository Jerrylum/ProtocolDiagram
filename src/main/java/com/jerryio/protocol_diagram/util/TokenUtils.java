package com.jerryio.protocol_diagram.util;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Token;

/**
 * a utility class that contains only static functions, for the ease of other
 * classes reuse the code without instantiating an object before the usage, this
 * class is responsible in the token utility functions
 */
public class TokenUtils {

    /**
     * a utility function that checks whether the character is delimiter
     * (null | ' ')
     * 
     * @param c the character to be checked
     * @return whether the character is delimiter
     */
    public static boolean isDelimiter(Character c) {
        return c == null || c == ' ';
    }

    /**
     * a utility function that checks whether the character is safe delimiter
     * (null | ' ' | ':' | ',')
     * 
     * @param c the character to be checked
     * @return whether the character is safe delimiter
     */
    public static boolean isSafeDelimiter(Character c) {
        return c == null || c == ' ' || c == ':' || c == ',';
    }

    /* Start internal functions */

    /**
     * a utility function that transforms a arguments of classes to an array of
     * classes
     * 
     * @param clazz the classes to be transformed
     * @return the array of classes
     */
    public static Class<?>[] doGetClasses(Class<?>... clazz) {
        return clazz;
    }

    /**
     * a utility function that creates new instance T via the inputted parameter
     * class T, an array of parameter types of the T's constructor, and an array of
     * parameter values of the T's constructor
     * 
     * @param <T>         the type of the instance to be created
     * @param token_class the class of the instance to be created
     * @param args_types  the array of parameter types of the T's constructor
     * @param args        the array of parameter values of the T's constructor
     * @return the instance of T
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
     * @param <T>         the type of the token to be created
     * @param buffer      the code point buffer to be read
     * @param accepts     the array of acceptable characters
     * @param token_class the class of the token to be created
     * @return the token instance
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
     * @param <T>         the type of the token
     * @param buffer      the code point buffer
     * @param quote       the quote character
     * @param token_class the class of the token to be returned
     * @return the token instance
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
