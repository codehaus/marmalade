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
/* Created on Apr 14, 2004 */
package org.codehaus.marmalade.tags.jstl.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.net.URL;

/**
 * @author jdcasey
 */
public class UrlTagParamTagTest extends TestCase
{
    private void _testUrlTag( String url, ParamTag[] params, String checkUrl )
        throws MarmaladeExecutionException
    {
        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "var", "url" );
        attrs.addAttribute( "", "url", url );

        OgnlExpressionEvaluator el = new OgnlExpressionEvaluator(  );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( el );

        UrlTag tag = new UrlTag( ti );

        if ( params != null )
        {
            for ( int i = 0; i < params.length; i++ )
            {
                ParamTag paramTag = params[i];

                tag.addChild( paramTag );
                paramTag.setParent( tag );
            }
        }

        DefaultContext ctx = new DefaultContext(  );

        assertNull( "url variable should be null", ctx.getVariable( "url", el ) );
        tag.execute( ctx );

        URL test = ( URL ) ctx.getVariable( "url", el );

        System.out.println( "Original URL: " + url + "\tResulting URL: "
            + test.toExternalForm(  ) );

        assertEquals( checkUrl, test.toExternalForm(  ) );
    }

    public void testShouldConstructURLObjectFromStringUrlWithNoParamsWithoutParamChildren(  )
        throws MarmaladeExecutionException
    {
        String url = "http://www.google.com";

        _testUrlTag( url, null, url );
    }

    public void testShouldConstructURLObjectFromStringUrlWithNoParamsWithOneParamChild(  )
        throws MarmaladeExecutionException
    {
        String url = "http://www.google.com";
        String check = url + "?test=value";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "name", "test" );
        attrs.addAttribute( "", "value", "value" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ParamTag param = new ParamTag( ti );

        _testUrlTag( url, new ParamTag[] { param }, check );
    }

    public void testShouldConstructURLObjectFromStringUrlWithNoParamsWithOneParamChildContainingSpaces(  )
        throws MarmaladeExecutionException
    {
        String url = "http://www.google.com";
        String check = url + "?test=test+value";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "name", "test" );
        attrs.addAttribute( "", "value", "test value" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ParamTag param = new ParamTag( ti );

        _testUrlTag( url, new ParamTag[] { param }, check );
    }

    public void testShouldConstructURLObjectFromStringUrlWithOneParamAndOneParamChild(  )
        throws MarmaladeExecutionException
    {
        String url = "http://www.google.com?param=pValue";
        String check = url + "&test=value";

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( "", "name", "test" );
        attrs.addAttribute( "", "value", "value" );

        MarmaladeTagInfo ti = new MarmaladeTagInfo(  );

        ti.setAttributes( attrs );
        ti.setExpressionEvaluator( new OgnlExpressionEvaluator(  ) );

        ParamTag param = new ParamTag( ti );

        _testUrlTag( url, new ParamTag[] { param }, check );
    }
}
