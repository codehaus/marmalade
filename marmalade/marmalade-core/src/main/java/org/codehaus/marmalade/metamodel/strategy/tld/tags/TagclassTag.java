
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
