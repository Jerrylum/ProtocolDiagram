package com.jerryio.protocol_diagram.token;

/**
 * this interface is used to distinguish whether the class is a token 
 */
public interface Token {
    /**
     * a static function that tokenize the CodePointBuffer and returns a processed
     * token, by default, it will return null if the parse function is not
     * implemented
     * 
     * @param buffer the CodePointBuffer to be tokenized
     * @return the processed token
     */
    public static Token parse(CodePointBuffer buffer) {
        return null;
    }
}
