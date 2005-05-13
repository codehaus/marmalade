/* Created on Aug 20, 2004 */
package org.codehaus.marmalade.lb.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;

/**
 * @author jdcasey
 */
public class LateBoundTagPropertyException
    extends TagInstantiationException
{
    private final Class beanClass;

    private final String property;

    private final Object value;

    public LateBoundTagPropertyException( MarmaladeTagInfo tagInfo, Class beanClass, String property, Object value,
        ExpressionEvaluationException e )
    {
        super( tagInfo, "late-bound tag property resolution failed", e );
        this.beanClass = beanClass;
        this.property = property;
        this.value = value;
    }

    public Class getBeanClass()
    {
        return beanClass;
    }

    public String getProperty()
    {
        return property;
    }

    public Object getValue()
    {
        return value;
    }
}