// TODO Attach license header here.
package org.codehaus.marmalade.util;

import java.util.Iterator;
import java.util.List;

/**
 * @author jdcasey
 *
 * Created on Jan 4, 2005
 */
public final class RegexSupport
{
    
    private static final String PATHLIKE_PATTERN = "([^/]*\\/.*)+";

    private RegexSupport()
    {
    }
    
    public static String buildUberPathPattern(List patterns)
    {
        StringBuffer buffer = new StringBuffer();
        
        for ( Iterator it = patterns.iterator(); it.hasNext(); )
        {
            String pattern = (String) it.next();
            
            if(isPathLikePattern(pattern))
            {
                pattern = convertPathLikeToPathRegex(pattern);
            }
            
            buffer.append('(').append(pattern).append(')');
            
            if(it.hasNext())
            {
                buffer.append(" | ");
            }
        }
        
        return buffer.toString();
    }

    public static String buildUberClassPattern(List patterns)
    {
        StringBuffer buffer = new StringBuffer();
        
        for ( Iterator it = patterns.iterator(); it.hasNext(); )
        {
            String pattern = (String) it.next();
            
            if(isPathLikePattern(pattern))
            {
                pattern = convertPathLikeToClassRegex(pattern);
            }
            
            buffer.append('(').append(pattern).append(')');
            
            if(it.hasNext())
            {
                buffer.append(" | ");
            }
        }
        
        return buffer.toString();
    }

    public static String convertPathLikeToPathRegex( String untrimmedPattern )
    {
        String pattern = untrimmedPattern.trim();
        
        StringBuffer buffer = new StringBuffer();
        
        char curr = '\0';
        char next = '\0';
        for(int i=0; i<pattern.length(); i++)
        {
            curr = pattern.charAt(i);
            if(i+1 < pattern.length())
            {
                next = pattern.charAt(i+1);
            }
            else
            {
                next = '\0';
            }
            
            if(curr == '*')
            {
                String repeatPattern = "[^/]";
                if(next == '*')
                {
                    repeatPattern = ".";
                }
                
                buffer.append(repeatPattern).append('*');
            }
            else if(curr == '?')
            {
                buffer.append(".?");
            }
            else
            {
                buffer.append(curr);
            }
        }
        
        return buffer.toString();
    }

    public static String convertPathLikeToClassRegex( String untrimmedPattern )
    {
        String pattern = untrimmedPattern.trim();
        
        StringBuffer buffer = new StringBuffer();
        
        char curr = '\0';
        char next = '\0';
        for(int i=0; i<pattern.length(); i++)
        {
            curr = pattern.charAt(i);
            if(i+1 < pattern.length())
            {
                next = pattern.charAt(i+1);
            }
            else
            {
                next = '\0';
            }
            
            if(curr == '*')
            {
                String repeatPattern = "[^.]";
                if(next == '*')
                {
                    repeatPattern = ".";
                }
                
                buffer.append(repeatPattern).append('*');
            }
            else if(curr == '?')
            {
                buffer.append(".?");
            }
            else
            {
                buffer.append(curr);
            }
        }
        
        return buffer.toString();
    }

    public static boolean isPathLikePattern( String pattern )
    {
        return pattern != null && pattern.trim().length() > 0 && pattern.matches(PATHLIKE_PATTERN);
    }

}
