
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

/* Created on Apr 29, 2004 */
package org.codehaus.marmalade.tags.httpunit.structure;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.IllegalAncestorException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.marmalade.tags.httpunit.TestingWebRequest;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class ParameterTagTest extends MockObjectTestCase
{
    public void testEmbedded_Success_ValueAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        Mock wrtMock = new Mock( WebRequestTag.class );

        wrtMock.expects( once(  ) ).method( "getRequest" ).withNoArguments(  )
               .will( returnValue( wrMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );

        ParameterTag tag = new ParameterTag( mti );

        tag.setParent( ( MarmaladeTag ) wrtMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testEmbedded_Success_ValueBody(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        Mock wrtMock = new Mock( WebRequestTag.class );

        wrtMock.expects( once(  ) ).method( "getRequest" ).withNoArguments(  )
               .will( returnValue( wrMock.proxy(  ) ) );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attributes );
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        ParameterTag tag = new ParameterTag( mti );

        tag.setParent( ( MarmaladeTag ) wrtMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testEmbedded_Failure_NameMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";
        
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        Mock wrtMock = new Mock( WebRequestTag.class );

        wrtMock.expects( once(  ) ).method( "getRequest" ).withNoArguments(  )
               .will( returnValue( wrMock.proxy(  ) ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );
        ParameterTag tag = new ParameterTag( mti );

        tag.setParent( ( MarmaladeTag ) wrtMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have thrown MissingAttributeException for missing name attribute." );
        }
        catch ( MissingAttributeException e )
        {
        }
    }

    public void testSerial_Success_ValueAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();
        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "request", wrMock.proxy(  ) );
        tag.execute( ctx );
    }

    public void testSerial_Failure_NameMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();
        
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "request", wrMock.proxy(  ) );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have thrown MissingAttributeException for missing name attribute." );
        }
        catch ( MissingAttributeException e )
        {
        }
    }

    public void testSerial_Failure_RequestParamMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have thrown IllegalAncestorException for missing request attribute." );
        }
        catch ( IllegalAncestorException e )
        {
        }
    }

    public void testSerial_Failure_RequestObjectMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.VALUE_ATTRIBUTE, "testval" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Should have thrown IllegalAncestorException for missing request object/parent." );
        }
        catch ( IllegalAncestorException e )
        {
        }
    }

    public void testSerial_Success_ValueBody(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( TestingWebRequest.class );

        wrMock.expects( once(  ) ).method( "setParameter" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", ParameterTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", ParameterTag.REQUEST_ATTRIBUTE, "#request" );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.setExpressionEvaluator(new OgnlExpressionEvaluator());
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        ParameterTag tag = new ParameterTag( mti );

        DefaultContext ctx = new DefaultContext(  );

        ctx.setVariable( "request", wrMock.proxy(  ) );
        tag.execute( ctx );
    }
}
