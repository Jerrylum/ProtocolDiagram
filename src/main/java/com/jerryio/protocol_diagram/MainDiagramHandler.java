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

    /**
     * a instance method that gets the diagram from Main
     */
    @Override
    public Diagram getDiagram() {
        return Main.diagram;
    }

    /**
     * a instance method that sets the diagram from Main
     * @param diagram
     * @return void
     */
    public void setDiagram(Diagram diagram) {
        Main.diagram = diagram;
    }

    /**
     * a method that eliminate all previous diagram related logic, and re-create a new diagram
     * @return void
     */
    public void newDiagram() {
        setDiagram(new Diagram());
        sourceFilePath = null;
        resetHistory();
    }

    /**
     * a method that checks whether the program is modified
     * @return boolean
     */
    public boolean isModified() {
        return isModified || (sourceCurrentMemento != getLatestMemento());
    }

    /**
     * a method that sets whether the program is modified
     * @param isModified
     * @return void
     */
    public void setModified(boolean isModified) {
        this.isModified = isModified;
    }

    /**
     * a getter function that retrieve the source file path
     * @return String
     */
    public String getSourceFilePath() {
        return sourceFilePath;
    }

    /**
     * a method that create a diagram from a JSON file, eliminate all history and renew the source path
     * @param path
     * @return HandleResult
     */
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

    /**
     * a method that creates a JSON file from the current diagram, and set the flag `isModified` to false
     * @return HandleResult
     */
    public HandleResult save(String path) {
        HandleResult result = FileUtils.save(path, Main.diagram);
        if (result.success()) {
            sourceFilePath = path;
            sourceCurrentMemento = getLatestMemento();
            isModified = false;
        }

        return result;
    }

    /**
     * a method that eliminates all history and set the flag `isModified` to false
     * @return void
     */
    @Override
    public void resetHistory() {
        super.resetHistory();
        sourceCurrentMemento = getLatestMemento();
        isModified = false;
    }

}
