/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.taglib;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.lb.model.LateBoundTagFactory;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.MarmaladeAttributes;
import org.codehaus.marmalade.parsing.ParserHint;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.MissingAttributeException;
import org.codehaus.marmalade.runtime.TagExecutionException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author jdcasey
 */
public class LooseMarmaladeTagTagTest
    extends MockObjectTestCase
{
    
    public void testShouldConstructWithNoParams() {
        LooseMarmaladeTagTag tag = new LooseMarmaladeTagTag();
    }
    
    public void testShouldRequireNameAttribute() throws MarmaladeExecutionException {
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(DefinitionTagConstants.TAG_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
        
        LooseMarmaladeTagTag tag = new LooseMarmaladeTagTag();
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        try
        {
            tag.execute(new DefaultContext());
            fail("should fail because of name attribute");
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing name attribute
        }
    }

    public void testShouldRequireTagClassAttribute() throws MarmaladeExecutionException {
        Mock attrsMock = mock(MarmaladeAttributes.class);
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(DefinitionTagConstants.TAG_NAME_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue("test"));
        attrsMock.expects(atLeastOnce()).method("getValue").with(eq(LooseMarmaladeTagTag.TAG_CLASS_ATTRIBUTE), eq(String.class), ANYTHING).will(returnValue(null));
        
        LooseMarmaladeTagTag tag = new LooseMarmaladeTagTag();
        
        MarmaladeAttributes attrs = (MarmaladeAttributes)attrsMock.proxy();
        tag.setAttributes(attrs);
        tag.setExpressionEvaluator(new BareBonesExpressionEvaluator());
        tag.setTagInfo(new MarmaladeTagInfo());
        
        try
        {
            tag.execute(new DefaultContext());
            fail("should fail because of tagClass attribute");
        }
        catch ( MissingAttributeException e )
        {
            // should snag on missing tagClass attribute
        }
    }

    public void testShouldAddLooseTagToLibrary() throws MarmaladeExecutionException {
        try
        {
            Mock attrsMock = mock( MarmaladeAttributes.class );
            attrsMock.expects( atLeastOnce() ).method( "getValue" ).with(
                eq( DefinitionTagConstants.TAG_NAME_ATTRIBUTE ), eq( String.class ), ANYTHING ).will(
                returnValue( "test" ) );
            attrsMock.expects( atLeastOnce() ).method( "getValue" ).with(
                eq( LooseMarmaladeTagTag.TAG_CLASS_ATTRIBUTE ), eq( String.class ), ANYTHING ).will(
                returnValue( "org.codehaus.marmalade.lb.tags.LooseTestTag" ) );

            LooseMarmaladeTagTag tag = new LooseMarmaladeTagTag();

            MarmaladeAttributes attrs = (MarmaladeAttributes) attrsMock.proxy();
            tag.setAttributes( attrs );
            tag.setExpressionEvaluator( new BareBonesExpressionEvaluator() );
            tag.setTagInfo( new MarmaladeTagInfo() );

            Mock parentMock = mock( LateBoundTagLibraryOwner.class );
            parentMock.expects( once() ).method( "registerTag" ).with( isA( String.class ), isA( ParserHint.class ),
                isA( LateBoundTagFactory.class ) ).isVoid();

            LateBoundTagLibraryOwner parent = (LateBoundTagLibraryOwner) parentMock.proxy();
            tag.setParent( parent );

            tag.execute( new DefaultContext() );
        }
        catch ( Throwable t )
        {
            t.printStackTrace();
        }
    }

    public void testShouldRejectMissingTagClass() throws MarmaladeExecutionException {
        Mock attrsMock = mock( MarmaladeAttributes.class );
        attrsMock.expects( atLeastOnce() ).method( "getValue" ).with(
            eq( DefinitionTagConstants.TAG_NAME_ATTRIBUTE ), eq( String.class ), ANYTHING ).will(
            returnValue( "test" ) );
        attrsMock.expects( atLeastOnce() ).method( "getValue" ).with(
            eq( LooseMarmaladeTagTag.TAG_CLASS_ATTRIBUTE ), eq( String.class ), ANYTHING ).will(
            returnValue( "org.codehaus.marmalade.lb.tags.TestTag" ) );

        LooseMarmaladeTagTag tag = new LooseMarmaladeTagTag();

        MarmaladeAttributes attrs = (MarmaladeAttributes) attrsMock.proxy();
        tag.setAttributes( attrs );
        tag.setExpressionEvaluator( new BareBonesExpressionEvaluator() );
        tag.setTagInfo( new MarmaladeTagInfo() );

        Mock parentMock = mock( LateBoundTagLibraryOwner.class );

        LateBoundTagLibraryOwner parent = (LateBoundTagLibraryOwner) parentMock.proxy();
        tag.setParent( parent );

        try
        {
            tag.execute( new DefaultContext() );
        }
        catch ( TagExecutionException e )
        {
            e.printStackTrace();
        }
    }

}
