/* Created on Aug 21, 2004 */
package org.codehaus.marmalade.el;

import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class BareBonesExpressionEvaluatorTest
    extends TestCase
{
    public void testShouldEvalLiteralExpressionToBooleanTrueWhenExpectedTypeIsBoolean()
        throws ExpressionEvaluationException
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();
        Object result = el.evaluate( "true", Collections.EMPTY_MAP, Boolean.class );

        assertTrue( "result is not a Boolean", (result instanceof Boolean) );
        assertEquals( "result should be Boolean.TRUE", Boolean.TRUE, result );
    }

    public void testShouldEvalContextValueToBooleanTrueWhenExpectedTypeIsBoolean() throws ExpressionEvaluationException
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        Map ctx = new TreeMap();

        ctx.put( "myVal", "true" );

        Object result = el.evaluate( "${myVal}", ctx, Boolean.class );

        assertTrue( "result is not a Boolean", (result instanceof Boolean) );
        assertEquals( "result should be Boolean.TRUE", Boolean.TRUE, result );
    }

    public void testShouldThrowExceptionWhenExpectedTypeIsInteger()
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        try
        {
            Object result = el.evaluate( "1", Collections.EMPTY_MAP, Integer.class );

            fail( "should fail to coerce to Integer...no coercion in BareBones." );
        }
        catch ( ExpressionEvaluationException e )
        {
            // should snag on bad type...
        }
    }

    public void testShouldEvalContextValueToStringWhenExpectedTypeIsString() throws ExpressionEvaluationException
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        Map ctx = new TreeMap();

        ctx.put( "myVal", "true" );

        Object result = el.evaluate( "${myVal}", ctx, String.class );

        assertTrue( "result is not a String", (result instanceof String) );
        assertEquals( "result should be \"true\"", "true", result );
    }

    public void testShouldEvalContextValueToStringWhenExpectedTypeIsObject() throws ExpressionEvaluationException
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        Map ctx = new TreeMap();

        ctx.put( "myVal", "true" );

        Object result = el.evaluate( "${myVal}", ctx, Object.class );

        assertTrue( "result is not a String", (result instanceof String) );
        assertEquals( "result should be \"true\"", "true", result );
    }

    public void testShouldEvalContextValueToURLWhenExpectedTypeIsURL() throws ExpressionEvaluationException,
        MalformedURLException
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        Map ctx = new TreeMap();

        ctx.put( "myVal", new URL( "http://www.google.com" ) );

        Object result = el.evaluate( "${myVal}", ctx, URL.class );

        assertTrue( "result is not a URL", (result instanceof URL) );
        assertEquals( "result URL is wrong", new URL( "http://www.google.com" ), result );
    }

    public void testShouldEvalContextValueToStringArrayWhenExpectedTypeIsObject() throws ExpressionEvaluationException,
        MalformedURLException
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        Map ctx = new TreeMap();

        String[] strArray = new String[] { "one", "two", "three" };

        ctx.put( "myVal", strArray );

        Object result = el.evaluate( "${myVal}", ctx, Object.class );

        assertEquals( "result array is wrong", strArray, result );
    }

    public void testShouldEvalContextURLValueToStringWhenExpectedTypeIsString() throws ExpressionEvaluationException,
        MalformedURLException
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        Map ctx = new TreeMap();

        ctx.put( "myVal", new URL( "http://www.google.com" ) );

        Object result = el.evaluate( "${myVal}", ctx, String.class );

        assertTrue( "result is not a String", (result instanceof String) );
        assertEquals( "result URL-string is wrong", "http://www.google.com", result );
    }

    public void testShouldThrowExceptionWhenContextValueIsStringAndExpectedTypeIsURL()
    {
        BareBonesExpressionEvaluator el = new BareBonesExpressionEvaluator();

        Map ctx = new TreeMap();

        ctx.put( "myVal", "http://www.google.com" );

        try
        {
            Object result = el.evaluate( "${myVal}", ctx, URL.class );

            fail( "Should snag on attempt to coerce String to URL" );
        }
        catch ( ExpressionEvaluationException e )
        {
            // should snag on coercion attempt.
        }
    }
}