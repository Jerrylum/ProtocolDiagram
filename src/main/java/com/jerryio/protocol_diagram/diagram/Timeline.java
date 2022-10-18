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

    public Diagram getDiagram() {
        return diagram;
    }

    public Diagram.Memento getLatestMemento() {
        return latest;
    }

    public void resetHistory() {
        undoStack.clear();
        redoStack.clear();
        latest = getDiagram().createMemento();
    }

    public void operate(T modifier) {
        undoStack.push(new Snapshot<T>(latest, modifier));
        redoStack.clear();
        latest = getDiagram().createMemento();
    }

    public T undo() {
        if (undoStack.isEmpty())
            return null;

        Snapshot<T> snapshot = undoStack.pop();
        redoStack.push(snapshot.modifier());
        getDiagram().restoreFromMemento(latest = snapshot.origin());

        return snapshot.modifier();
    }

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
