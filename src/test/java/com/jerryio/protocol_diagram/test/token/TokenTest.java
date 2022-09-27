package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Token;

public class TokenTest {
    @Test
    public void testTokenDelimiterMethods() {
        assertEquals(Token.parse(new CodePointBuffer("")), null);
        assertEquals(Token.isDelimiter(' '), true);
        assertEquals(Token.isDelimiter(null), true);
        assertEquals(Token.isDelimiter('\''), false);
        assertEquals(Token.isSafeDelimiter(null), true);
        assertEquals(Token.isSafeDelimiter(' '), true);
        assertEquals(Token.isSafeDelimiter(':'), true);
        assertEquals(Token.isSafeDelimiter(','), true);
        assertEquals(Token.isSafeDelimiter('\''), false);
        assertNull(Token.doNewInstance(null, null));
        assertNull(Token.doNewInstance(Token.class, null));
    }
}
