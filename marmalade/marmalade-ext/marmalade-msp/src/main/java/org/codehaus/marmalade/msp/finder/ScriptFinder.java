// TODO Attach license header here.
package org.codehaus.marmalade.msp.finder;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jdcasey
 *
 * Created on Nov 26, 2004
 */
public interface ScriptFinder
{
    
    Reader getScript(HttpServletRequest request) throws IOException;

}
