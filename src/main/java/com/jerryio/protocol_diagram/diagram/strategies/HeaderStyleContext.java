package com.jerryio.protocol_diagram.diagram.strategies;

public class HeaderStyleContext {

	private IHeaderStyleStrategy strategy;

	public void setStrategy(IHeaderStyleStrategy strategy) {
		this.strategy = strategy;
	}

	public String execute(int width) {
		return this.strategy.execute(width);
	}

}
