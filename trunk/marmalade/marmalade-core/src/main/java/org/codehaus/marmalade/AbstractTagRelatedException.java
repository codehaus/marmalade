// TODO Attach license header here.
package org.codehaus.marmalade;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.util.TagRelatedProblemSupport;

/**
 * @author jdcasey Created on Dec 2, 2004
 */
public abstract class AbstractTagRelatedException
    extends MarmaladeException
    implements TagRelatedProblem
{

    private TagRelatedProblemSupport tagRelatedSupport;

    protected AbstractTagRelatedException( MarmaladeTagInfo tagInfo )
    {
        this.tagRelatedSupport = new TagRelatedProblemSupport( tagInfo );
    }

    protected AbstractTagRelatedException( MarmaladeTagInfo tagInfo, String message )
    {
        super( message );
        this.tagRelatedSupport = new TagRelatedProblemSupport( tagInfo );
    }

    protected AbstractTagRelatedException( MarmaladeTagInfo tagInfo, Throwable cause )
    {
        super( cause );
        this.tagRelatedSupport = new TagRelatedProblemSupport( tagInfo );
    }

    protected AbstractTagRelatedException( MarmaladeTagInfo tagInfo, String message, Throwable cause )
    {
        super( message, cause );
        this.tagRelatedSupport = new TagRelatedProblemSupport( tagInfo );
    }

    public MarmaladeTagInfo getOffendingTagInfo()
    {
        return tagRelatedSupport.getOffendingTagInfo();
    }

    public String getSourceFile()
    {
        return tagRelatedSupport.getSourceFile();
    }

    public int getSourceLine()
    {
        return tagRelatedSupport.getSourceLine();
    }

    public void printStackTrace()
    {
        tagRelatedSupport.printStackTrace( this );
    }

    public void printStackTrace( PrintStream s )
    {
        tagRelatedSupport.printStackTrace( this, s );
    }

    public void printStackTrace( PrintWriter s )
    {
        tagRelatedSupport.printStackTrace( this, s );
    }
}