// TODO Attach license header here.
package org.codehaus.marmalade.util;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;

/**
 * @author jdcasey
 *
 * Created on Dec 2, 2004
 */
public final class TagRelatedProblemSupport
{

    private MarmaladeTagInfo tagInfo;
    private boolean suppressVerboseStackTraceOutput = false;

    public TagRelatedProblemSupport(MarmaladeTagInfo tagInfo)
    {
        this.tagInfo = tagInfo;
    }
    
    public boolean suppressVerboseStackTraceOutput()
    {
        return suppressVerboseStackTraceOutput;
    }
    
    public void setSuppressVerboseStackTraceOutput(boolean suppressVerboseStackTraceOutput)
    {
        this.suppressVerboseStackTraceOutput = suppressVerboseStackTraceOutput;
    }
    
    public MarmaladeTagInfo getOffendingTagInfo()
    {
        return tagInfo;
    }
    
    public String getSourceFile()
    {
        return tagInfo.getSourceFile();
    }
    
    public int getSourceLine()
    {
        return tagInfo.getSourceLine();
    }
    
    public void printStackTrace(Exception owningException)
    {
        printStackTrace(owningException, System.err);
    }

    public void printStackTrace( Exception owningException, PrintStream err )
    {
        err.println(getFormattedProblemStatistics());
        owningException.printStackTrace(err);
    }

    public void printStackTrace( Exception owningException, PrintWriter err )
    {
        err.println(getFormattedProblemStatistics());
        owningException.printStackTrace(err);
    }

    private String getFormattedProblemStatistics()
    {
        StringBuffer buffer = new StringBuffer();
        
        if(!suppressVerboseStackTraceOutput)
        {
            buffer.append("There was a tag error in Marmalade! Here are the vital statistics:")
                  .append("\n  o Element: ").append(tagInfo.getElement())
                  .append("\n  o File: ").append(tagInfo.getSourceFile())
                  .append("\n  o Line: ").append(tagInfo.getSourceLine());
        }
        
        return buffer.toString();
    }
    
}
