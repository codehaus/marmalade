package org.codehaus.marmalade.tags.test;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;

public class TestTagLibrary extends AbstractMarmaladeTagLibrary {

	public MarmaladeTag createTag(MarmaladeTagInfo tagInfo) throws TagInstantiationException {
		return new TestSetTag();
	}

	
}
