package org.codehaus.marmalade.monitor.log;


/**
 * @author jdcasey
 */
public class DefaultLog extends AbstractLog
{

    protected boolean enabled( String level )
    {
        // this thing is moronically simple...
        return true;
    }

    protected void doLog( String level, String content )
    {
        System.out.println("[" + level + "] " + content);
    }

}
