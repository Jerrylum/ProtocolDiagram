package com.jerryio.protocol_diagram.test.terminal;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import java.io.ByteArrayOutputStream;

public class MainTest {
    @Before
    public void setup(){
        Main.diagram = new Diagram();
        Main.diagram.addField(new Field("test1", 1));
        Main.diagram.addField(new Field("test2", 2));
        Main.diagram.addField(new Field("test3", 3));
    }
    @Test
    public void test(){
        String[] arg = new String[1];

        ByteArrayOutputStream out;
        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        arg[0] = "-h";
        Main.main(arg);
        assertEquals(out.toString().startsWith("Usage: java -jar protocol_diagram.jar [options]"), true);

        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        arg[0] = "--help";
        Main.main(arg);
        assertEquals(out.toString().startsWith("Usage: java -jar protocol_diagram.jar [options]"), true);

        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        arg[0] = "-p";
        Main.main(arg);//"test1(1) test2(2) test3(3)"
        assertEquals(out.toString().startsWith("test1(1) test2(2) test3(3)"), true);

        setup();
        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        arg[0] = "--print";
        Main.main(arg);//"test1(1) test2(2) test3(3)"
        assertEquals(out.toString().startsWith("test1(1) test2(2) test3(3)"), true);


        


    }

    
}
