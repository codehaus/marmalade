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
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.PassThroughExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jdcasey
 */
public class DefaultAttributesTest extends MockObjectTestCase
{
    public void testGetExpressionEvaluator(  )
    {
    }

    public void testShouldReturnNullValueWhenInputAttributesAreNull(  )
        throws ExpressionEvaluationException
    {
        DefaultAttributes attrs = new DefaultAttributes( new PassThroughExpressionEvaluator(  ),
                null );

        assertNull( attrs.getValue( "url", String.class, new DefaultContext(  ) ) );
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
