/* Created on Aug 11, 2004 */
package org.codehaus.marmalade.metamodel;

import org.codehaus.marmalade.AbstractTagRelatedException;

/**
 * @author jdcasey
 */
public class ModelBuilderException
    extends AbstractTagRelatedException
{
    public ModelBuilderException(MarmaladeTagInfo tagInfo)
    {
        super(tagInfo);
    }

    public ModelBuilderException( MarmaladeTagInfo tagInfo, String message )
    {
        super( tagInfo, message );
    }

    public ModelBuilderException( MarmaladeTagInfo tagInfo, Throwable cause )
    {
        super( tagInfo, cause );
    }

    public ModelBuilderException( MarmaladeTagInfo tagInfo, String message, Throwable cause )
    {
        super( tagInfo, message, cause );
    }

}