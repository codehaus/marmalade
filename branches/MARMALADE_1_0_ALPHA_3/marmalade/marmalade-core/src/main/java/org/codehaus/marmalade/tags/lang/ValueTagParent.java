package org.codehaus.marmalade.tags.lang;

import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 *
 * Created on Feb 4, 2005
 */
public interface ValueTagParent
{

    void setValue(Object value) throws MarmaladeExecutionException;
}
