package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Token;

public class TokenTest {
    @Test
    public void testTokenDelimiterMethods() {
        assertNull(Token.parse(new CodePointBuffer("")));
        assertTrue(Token.isDelimiter(' '));
        assertTrue(Token.isDelimiter(null));
        assertFalse(Token.isDelimiter('\''));
        assertTrue(Token.isSafeDelimiter(null));
        assertTrue(Token.isSafeDelimiter(' '));
        assertTrue(Token.isSafeDelimiter(':'));
        assertTrue(Token.isSafeDelimiter(','));
        assertFalse(Token.isSafeDelimiter('\''));
        assertNull(Token.doNewInstance(null, null));
        assertNull(Token.doNewInstance(Token.class, null));
    }
}
