package com.jerryio.protocol_diagram;

import com.jerryio.protocol_diagram.diagram.Diagram;

public class FileSystem {

    public static String mountedFile = null;
    public static Diagram.Memento fileMemento = null;
    public static boolean isModified = false;

    public static boolean isChanged() {
        return isModified || (fileMemento != null && fileMemento != Main.timeline.getLatestMemento());
    }

}
