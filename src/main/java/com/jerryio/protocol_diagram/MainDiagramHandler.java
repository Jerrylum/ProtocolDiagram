package com.jerryio.protocol_diagram;

import static com.jerryio.protocol_diagram.command.HandleResult.*;

import com.jerryio.protocol_diagram.command.CancellableCommand;
import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Timeline;
import com.jerryio.protocol_diagram.util.FileUtils;

public final class MainDiagramHandler extends Timeline<CancellableCommand> {

    // non-normalized path input by user, null means no file is mounted
    private String sourceFilePath;
    // The current memento saved in the file or the first memento in the timeline
    private Diagram.Memento sourceCurrentMemento;
    private boolean isModified;

    public MainDiagramHandler() {
        super(null);
        newDiagram();
    }

    @Override
    public Diagram getDiagram() {
        return Main.diagram;
    }

    public void setDiagram(Diagram diagram) {
        Main.diagram = diagram;
    }

    public void newDiagram() {
        setDiagram(new Diagram());
        sourceFilePath = null;
        resetHistory();
    }

    public boolean isModified() {
        return isModified || (sourceCurrentMemento != getLatestMemento());
    }

    public void setModified(boolean isModified) {
        this.isModified = isModified;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public HandleResult load(String path) {
        Diagram diagram = FileUtils.load(path);

        if (diagram == null) {
            return fail("Failed to load diagram from " + path);
        } else {
            setDiagram(diagram);
            sourceFilePath = path;
            resetHistory();
            return HANDLED;
        }
    }

    public HandleResult save(String path) {
        HandleResult result = FileUtils.save(path, Main.diagram);
        if (result.success()) {
            sourceFilePath = path;
            sourceCurrentMemento = getLatestMemento();
            isModified = false;
        }

        return result;
    }

    @Override
    public void resetHistory() {
        super.resetHistory();
        sourceCurrentMemento = getLatestMemento();
        isModified = false;
    }

}
