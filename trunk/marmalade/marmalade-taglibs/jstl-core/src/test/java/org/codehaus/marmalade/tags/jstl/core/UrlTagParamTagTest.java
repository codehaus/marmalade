
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
