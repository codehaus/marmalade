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
/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.metamodel.strategy.tld.tags;

import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.tagalog.AbstractTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;

/**
 * @author jdcasey
 */
public class TagclassTag extends AbstractTag
{
    private StringBuffer className = new StringBuffer(  );

    public TagclassTag(  )
    {
    }

    public Object end( String elementName )
        throws TagException, TagalogParseException
    {
        try
        {
            ClassLoader cloader = getClass(  ).getClassLoader(  );
            Class tagClass = cloader.loadClass( className.toString(  ) );

            if ( !MarmaladeTag.class.isAssignableFrom( tagClass ) )
            {
                throw new TagException( className.toString(  )
                    + " is not an implementation of MarmaladeTag." );
            }

            TagTag parent = ( TagTag ) getParent(  );

            parent.setTagClass( tagClass );
        }
        catch ( ClassNotFoundException e )
        {
            throw new TagException( "Invalid tag class: "
                + className.toString(  ), e );
        }

        return null;
    }

    public void text( char[] characters, int start, int length )
        throws TagException, TagalogParseException
    {
        String add = String.valueOf( characters, start, length );

        className.append( add.trim(  ) );
    }

    public boolean recycle(  )
    {
        className.setLength( 0 );

        return true;
    }
}
