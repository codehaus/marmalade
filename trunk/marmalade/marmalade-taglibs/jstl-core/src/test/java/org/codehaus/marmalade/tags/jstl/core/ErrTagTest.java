
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
/* Created on Apr 13, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author jdcasey
 */
public class ErrTagTest extends TestCase
{
    public void testShouldFailWithMissingValueAndDefaultAttributesAndMissingBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );

        ErrTag tag = new ErrTag( ti );

        try
        {
            tag.execute( new DefaultContext(  ) );
            fail( 
                "should fail because of missing value attribute (default attribute and body are missing also)" );
        }
        catch ( MarmaladeExecutionException e )
        {
            // should snag on missing value attribute when default attribute and body are missing
        }
    }

    public void testShouldSucceedWithSpecifiedValueAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", "message" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );

        ErrTag tag = new ErrTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldSucceedWithSpecifiedBody(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( new DefaultRawAttributes(  ) );
        ti.appendText( "test".toCharArray(  ), 0, "test".length(  ) );

        ErrTag tag = new ErrTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldSucceedWithSpecifiedDefaultAttribute(  )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "default", "message" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );

        ErrTag tag = new ErrTag( ti );

        tag.execute( new DefaultContext(  ) );
    }

    public void testShouldReproduceBodyToErrWriterBeforeEvaluatingValueOrDefaultAttributes(  )
        throws MarmaladeExecutionException
    {
        String message = "test message";
        String message2 = "test message 2";
        String message3 = "test message 3";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", message2 ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "default", message3 ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.appendText( message.toCharArray(  ), 0, message.length(  ) );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        StringWriter err = new StringWriter(  );

        ErrTag tag = new ErrTag( ti );

        DefaultContext context = new DefaultContext(  );

        context.setErrWriter( new PrintWriter( err ) );
        tag.execute( context );

        assertEquals( message, err.getBuffer(  ).toString(  ) );
    }

    public void testShouldReproduceValueAttributeResultToErrWriterBeforeEvaluatingDefaultAttribute(  )
        throws MarmaladeExecutionException
    {
        String message = "test message";
        String message2 = "test message 2";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", message ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "default", message2 ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        StringWriter err = new StringWriter(  );

        ErrTag tag = new ErrTag( ti );

        DefaultContext context = new DefaultContext(  );

        context.setErrWriter( new PrintWriter( err ) );
        tag.execute( context );

        assertEquals( message, err.getBuffer(  ).toString(  ) );
    }

    public void testShouldProperlyEscapeXmlWhenEnabled(  )
        throws MarmaladeExecutionException
    {
        String message = "<test attr=\"message\"/>";
        String escapedMessage = "&lt;test attr=\"message\"/&gt;";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "value", message ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "escapeXml", "true" ) );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        StringWriter err = new StringWriter(  );

        ErrTag tag = new ErrTag( ti );

        DefaultContext context = new DefaultContext(  );

        context.setErrWriter( new PrintWriter( err ) );
        tag.execute( context );

        assertEquals( escapedMessage, err.getBuffer(  ).toString(  ) );
    }
}
