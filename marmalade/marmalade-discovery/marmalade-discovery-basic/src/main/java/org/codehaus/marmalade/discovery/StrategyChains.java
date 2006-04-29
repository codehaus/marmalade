package org.codehaus.marmalade.discovery;

import java.util.Arrays;
import java.util.List;

public class StrategyChains {

	public static final List DEFAULT_STRATEGY_CHAIN = Arrays
			.asList(new TaglibResolutionStrategy[] {
					new LiteralResolutionStrategy(),
					new PrefixedTldResolutionStrategy(),
					new PrefixedDefFileResolutionStrategy(),
					new PassThroughResolutionStrategy() });

	public static final List NO_PASSTHROUGH_STRATEGY_CHAIN = Arrays
			.asList(new TaglibResolutionStrategy[] {
					new LiteralResolutionStrategy(),
					new PrefixedTldResolutionStrategy(),
					new PrefixedDefFileResolutionStrategy() });

	private StrategyChains() {
	}

}
