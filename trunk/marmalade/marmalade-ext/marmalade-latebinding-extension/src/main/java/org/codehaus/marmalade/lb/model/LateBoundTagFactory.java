/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.model;

import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;

/**
 * @author jdcasey
 */
public interface LateBoundTagFactory
{
    public MarmaladeTag newTag() throws TagInstantiationException;
}