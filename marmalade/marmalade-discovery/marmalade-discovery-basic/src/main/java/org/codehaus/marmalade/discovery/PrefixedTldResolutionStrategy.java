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

import org.codehaus.marmalade.discovery.tld.TldParser;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.monitor.log.CommonLogLevels;
import org.codehaus.marmalade.monitor.log.DefaultLog;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * @author jdcasey
 */
public class PrefixedTldResolutionStrategy
    implements TaglibResolutionStrategy
{
    private MarmaladeLog log;

    public PrefixedTldResolutionStrategy()
    {
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib, ClassLoader cloader )
    {
        synchronized ( this )
        {
            if(log == null)
            {
                log = new DefaultLog();
            }
        }
        
        MarmaladeTagLibrary tlib = null;

        InputStream tldStream = null;

        try
        {
            String tldFile = "META-INF/" + prefix + "/" + taglib + ".tld";

            tldStream = cloader.getResourceAsStream( tldFile );

            if ( tldStream != null )
            {
                TldParser parser = new TldParser();

                try
                {
                    tlib = parser.parse( new InputStreamReader( tldStream, "UTF-8" ) );
                }

                // Intercept and ignore...return null.
                catch ( UnsupportedEncodingException e )
                {
                    List entries = Arrays.asList(new Object[] {e, "Proceeding with taglib resolution."});
                    log.log(CommonLogLevels.DEBUG, entries);
                }
                catch ( XmlPullParserException e )
                {
                    List entries = Arrays.asList(new Object[] {e, "Proceeding with taglib resolution."});
                    log.log(CommonLogLevels.DEBUG, entries);
                }
                catch ( IOException e )
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
        }
        finally
        {
            if ( tldStream != null )
            {
                try
                {
                    tldStream.close();
                }
                catch ( IOException e )
                {
                }
            }
        }

        List entries = Arrays.asList( new Object[] {
            "Returning taglib: ",
            tlib,
            " from prefixed TLD file strategy for taglib {prefix: ",
            prefix,
            ", taglib: ",
            taglib,
            "} using classloader: ",
            cloader } );

        log.log( CommonLogLevels.DEBUG, entries );

        return tlib;
    }

    public void setLog( MarmaladeLog log )
    {
        this.log = log;
    }
}