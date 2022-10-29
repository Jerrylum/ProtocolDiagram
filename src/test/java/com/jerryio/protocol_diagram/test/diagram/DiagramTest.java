package com.jerryio.protocol_diagram.test.diagram;

import com.jerryio.protocol_diagram.diagram.*;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Parameter;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiagramTest {
    @Test
    public void testDiagramAdd() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        assertEquals("testadd", dtest.getField(0).getName());
        assertEquals(1, dtest.size());
        dtest.addField(new Field("testadd2", 2));
        assertEquals("testadd2", dtest.getField(1).getName());
        assertEquals(2, dtest.size());
    }

    @Test
    public void testDiagramDelete() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.removeField(1);
        assertEquals("testadd3", dtest.getField(1).getName());
        assertEquals(2, dtest.size());
        dtest.removeField(0);
        assertEquals("testadd3", dtest.getField(0).getName());
        assertEquals(1, dtest.size());
        dtest.removeField(0);
        assertEquals(0, dtest.size());
    }

    @Test
    public void testDiagramInsert() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.insertField(1, new Field("testadd4", 4));
        assertEquals("testadd4", dtest.getField(1).getName());
        assertEquals("testadd2", dtest.getField(2).getName());
        assertEquals(4, dtest.size());
    }

    @Test
    public void testDiagramMove() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(0, 2);
        assertEquals("testadd2", dtest.getField(0).getName());
        assertEquals("testadd3", dtest.getField(1).getName());
        assertEquals("testadd", dtest.getField(2).getName());
        assertEquals(3, dtest.size());
    }

    @Test
    public void testDiagramMove2() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(2, 0);
        assertEquals("testadd3", dtest.getField(0).getName());
        assertEquals("testadd", dtest.getField(1).getName());
        assertEquals("testadd2", dtest.getField(2).getName());
        assertEquals(3, dtest.size());
    }

    @Test
    public void testDiagramMove3() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(1, 1);
        assertEquals("testadd", dtest.getField(0).getName());
        assertEquals("testadd2", dtest.getField(1).getName());
        assertEquals("testadd3", dtest.getField(2).getName());
        assertEquals(3, dtest.size());
    }

    @Test
    public void testDiagramMove4() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(1, 0);
        assertEquals("testadd2", dtest.getField(0).getName());
        assertEquals("testadd", dtest.getField(1).getName());
        assertEquals("testadd3", dtest.getField(2).getName());
        assertEquals(3, dtest.size());
    }

    @Test
    public void testDiagramMove5() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.moveField(0, 1);
        assertEquals("testadd2", dtest.getField(0).getName());
        assertEquals("testadd", dtest.getField(1).getName());
        assertEquals("testadd3", dtest.getField(2).getName());
        assertEquals(3, dtest.size());
    }

    @Test
    public void testDiagramClear() {
        Diagram dtest = new Diagram();
        dtest.addField(new Field("testadd", 1));
        dtest.addField(new Field("testadd2", 2));
        dtest.addField(new Field("testadd3", 3));
        dtest.clear();
        assertEquals(0, dtest.size());
    }

    @Test
    public void testReadWriteJson() {
        Diagram d1 = new Diagram();

        assertNull(Diagram.fromJson(""));

        assertNull(Diagram.fromJson(null));

        String empty = "{\"fields\":[]}";
        assertEquals(Diagram.fromJson(empty).toJson(), d1.toJson());

        empty = "{\"config\":{}}";
        assertEquals(Diagram.fromJson(empty).toJson(), d1.toJson());

        empty = "{\"something new\":{}}";
        assertEquals(Diagram.fromJson(empty).toJson(), d1.toJson());

        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());

        d1.addField(new Field("test4", 4));
        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());

        d1.addField(new Field("test5", 5));
        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());

        d1.getConfig().setValue("bit", Parameter.parse(new CodePointBuffer("8")));
        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());

        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("ascii-verbose")));
        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());

        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("utf8-header")));
        assertEquals(Diagram.fromJson(d1.toJson()).toString(), d1.toString());

        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("ascii")));
        assertEquals(Diagram.fromJson(d1.toJson()).toString(), d1.toString());

        d1.getConfig().setValue("header-style", Parameter.parse(new CodePointBuffer("full")));
        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());

        d1.getConfig().setValue("left-space-placeholder", Parameter.parse(new CodePointBuffer("true")));
        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());

        d1.getConfig().setValue("left-space-placeholder", Parameter.parse(new CodePointBuffer("true")));
        d1.getConfig().setValue("header-style", Parameter.parse(new CodePointBuffer("trim")));
        d1.getConfig().setValue("diagram-style", Parameter.parse(new CodePointBuffer("utf8-corner")));
        d1.getConfig().setValue("bit", Parameter.parse(new CodePointBuffer("16")));
        d1.addField(new Field("test6", 6));
        assertEquals(Diagram.fromJson(d1.toJson()).toJson(), d1.toJson());
    }

}
