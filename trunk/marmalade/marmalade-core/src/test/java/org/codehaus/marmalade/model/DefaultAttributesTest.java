
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
package org.codehaus.marmalade.model;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class DefaultAttributesTest extends MockObjectTestCase
{
    public void testGetExpressionEvaluator(  )
    {
    }
    
    public void testShouldReturnNullValueWhenInputAttributesAreNull() throws ExpressionEvaluationException {
        DefaultAttributes attrs = new DefaultAttributes(new PassThroughExpressionEvaluator(), null);
        assertNull(attrs.getValue("url", String.class, new DefaultContext()));
    }

    /*
     * Class to test for Object getValue(String, MarmaladeExecutionContext)
     */
    public void testGetValue_String_Context(  )
        throws ExpressionEvaluationException
    {
        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( once(  ) ).method( "evaluate" )
              .with( eq( "value" ), isA( Map.class ), isA( Class.class ) ).will( returnValue( 
                "value" ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( null, "testKey",
                "value" ) );

        DefaultAttributes attrs = new DefaultAttributes( ( ExpressionEvaluator ) elMock
                .proxy(  ), attributes );

        Mock ctxMock = mock( MarmaladeExecutionContext.class );

        ctxMock.expects( once(  ) ).method( "unmodifiableVariableMap" )
               .withNoArguments(  ).will( returnValue( new HashMap(  ) ) );

        Object result = attrs.getValue( "testKey",
                ( MarmaladeExecutionContext ) ctxMock.proxy(  ) );

        assertEquals( "Wrong attribute value.", "value", result );
    }

    /*
     * Class to test for Object getValue(String, MarmaladeExecutionContext, Object)
     */
    public void testGetValue_String_Context_Object(  )
        throws ExpressionEvaluationException
    {
        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( once(  ) ).method( "evaluate" )
              .with( eq( "value" ), isA( Map.class ), isA( Class.class ) ).will( returnValue( 
                "value" ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( null, "testKey",
                "value" ) );

        DefaultAttributes attrs = new DefaultAttributes( ( ExpressionEvaluator ) elMock
                .proxy(  ), attributes );

        Mock ctxMock = mock( MarmaladeExecutionContext.class );

        ctxMock.expects( once(  ) ).method( "unmodifiableVariableMap" )
               .withNoArguments(  ).will( returnValue( new HashMap(  ) ) );

        Object result = attrs.getValue( "testKey",
                ( MarmaladeExecutionContext ) ctxMock.proxy(  ), "value" );

        assertEquals( "Wrong attribute value.", "value", result );
    }

    public void testGetValue_String_Class_MarmaladeExecutionContext_Object(  )
        throws ExpressionEvaluationException
    {
        Integer testVal = new Integer( 10 );

        Mock elMock = mock( ExpressionEvaluator.class );

        elMock.expects( once(  ) ).method( "evaluate" )
              .with( eq( "value" ), isA( Map.class ), isA( Class.class ) ).will( returnValue( 
                null ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( new DefaultRawAttribute( null, "testKey",
                "value" ) );

        DefaultAttributes attrs = new DefaultAttributes( ( ExpressionEvaluator ) elMock
                .proxy(  ), attributes );

        Mock ctxMock = mock( MarmaladeExecutionContext.class );

        ctxMock.expects( once(  ) ).method( "unmodifiableVariableMap" )
               .withNoArguments(  ).will( returnValue( new HashMap(  ) ) );

        Object result = attrs.getValue( "testKey", Integer.class,
                ( MarmaladeExecutionContext ) ctxMock.proxy(  ),
                new Integer( 2 ) );

        assertFalse( "Wrong attribute value.", testVal.equals( result ) );
        assertEquals( "Should equal default value", 2,
            ( ( Integer ) result ).intValue(  ) );
    }
}
