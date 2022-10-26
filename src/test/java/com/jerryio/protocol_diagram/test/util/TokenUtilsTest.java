package com.jerryio.protocol_diagram.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Token;
import com.jerryio.protocol_diagram.util.TokenUtils;

public class TokenUtilsTest {
    @Test
    public void testTokenDelimiterMethods() {
        new TokenUtils(); // dummy
        assertNull(Token.parse(new CodePointBuffer("")));
        assertTrue(TokenUtils.isDelimiter(' '));
        assertTrue(TokenUtils.isDelimiter(null));
        assertFalse(TokenUtils.isDelimiter('\''));
        assertTrue(TokenUtils.isSafeDelimiter(null));
        assertTrue(TokenUtils.isSafeDelimiter(' '));
        assertTrue(TokenUtils.isSafeDelimiter(':'));
        assertTrue(TokenUtils.isSafeDelimiter(','));
        assertFalse(TokenUtils.isSafeDelimiter('\''));
        assertNull(TokenUtils.doNewInstance(null, null));
        assertNull(TokenUtils.doNewInstance(Token.class, null));
    }
}
