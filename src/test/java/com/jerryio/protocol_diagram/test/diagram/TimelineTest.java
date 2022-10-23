package com.jerryio.protocol_diagram.test.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.ICancellable;
import com.jerryio.protocol_diagram.diagram.Timeline;

public class TimelineTest {
    
    @Test
    public void testTimeline() {
        
        class TestICancellable implements ICancellable {
            public int countExecute = 0;
            public void execute() {
                countExecute++;
            }
        }

        class TestDiagram extends Diagram {
            public int countCreate = 0;
            public int countRestore = 0;
            public Memento createMemento() {
                countCreate++;
                return super.createMemento();
            }
            public void restoreFromMemento(Diagram.Memento memento) {
                countRestore++;
            }
        }

        
        Diagram d = new Diagram();
        Timeline<TestICancellable> tl = new Timeline<>(d);

        d.addField(new Field("test", 3));
        Diagram compare = new Diagram();
        TestICancellable tic = new TestICancellable();
        tl.operate(tic);
        compare.restoreFromMemento(tl.getLatestMemento());
        assertEquals(compare.toString(), d.toString());


        TestICancellable tic2 = new TestICancellable();
        TestDiagram td = new TestDiagram();
        Timeline<TestICancellable> tl2 = new Timeline<>(td);
        assertEquals(0, tic2.countExecute);
        assertEquals(1, td.countCreate);
        assertEquals(0, td.countRestore);
        tic2.execute();
        tl2.operate(tic2);
        assertEquals(1, tic2.countExecute);
        assertEquals(2, td.countCreate);
        assertEquals(0, td.countRestore);
        assertEquals(tic2, tl2.undo());
        assertEquals(1, tic2.countExecute);
        assertEquals(2, td.countCreate);
        assertEquals(1, td.countRestore);
        assertEquals(tic2, tl2.redo());
        assertEquals(2, tic2.countExecute);
        assertEquals(3, td.countCreate);
        assertEquals(1, td.countRestore);

        TestICancellable tic3 = new TestICancellable();
        TestICancellable tic4 = new TestICancellable();
        TestICancellable tic5 = new TestICancellable();

        tic3.execute();
        tl2.operate(tic3);
        tic4.execute();
        tl2.operate(tic4);
        tic5.execute();
        tl2.operate(tic5);
        assertEquals(1, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(6, td.countCreate);
        assertEquals(1, td.countRestore);

        assertEquals(tic5, tl2.undo());
        assertEquals(1, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(6, td.countCreate);
        assertEquals(2, td.countRestore);

        assertEquals(tic4, tl2.undo());
        assertEquals(1, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(6, td.countCreate);
        assertEquals(3, td.countRestore);

        assertEquals(tic3, tl2.undo());
        assertEquals(1, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(6, td.countCreate);
        assertEquals(4, td.countRestore);

        assertEquals(tic2, tl2.undo());
        assertEquals(1, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(6, td.countCreate);
        assertEquals(5, td.countRestore);

        assertNull(tl2.undo());

        assertEquals(1, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(6, td.countCreate);
        assertEquals(5, td.countRestore);

        assertEquals(tic2, tl2.redo());
        assertEquals(1, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(7, td.countCreate);
        assertEquals(5, td.countRestore);

        assertEquals(tic3, tl2.redo());
        assertEquals(2, tic3.countExecute);
        assertEquals(1, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(8, td.countCreate);
        assertEquals(5, td.countRestore);

        assertEquals(tic4, tl2.redo());
        assertEquals(2, tic3.countExecute);
        assertEquals(2, tic4.countExecute);
        assertEquals(1, tic5.countExecute);
        assertEquals(9, td.countCreate);
        assertEquals(5, td.countRestore);

        assertEquals(tic5, tl2.redo());
        assertEquals(2, tic3.countExecute);
        assertEquals(2, tic4.countExecute);
        assertEquals(2, tic5.countExecute);
        assertEquals(10, td.countCreate);
        assertEquals(5, td.countRestore);

        assertNull(tl2.redo());

        assertEquals(tic5, tl2.undo());
        TestICancellable tic6 = new TestICancellable();
        tic6.execute();
        tl2.operate(tic6);
        assertEquals(11, td.countCreate);
        assertEquals(6, td.countRestore);
        assertNull(tl2.redo());

        assertEquals(tic6, tl2.undo());
        assertEquals(11, td.countCreate);
        assertEquals(7, td.countRestore);
        assertEquals(tic4, tl2.undo());
        assertEquals(11, td.countCreate);
        assertEquals(8, td.countRestore);
        tl2.resetHistory();
        assertEquals(12, td.countCreate);
        assertEquals(8, td.countRestore);

        assertNull(tl2.undo());
        assertNull(tl2.redo());
    }
    
}
