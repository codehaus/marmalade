// TODO Attach license header here.
package org.codehaus.marmalade.msp.finder;

/**
 * @author jdcasey
 *
 * Created on Nov 27, 2004
 */
public class PathTrimInterpreter
    implements PathInterpreter
{
    
    private String trim;

    public void setTrim(String trim)
    {
        this.trim = trim;
    }

    public String interpret( String inputPath )
    {
        String result = inputPath;
        if(result.startsWith(trim))
        {
            result = result.substring(trim.length());
        }
        
        return result;
    }

}
