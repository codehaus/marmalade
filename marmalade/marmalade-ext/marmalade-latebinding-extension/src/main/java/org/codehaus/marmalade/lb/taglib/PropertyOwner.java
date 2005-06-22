/* Created on Aug 21, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.model.MarmaladeTag;

/**
 * @author jdcasey
 */
public interface PropertyOwner extends MarmaladeTag
{
    public void addProperty( String propertyName, Object value );
}