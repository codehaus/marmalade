package org.codehaus.marmalade.util;

/**
 * @author jdcasey
 *
 * Created on Feb 4, 2005
 */
public final class StringUtils
{
    
    private StringUtils() {}
    
    public static boolean empty(String test)
    {
        return test != null && test.trim().length() > 0;
    }

}
