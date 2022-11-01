package com.jerryio.protocol_diagram.diagram;

import java.util.Stack;

public class Timeline<T extends ICancellable> {
    private Diagram diagram;
    private Diagram.Memento latest;
    private Stack<Snapshot<T>> undoStack;
    private Stack<T> redoStack;

    public Timeline(Diagram diagram) {
        this.diagram = diagram;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        resetHistory();
    }

    /**
     * a getter method that returns the diagram it stored
     * 
     * @return Diagram
     */
    public Diagram getDiagram() {
        return diagram;
    }

    /**
     * a getter method that returns the latest memento
     * 
     * @return Diagram.Memento
     */
    public Diagram.Memento getLatestMemento() {
        return latest;
    }

    /**
     * a method that eliminates all undo history and redo history, and generates a
     * new memento
     */
    public void resetHistory() {
        undoStack.clear();
        redoStack.clear();
        latest = getDiagram().createMemento();
    }

    /**
     * a method that pushes a modifier into the undo history, and resets the stack
     * of redo.
     * 
     * @param modifier the modifier that is going to be pushed into the undo history
     */
    public void operate(T modifier) {
        undoStack.push(new Snapshot<T>(latest, modifier));
        redoStack.clear();
        latest = getDiagram().createMemento();
    }

    /**
     * a method that pops the top of the undo stack, pushes that popped history into
     * the redo stack, and restores the diagram based on the popped snapshot.
     * 
     * @return T
     */
    public T undo() {
        if (undoStack.isEmpty())
            return null;

        Snapshot<T> snapshot = undoStack.pop();
        redoStack.push(snapshot.modifier());
        getDiagram().restoreFromMemento(latest = snapshot.origin());

        return snapshot.modifier();
    }

    /**
     * a method that pops the top of the redo stack, pushes that popped history into
     * the undo stack, and executes the popped command from the redo stack.
     * 
     * @return T
     */
    public T redo() {
        if (redoStack.isEmpty())
            return null;

        T modifier = redoStack.pop();
        modifier.execute();
        undoStack.push(new Snapshot<T>(latest, modifier));
        latest = getDiagram().createMemento();

        return modifier;
    }
}
