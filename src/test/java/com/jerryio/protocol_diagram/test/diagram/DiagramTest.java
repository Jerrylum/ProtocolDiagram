package com.jerryio.protocol_diagram.test.diagram;

import com.jerryio.protocol_diagram.diagram.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DiagramTest {
    @Test
    public void testDiagramAdd() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        assertEquals(dtest.getField(0).getName(), "testadd");
        assertEquals(dtest.size(), 1);
        dtest.addField(new Field("testadd2", 2));
        assertEquals(dtest.getField(1).getName(), "testadd2");
        assertEquals(dtest.size(), 2);
    }

    @Test
    public void testDiagramDelete() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.removeField(1);
        assertEquals(dtest.getField(1).getName(), "testadd3");
        assertEquals(dtest.size(), 2);
        dtest.removeField(0);
        assertEquals(dtest.getField(0).getName(), "testadd3");
        assertEquals(dtest.size(), 1);
        dtest.removeField(0);
        assertEquals(dtest.size(), 0);
    }

    @Test
    public void testDiagramInsert(){
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.insertField(1, new Field("testadd4", 4));
        assertEquals(dtest.getField(1).getName(), "testadd4");
        assertEquals(dtest.getField(2).getName(), "testadd2");
        assertEquals(dtest.size(), 4);
    }

    @Test
    public void testDiagramMove(){
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(0, 2);
        assertEquals(dtest.getField(0).getName(), "testadd2");
        assertEquals(dtest.getField(1).getName(), "testadd3");
        assertEquals(dtest.getField(2).getName(), "testadd");
        assertEquals(dtest.size(), 3);
    }

    @Test
    public void testDiagramMove2(){
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(2, 0);
        assertEquals(dtest.getField(0).getName(), "testadd3");
        assertEquals(dtest.getField(1).getName(), "testadd");
        assertEquals(dtest.getField(2).getName(), "testadd2");
        assertEquals(dtest.size(), 3);
    }

    @Test
    public void testDiagramMove3(){
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(1, 1);
        assertEquals(dtest.getField(0).getName(), "testadd");
        assertEquals(dtest.getField(1).getName(), "testadd2");
        assertEquals(dtest.getField(2).getName(), "testadd3");
        assertEquals(dtest.size(), 3);
    }

    @Test
    public void testDiagramMove4(){
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(1, 0);
        assertEquals(dtest.getField(0).getName(), "testadd2");
        assertEquals(dtest.getField(1).getName(), "testadd");
        assertEquals(dtest.getField(2).getName(), "testadd3");
        assertEquals(dtest.size(), 3);
    }

    @Test
    public void testDiagramMove5(){
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(0, 1);
        assertEquals(dtest.getField(0).getName(), "testadd2");
        assertEquals(dtest.getField(1).getName(), "testadd");
        assertEquals(dtest.getField(2).getName(), "testadd3");
        assertEquals(dtest.size(), 3);
    }

    @Test
    public void testDiagramClear() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.clear();
        assertEquals(dtest.size(), 0);
    }

}
