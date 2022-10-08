package com.jerryio.protocol_diagram.diagram.strategies;

import java.util.Map;

public class NoneHeaderStyleStrategy implements IHeaderStyleStrategy {

	public NoneHeaderStyleStrategy(Map<Class, Object> dependencies) {}

	@Override
	public String execute(int width) { return ""; }
	
}
