package com.jerryio.protocol_diagram.token;

public interface Token {
    /**
     * a static function that tokenize the CodePointBuffer and return a processed token,
     * by default, it will return null if the parse function is not implemented
     * @param buffer
     * @return
     */
    public static Token parse(CodePointBuffer buffer) {
        return null;
    }
}
