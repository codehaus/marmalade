/* Created on Aug 21, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.model.MarmaladeTag;

/**
 * @author jdcasey
 */
public interface ConstructorArgOwner extends MarmaladeTag
{
    public void addConstructorArg( Object arg );
}