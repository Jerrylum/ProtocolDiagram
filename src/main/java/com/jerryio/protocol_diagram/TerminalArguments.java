package com.jerryio.protocol_diagram;

import com.beust.jcommander.Parameter;

public class TerminalArguments {

    @Parameter(names = { "--print", "-p" }, description = "Print the diagram to console and exit")
    public boolean print = false;

    @Parameter(names = { "--single-line", "-s" }, description = "Input the protocol in a single line")
    public String singleLine = null;

    @Parameter(names = { "--help", "-h" }, help = true, description = "Show this help message")
    public boolean help = false;
}
