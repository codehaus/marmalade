package org.codehaus.marmalade.tags.test;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

public class TestSetTag extends AbstractMarmaladeTag {

	protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException {
		
		String key = (String) requireTagAttribute("key", context);
		String value = (String) requireTagAttribute("value", context);
		
		Boolean extern = (Boolean) getAttributes().getValue("extern", Boolean.class, context);
		
		if ( Boolean.TRUE == extern )
		{
			context.setVariable(key, value, extern.booleanValue());
		}
		else
		{
			context.setVariable(key, value);
		}
		
	}

}
