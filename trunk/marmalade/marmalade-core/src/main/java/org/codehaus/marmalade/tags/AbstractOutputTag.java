/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Apr 11, 2004 */
package org.codehaus.marmalade.tags;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.util.XMLUtils;

/**
 * @author jdcasey
 */
public abstract class AbstractOutputTag extends AbstractMarmaladeTag
{
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String DEFAULT_ATTRIBUTE = "default";
    public static final String ESCAPE_XML_ATTRIBUTE = "escapeXml";

    /**
     */
    protected AbstractOutputTag( MarmaladeTagInfo tagInfo )
    {
        super( tagInfo );
    }

    protected abstract void write( String message,
        MarmaladeExecutionContext context )
        throws MarmaladeExecutionException;

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String value = ( String ) getBody( context, String.class );

        MarmaladeAttributes attributes = getAttributes(  );

        if ( ( value == null ) || ( value.length(  ) < 1 ) )
        {
            value = ( String ) attributes.getValue( VALUE_ATTRIBUTE,
                    String.class, context );
        }

        if ( ( value == null ) || ( value.length(  ) < 1 ) )
        {
            value = ( String ) attributes.getValue( DEFAULT_ATTRIBUTE,
                    String.class, context, "" );
        }

        if ( ( value == null ) || ( value.length(  ) < 1 ) )
        {
            throw new MarmaladeExecutionException( 
                "Message is null. Either specify the value or default "
                + "attribute, or provide a non-null body for this tag." );
        }
        else
        {
            Boolean escapeXml = ( Boolean ) attributes.getValue( ESCAPE_XML_ATTRIBUTE,
                    Boolean.class, context, Boolean.FALSE );

            boolean escape = false;

            if ( escapeXml != null )
            {
                escape = escapeXml.booleanValue(  );
            }

            if ( escape )
            {
                value = XMLUtils.escapeXml( value );
            }

            write( value, context );
        }
    }
}
