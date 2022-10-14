package com.jerryio.protocol_diagram.test.command;

import static org.junit.Assert.assertEquals;

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
                // TODO Auto-generated method stub
                return null;
            }
        }
        TestCommand tc = new TestCommand();
        assertEquals(tc.getName(), "test");
        assertEquals(tc.getUsage(), "test usage");
        assertEquals(tc.getDescription(), "test description");
        assertEquals(tc.handle(new ArrayList<Parameter>()), null);
    }
}
