package com.jerryio.protocol_diagram.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jerryio.protocol_diagram.ExistingProtocol;
import com.jerryio.protocol_diagram.Main;

public class ExistingProtocolTest {

    @Test
    public void testAllProtocolsParsing() {
        new ExistingProtocol(); // dummy

        for (String protocol : ExistingProtocol.getProtocols().values()) {
            Main.diagram.clear();
            assertTrue(Main.doHandleSingleLine(protocol).success());
        }
    }
}
