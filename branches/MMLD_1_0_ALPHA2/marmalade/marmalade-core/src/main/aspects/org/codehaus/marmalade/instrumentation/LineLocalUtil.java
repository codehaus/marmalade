/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;

/**
 * @author jdcasey
 */
public class LineLocalUtil
{
    private LineLocalUtil()
    {
    }

    public static String formatForSingleLine( MarmaladeTagInfo tagInfo )
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append( tagInfo.getSourceFile() ).append( "[" ).append( tagInfo.getSourceLine() ).append( "]" );

        String result = buffer.toString();

        return result;
    }
}