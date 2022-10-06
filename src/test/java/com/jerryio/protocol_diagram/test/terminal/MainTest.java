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

    public void init(String[] args){
        setup();
        Main.main(args);
    }

    @Test
    public void testHelp(){
        String[] arg = new String[1];
        ByteArrayOutputStream out;
        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        arg[0] = "--help";
        init(arg);
        assertEquals(out.toString().startsWith("Usage: java -jar protocol_diagram.jar [options]"), true);

        out.reset();
        arg[0] = "-h";
        init(arg);
        assertEquals(out.toString().startsWith("Usage: java -jar protocol_diagram.jar [options]"), true);
    }

    @Test
    public void testPrint(){
        String[] arg = new String[1];
        ByteArrayOutputStream out;
        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        out.reset();
        arg[0] = "-p";
        init(arg);//"test1(1) test2(2) test3(3)"
        assertEquals(out.toString().startsWith("test1(1) test2(2) test3(3)"), true);

        out.reset();
        arg[0] = "--print";
        init(arg);//"test1(1) test2(2) test3(3)"
        assertEquals(out.toString().startsWith("test1(1) test2(2) test3(3)"), true);
    }
    @Test
    public void testSingleLine(){
        String[] arg = new String[3];
        ByteArrayOutputStream out;
        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        out.reset();
        arg[0] = "--single-line";
        arg[1] = "test4: 4";
        arg[2] = "-p";
        init(arg);//"test1(1) test2(2) test3(3) test4(4)"
        assertEquals(Main.diagram.getFields().size(), 4);
        assertEquals(out.toString().startsWith("test1(1) test2(2) test3(3) test4(4)"), true);

        out.reset();
        setup();
        arg = new String[3];
        arg[0] = "-s";
        arg[1] = "test4: 4,test5:5";
        arg[2] = "-p";
        init(arg);//"test1(1) test2(2) test3(3) test4(4)"
        assertEquals(Main.diagram.getFields().size(), 5);
        assertEquals(out.toString().startsWith("test1(1) test2(2) test3(3) test4(4) test5(5)"), true);
        
    }

    @Test
    public void testErrorMessage(){
        String[] arg = new String[1];
        ByteArrayOutputStream out;
        out = new ByteArrayOutputStream();    
        System.setOut(new java.io.PrintStream(out)); 
        out.reset();
        arg[0] = "-print";
        init(arg);//"test1(1) test2(2) test3(3)"
        assertEquals(out.toString().startsWith("Was passed main parameter '"+arg[0]+"' but no main parameter was defined in your arg class"), true);

        out.reset();
        arg[0] = "--single-line";
        init(arg);
        assertEquals(out.toString().startsWith("Expected a value after parameter --single-line"), true);

        out.reset();
        arg[0] = "-s";
        init(arg);
        assertEquals(out.toString().startsWith("Expected a value after parameter -s"), true);

        out.reset();
        arg = new String[2];
        arg[0] = "--single-line";
        arg[1] = "12321";
        init(arg);
        assertEquals(out.toString().startsWith("Invalid single line input"), true);
    }
    
}
