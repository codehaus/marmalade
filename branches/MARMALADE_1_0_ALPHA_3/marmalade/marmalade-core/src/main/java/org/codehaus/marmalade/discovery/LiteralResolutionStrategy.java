/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on May 18, 2004 */
package org.codehaus.marmalade.discovery;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.monitor.log.CommonLogLevels;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;

/**
 * @author jdcasey
 */
public class LiteralResolutionStrategy
    extends AbstractTaglibResolutionStrategy
{
    public LiteralResolutionStrategy()
    {
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib, ClassLoader cloader )
    {
        MarmaladeLog log = getLog();
        
        MarmaladeTagLibrary tlib = null;

        // We need to be more proactive in determining whether the class exists
        // rather than relying on Exceptional behavior.
        URL classUrl = cloader.getResource(taglib.replace('.', '/') + ".class");
        if(classUrl != null)
        {
            try
            {
                Class tlCls = cloader.loadClass( taglib );

                if ( MarmaladeTagLibrary.class.isAssignableFrom( tlCls ) )
                {
                    tlib = (MarmaladeTagLibrary) tlCls.newInstance();
                }
            }

            // Ignore these, and return null.
            catch ( InstantiationException e )
            {
                List entries = Arrays.asList(new Object[] {e, "Proceeding with taglib resolution."});
                log.log(CommonLogLevels.DEBUG, entries);
            }
            catch ( IllegalAccessException e )
            {
                List entries = Arrays.asList(new Object[] {e, "Proceeding with taglib resolution."});
                log.log(CommonLogLevels.DEBUG, entries);
            }
            catch ( ClassNotFoundException e )
            {
                List entries = Arrays.asList(new Object[] {e, "Proceeding with taglib resolution."});
                log.log(CommonLogLevels.DEBUG, entries);
            }
        }

        List entries = Arrays.asList( new Object[] {
            "Returning taglib: ",
            tlib,
            " from literal taglib class strategy for taglib {prefix: ",
            prefix,
            ", taglib: ",
            taglib,
            "} using classloader: ",
            cloader } );

        log.log( CommonLogLevels.DEBUG, entries );

        return tlib;
    }

}