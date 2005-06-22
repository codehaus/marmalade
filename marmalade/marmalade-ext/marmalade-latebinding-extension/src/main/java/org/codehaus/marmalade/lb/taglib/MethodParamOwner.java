/* Created on Aug 21, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.model.MarmaladeTag;

/**
 * @author jdcasey
 */
public interface MethodParamOwner extends MarmaladeTag
{
    public void addMethodParameter( Object value );
}