package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

public class CommandTest {
    @Test
    public void testCommandGet() {
        class TestCommand extends com.jerryio.protocol_diagram.command.Command {
            public TestCommand() {
                super("test", "test usage", "test description");
            }

            @Override
            public HandleResult handle(List<Parameter> params) {
                return null;
            }
        }
        TestCommand tc = new TestCommand();
        assertEquals("test", tc.getName());
        assertEquals("test usage", tc.getUsage());
        assertEquals("test description", tc.getDescription());
        assertNull(tc.handle(new ArrayList<Parameter>()));
    }
}
