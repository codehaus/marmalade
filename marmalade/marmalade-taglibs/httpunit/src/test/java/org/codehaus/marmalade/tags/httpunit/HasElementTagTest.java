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



* Created on Apr 25, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import com.meterware.httpunit.HTMLElement;

import org.codehaus.marmalade.metamodel.DefaultRawAttribute;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.WebResponseTag;
import org.codehaus.tagalog.TagException;
import org.codehaus.tagalog.TagalogParseException;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

/**
 * @author jdcasey
 */
public class HasElementTagTest extends MockObjectTestCase
{
    public void testSuccessWithName(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";

        Mock elementMock = new Mock( HTMLElement.class );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementsWithName" )
                    .with( eq( name ) ).will( returnValue( 
                new HTMLElement[] { ( HTMLElement ) elementMock.proxy(  ) } ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFailWithName(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";

        Mock elementMock = new Mock( HTMLElement.class );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementsWithName" )
                    .with( eq( name ) ).will( returnValue( new HTMLElement[0] ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( 
                "Titles should not match, and an exception should have been thrown." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }

    public void testSuccessWithId(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String id = "1";

        Mock elementMock = new Mock( HTMLElement.class );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementWithID" )
                    .with( eq( id ) ).will( returnValue( 
                ( HTMLElement ) elementMock.proxy(  ) ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withId", id ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFailWithId(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String id = "1";

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementWithID" )
                    .with( eq( id ) ).will( returnValue( null ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withId", id ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "An exception should have been thrown." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }

    public void testSuccessWithName_ClassName(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";
        String className = "testClass";

        Mock elementMock = new Mock( HTMLElement.class );

        elementMock.expects( once(  ) ).method( "getClassName" )
                   .withNoArguments(  ).will( returnValue( className ) );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementsWithName" )
                    .with( eq( name ) ).will( returnValue( 
                new HTMLElement[] { ( HTMLElement ) elementMock.proxy(  ) } ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "withClassName",
                className ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFailWithName_ClassName(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";
        String className = "testClass";

        Mock elementMock = new Mock( HTMLElement.class );

        elementMock.expects( once(  ) ).method( "getClassName" )
                   .withNoArguments(  ).will( returnValue( "testClassNOMATCH" ) );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementsWithName" )
                    .with( eq( name ) ).will( returnValue( 
                new HTMLElement[] { ( HTMLElement ) elementMock.proxy(  ) } ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "withClassName",
                className ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "An exception should have been thrown." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }

    public void testSuccessWithName_Title(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";
        String title = "testTitle";

        Mock elementMock = new Mock( HTMLElement.class );

        elementMock.expects( once(  ) ).method( "getTitle" ).withNoArguments(  )
                   .will( returnValue( title ) );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementsWithName" )
                    .with( eq( name ) ).will( returnValue( 
                new HTMLElement[] { ( HTMLElement ) elementMock.proxy(  ) } ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "withTitle", title ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFailWithName_Title(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";
        String title = "testTitle";

        Mock elementMock = new Mock( HTMLElement.class );

        elementMock.expects( once(  ) ).method( "getTitle" ).withNoArguments(  )
                   .will( returnValue( "testTitleNOMATCH" ) );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementsWithName" )
                    .with( eq( name ) ).will( returnValue( 
                new HTMLElement[] { ( HTMLElement ) elementMock.proxy(  ) } ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "withTitle", title ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "An exception should have been thrown." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }

    public void testSuccessWithId_Name(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";
        String id = "1";

        Mock elementMock = new Mock( HTMLElement.class );

        elementMock.expects( once(  ) ).method( "getName" ).withNoArguments(  )
                   .will( returnValue( name ) );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementWithID" )
                    .with( eq( id ) ).will( returnValue( 
                ( HTMLElement ) elementMock.proxy(  ) ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "withId", id ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        tag.execute( ctx );
    }

    public void testFailWithId_Name(  )
        throws TagException, TagalogParseException, MarmaladeExecutionException
    {
        String name = "h1";
        String id = "1";

        Mock elementMock = new Mock( HTMLElement.class );

        elementMock.expects( once(  ) ).method( "getName" ).withNoArguments(  )
                   .will( returnValue( "h2" ) );

        Mock responseMock = new Mock( TestingWebResponse.class );

        responseMock.expects( once(  ) ).method( "getElementWithID" )
                    .with( eq( id ) ).will( returnValue( 
                ( HTMLElement ) elementMock.proxy(  ) ) );

        Mock parentMock = new Mock( WebResponseTag.class );

        parentMock.expects( once(  ) ).method( "getResponse" ).withNoArguments(  )
                  .will( returnValue( responseMock.proxy(  ) ) );

        DefaultRawAttributes attrs = new DefaultRawAttributes(  );

        attrs.addAttribute( new DefaultRawAttribute( "", "withName", name ) );
        attrs.addAttribute( new DefaultRawAttribute( "", "withId", id ) );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        mti.setAttributes( attrs );
        mti.setElement( "hasElement" );

        HasElementTag tag = new HasElementTag( mti );

        tag.setParent( ( MarmaladeTag ) parentMock.proxy(  ) );

        DefaultContext ctx = new DefaultContext(  );

        try
        {
            tag.execute( ctx );
            fail( "An exception should have been thrown." );
        }
        catch ( HttpAssertionFailedException e )
        {
        }
    }
}