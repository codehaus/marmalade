/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.Tag;
import org.apache.commons.jelly.TagLibrary;
import org.apache.commons.jelly.XMLOutput;
import org.codehaus.marmalade.compat.jelly.runtime.MarmaladeCompatibleJellyContext;
import org.codehaus.marmalade.compat.jelly.util.SAXAttributesWrapper;
import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;
import org.codehaus.marmalade.runtime.ConfigurationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class JellyCompatMarmaladeTag extends AbstractMarmaladeTag
{
    private TagLibrary tagLibrary;

    public JellyCompatMarmaladeTag( MarmaladeTagInfo mti, TagLibrary tagLibrary )
    {
        super( mti );
        this.tagLibrary = tagLibrary;
    }

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        JellyContext ctx = new MarmaladeCompatibleJellyContext( context,
                getExpressionEvaluator(  ) );

        MarmaladeTagInfo mti = getTagInfo(  );
        SAXAttributesWrapper attrs = new SAXAttributesWrapper( getAttributes(  ) );

        try
        {
            Tag jellyTag = tagLibrary.createTag( mti.getElement(  ), attrs );
            XMLOutput output = XMLOutput.createXMLOutput( context.getOutWriter(  ) );

            jellyTag.doTag( output );
        }
        catch ( JellyException e )
        {
            throw new MarmaladeExecutionException( e );
        }
    }
}
