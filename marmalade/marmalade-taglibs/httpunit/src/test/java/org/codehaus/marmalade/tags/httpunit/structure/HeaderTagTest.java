
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

import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.tagalog.Attributes;
import org.codehaus.tagalog.Tag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class HeaderTagTest extends MockObjectTestCase
{
    public void testSuccess_ValueAttribute(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        Mock wrMock = mock( HeaderParent.class );

        wrMock.expects( once(  ) ).method( "setHeader" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", HeaderTag.NAME_ATTRIBUTE, "testkey" );
        attributes.addAttribute( "", HeaderTag.VALUE_ATTRIBUTE, "testval" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);

        HeaderTag tag = new HeaderTag( mti );

        tag.setParent( ( MarmaladeTag ) wrMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testSuccess_ValueBody(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( HeaderParent.class );

        wrMock.expects( once(  ) ).method( "getExpressionEvaluator" )
              .withNoArguments(  ).will( returnValue( null ) );

        wrMock.expects( once(  ) ).method( "setHeader" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes();

        attributes.addAttribute( "", HeaderTag.NAME_ATTRIBUTE, "testkey" );

        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.setAttributes(attributes);
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        HeaderTag tag = new HeaderTag( mti );

        tag.setParent( ( MarmaladeTag ) wrMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFailure_NameMissing(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String body = "testval";

        Mock wrMock = new Mock( HeaderParent.class );

        wrMock.expects( once(  ) ).method( "getExpressionEvaluator" )
              .withNoArguments(  ).will( returnValue( null ) );

        wrMock.expects( once(  ) ).method( "setHeader" )
              .with( eq( "testkey" ), eq( "testval" ) ).isVoid(  );
        
        MarmaladeTagInfo mti = new MarmaladeTagInfo();
        mti.appendText( body.toCharArray(  ), 0, body.length(  ) );

        HeaderTag tag = new HeaderTag( mti );

        tag.setParent( ( MarmaladeTag ) wrMock.proxy(  ) );

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
}
