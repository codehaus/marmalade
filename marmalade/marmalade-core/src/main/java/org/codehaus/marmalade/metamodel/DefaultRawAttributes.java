
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
/* Created on Apr 10, 2004 */
package org.codehaus.marmalade.metamodel;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class DefaultRawAttributes implements ModelBuilderAttributes
{
    private Map parsedAttributes = new TreeMap(  );

    public Iterator iterator(  )
    {
        return parsedAttributes.values(  ).iterator(  );
    }

    public String getNamespace( String name )
    {
        ModelBuilderAttribute attr = ( ModelBuilderAttribute ) parsedAttributes
            .get( name );
        String ns = attr.getNamespace(  );

        return ns;
    }

    public String getValue( String name )
    {
        String value = null;

        ModelBuilderAttribute attr = ( ModelBuilderAttribute ) parsedAttributes
            .get( name );

        if ( attr != null )
        {
            value = attr.getValue(  );
        }

        return value;
    }

    public String getValue( String namespace, String name )
    {
        String value = null;

        ModelBuilderAttribute attr = ( ModelBuilderAttribute ) parsedAttributes
            .get( name );

        if ( ( attr != null ) && ( namespace != null )
            && namespace.equals( attr.getNamespace(  ) ) )
        {
            value = attr.getValue(  );
        }

        return value;
    }

    public void addAttribute( ModelBuilderAttribute attribute )
    {
        this.parsedAttributes.put( attribute.getName(  ), attribute );
    }

    public void addAttribute( String namespace, String name, String value )
    {
        this.parsedAttributes.put( name,
            new DefaultRawAttribute( namespace, name, value ) );
    }
}
