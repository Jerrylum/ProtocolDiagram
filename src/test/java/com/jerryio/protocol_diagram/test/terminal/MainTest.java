package com.jerryio.protocol_diagram.test.terminal;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jerryio.protocol_diagram.Main;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;

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
        arg[0] = "-p";
        Main.main(arg);//"test1(1) test2(2) test3(3)"
        arg[0] = "--print";
        Main.main(arg);//"test1(1) test2(2) test3(3)"
        arg[0] = "-h";
        Main.main(arg);
        // Usage: java -jar protocol_diagram.jar [options]
        //     Options:
        //         --help, -h
        //         Show this help message
        //         --print, -p
        //         Print the diagram to console and exit
        //         Default: false
        //         --single-line, -s
        //         Input the protocol in a single line
        arg[0] = "--help";
        Main.main(arg);
        //same as last trial
        String[] arg2 = new String[2];
        arg2[0] = "-s";
        arg2[1] = "tcp";
        Main.main(arg2);



    }

    
}
