
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
/* Created on May 20, 2004 */
package org.codehaus.marmalade.modelbuilder;

import junit.framework.TestCase;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.ModelBuilderAttribute;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class DefaultRawAttributesTest extends TestCase
{
    public void testShouldIterateOverTwoAttributes(  )
    {
        DefaultRawAttributes ra = new DefaultRawAttributes(  );

        ra.addAttribute( new DefaultRawAttribute( "testNS", "testAttr",
                "testVal" ) );
        ra.addAttribute( new DefaultRawAttribute( "testNS2", "testAttr2",
                "testVal2" ) );

        int counter = 0;

        for ( Iterator it = ra.iterator(  ); it.hasNext(  ); )
        {
            ModelBuilderAttribute attribute = ( ModelBuilderAttribute ) it.next(  );

            counter++;
        }

        assertEquals( 2, counter );
    }

    public void testShouldStoreRetrieveAttributeNamespace(  )
    {
        DefaultRawAttributes ra = new DefaultRawAttributes(  );

        ra.addAttribute( new DefaultRawAttribute( "testNS", "testAttr",
                "testVal" ) );

        assertEquals( "testNS", ra.getNamespace( "testAttr" ) );
    }

    public void testShouldStoreRetrieveAttributeValue(  )
    {
        DefaultRawAttributes ra = new DefaultRawAttributes(  );

        ra.addAttribute( new DefaultRawAttribute( "testNS", "testAttr",
                "testVal" ) );

        assertEquals( "testVal", ra.getValue( "testAttr" ) );
    }
}
