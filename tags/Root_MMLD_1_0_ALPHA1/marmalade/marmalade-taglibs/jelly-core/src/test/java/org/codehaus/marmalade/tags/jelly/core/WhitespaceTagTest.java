/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.ognl.OgnlExpressionEvaluator;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.DefaultAttributes;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.jelly.AbstractJellyMarmaladeTag;

/**
 * @author jdcasey
 */
public class WhitespaceTagTest extends TestCase
{
    public void testCheckControlPreserveWhitespace(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        String testBody = "This is a \ntest.";

        WhitespaceCheckerTag tag = new WhitespaceCheckerTag( true );

        tag.setTagInfo( mti );
        tag.appendBodyText( testBody );

        tag.execute( new DefaultContext(  ) );

        assertEquals( "Whitespace should be preserved by default.", testBody,
            tag.getMyBody(  ) );
    }

    public void testCheckControlTrimWhitespace(  )
        throws MarmaladeExecutionException
    {
        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        String testBody = "This is a \ntest.";

        WhitespaceCheckerTag tag = new WhitespaceCheckerTag( false );

        tag.setTagInfo( mti );
        tag.setAttributes( new DefaultAttributes(  ) );
        tag.appendBodyText( testBody );

        tag.execute( new DefaultContext(  ) );

        String checkBody = "This is a test.";

        assertEquals( "Whitespace should not be preserved when trim attribute is specified.",
            checkBody, tag.getMyBody(  ) );
    }

    public void testShouldEnforceWhitespacePreservation(  )
        throws MarmaladeExecutionException
    {
        WhitespaceTag tag = new WhitespaceTag(  );

        DefaultRawAttributes attributes = new DefaultRawAttributes(  );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        String testBody = "This is a \ntest.";

        WhitespaceCheckerTag child = new WhitespaceCheckerTag( false );

        child.setTagInfo( mti );
        child.setAttributes( new DefaultAttributes(  ) );
        child.appendBodyText( testBody );

        child.setParent( tag );
        tag.addChild( child );

        tag.execute( new DefaultContext(  ) );

        assertEquals( testBody, child.getMyBody(  ) );
    }

    public void testShouldEnforceWhitespacePreservationWhenChildSpecifiesTrim(  )
        throws MarmaladeExecutionException
    {
        WhitespaceTag tag = new WhitespaceTag(  );

        MarmaladeTagInfo mti = new MarmaladeTagInfo(  );

        String testBody = "This is a \ntest.";

        WhitespaceCheckerTag child = new WhitespaceCheckerTag( true );

        child.setTagInfo( mti );
        child.setAttributes( new DefaultAttributes(  ) );
        child.appendBodyText( testBody );

        child.setParent( tag );
        tag.addChild( child );

        tag.execute( new DefaultContext(  ) );

        assertEquals( testBody, child.getMyBody(  ) );
    }
}
