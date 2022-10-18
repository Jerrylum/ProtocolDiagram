package com.jerryio.protocol_diagram.diagram;

import java.util.Stack;

import com.jerryio.protocol_diagram.command.ICancellable;

public class Timeline {
    private Diagram diagram;
    private Diagram.Memento latest;
    private Stack<Snapshot> undoStack;
    private Stack<ICancellable> redoStack;

    public Timeline(Diagram diagram) {
        this.diagram = diagram;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        reset();
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public Diagram.Memento getLatestMemento() {
        return latest;
    }

    public void reset() {
        undoStack.clear();
        redoStack.clear();
        latest = getDiagram().createMemento();
    }

    public void add(ICancellable command) {
        undoStack.push(new Snapshot(latest, command));
        redoStack.clear();
        latest = getDiagram().createMemento();
    }

    public ICancellable undo() {
        if (undoStack.isEmpty())
            return null;

        Snapshot snapshot = undoStack.pop();
        redoStack.push(snapshot.command());
        getDiagram().restoreFromMemento(latest = snapshot.origin());

        return snapshot.command();
    }

    public ICancellable redo() {
        if (redoStack.isEmpty())
            return null;

        ICancellable command = redoStack.pop();
        command.execute();
        undoStack.push(new Snapshot(latest, command));
        latest = getDiagram().createMemento();

        return command;
    }
}
