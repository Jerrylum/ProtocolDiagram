package com.jerryio.protocol_diagram.test.token;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jerryio.protocol_diagram.token.BooleanT;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Token;

public class TokenTest {
    @Test
    public void testTokenVaild() {
        assertEquals(Token.parse(new CodePointBuffer("")), null);
        assertEquals(Token.isDelimiter(' '), true);
        assertEquals(Token.isDelimiter(null), true);
        assertEquals(Token.isDelimiter('\''), false);
        assertEquals(Token.isSafeDelimiter(null), true);
        assertEquals(Token.isSafeDelimiter(' '), true);
        assertEquals(Token.isSafeDelimiter(':'), true);
        assertEquals(Token.isSafeDelimiter(','), true);
        assertEquals(Token.isSafeDelimiter('\''), false);
        assertEquals(Token.doGetClasses(CodePointBuffer.class), CodePointBuffer.class);
        // assertEquals(Parameter.parse(new CodePointBuffer("0")).getInt(), 0);
        // assertEquals(Parameter.parse(new CodePointBuffer("0.0")).getDouble(), 0.0, 0.0001);
        // assertEquals(Parameter.parse(new CodePointBuffer("-14")).getInt(), -14);
        // assertEquals(Parameter.parse(new CodePointBuffer("-14.0")).getDouble(), -14.0, 0.0001);
    }


    @Test
    public void testParameterNull() {  

    }
}
