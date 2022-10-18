package com.jerryio.protocol_diagram;

import com.jerryio.protocol_diagram.diagram.Diagram;
import com.jerryio.protocol_diagram.diagram.Timeline;

public final class MainTimeline extends Timeline {
    
    public MainTimeline() {
        super(null);
    }

    public Diagram getDiagram() {
        return Main.diagram;
    }
    
}
