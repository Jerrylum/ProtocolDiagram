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
     * 
     * @param diagram
     */
    public void setDiagram(Diagram diagram) {
        Main.diagram = diagram;
    }

    /**
     * a method that eliminates all previous diagram-related logic, and re-creates a
     * new diagram
     */
    public void newDiagram() {
        setDiagram(new Diagram());
        sourceFilePath = null;
        resetHistory();
    }

    /**
     * a method that checks whether the diagram is modified, returns true if the flag
     * `isModified` is true or the file is not saved
     * 
     * @return boolean
     */
    public boolean isModified() {
        return isModified || (sourceCurrentMemento != getLatestMemento());
    }

    /**
     * a method that sets the flag `isModified` to the given value., note that
     * setting the flag to false does not mean the diagram is not modified
     * 
     * @see #isModified()
     * 
     * @param isModified
     */
    public void setModified(boolean isModified) {
        this.isModified = isModified;
    }

    /**
     * a getter function that retrieves the source file path
     * 
     * @return String
     */
    public String getSourceFilePath() {
        return sourceFilePath;
    }

    /**
     * a method that creates a diagram from a JSON file, eliminates all history and
     * renew the source path
     * 
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
     * a method that creates a JSON file from the current diagram, and set the flag
     * `isModified` to false
     * 
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
     */
    @Override
    public void resetHistory() {
        super.resetHistory();
        sourceCurrentMemento = getLatestMemento();
        isModified = false;
    }

}
