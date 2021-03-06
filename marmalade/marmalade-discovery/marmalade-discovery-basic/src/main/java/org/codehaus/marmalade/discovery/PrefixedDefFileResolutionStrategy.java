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

import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.monitor.log.CommonLogLevels;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * @author jdcasey
 */
public class PrefixedDefFileResolutionStrategy
    extends AbstractTaglibResolutionStrategy
{
    public PrefixedDefFileResolutionStrategy()
    {
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib, ClassLoader cloader )
    {
        MarmaladeLog log = getLog();

        MarmaladeTagLibrary tlib = null;

        InputStream defStream = null;

        try
        {
            String defResource = "META-INF/" + prefix + "/" + taglib + ".def";

            defStream = cloader.getResourceAsStream( defResource );

            if ( defStream != null )
            {
                String className = null;

                try
                {
                    BufferedReader reader = new BufferedReader( new InputStreamReader( defStream ) );

                    className = reader.readLine();
                    
                    log.log(CommonLogLevels.DEBUG, "Tag library class is: " + className);
                    
                    reader.close();
                }

                // Ignore this and return null.
                catch ( IOException e )
                {
                    List entries = Arrays.asList( new Object[] { e, "Proceeding with taglib resolution." } );
                    log.log( CommonLogLevels.DEBUG, entries );
                }

                if ( ( className != null ) && ( className.length() > 0 ) )
                {
                    try
                    {
                        Class tlCls = cloader.loadClass( className );
                        
                        if ( MarmaladeTagLibrary.class.isAssignableFrom( tlCls ) )
                        {
                            tlib = (MarmaladeTagLibrary) tlCls.newInstance();
                        }
                        else
                        {
                            log.log(CommonLogLevels.WARN, "Taglib class read from definition file does not implement MarmaladeTagLibrary.");
                        }
                    }

                    // Ignore these, and return null.
                    catch ( InstantiationException e )
                    {
                        List entries = Arrays.asList( new Object[] { e, "Proceeding with taglib resolution." } );
                        log.log( CommonLogLevels.DEBUG, entries );
                    }
                    catch ( IllegalAccessException e )
                    {
                        List entries = Arrays.asList( new Object[] { e, "Proceeding with taglib resolution." } );
                        log.log( CommonLogLevels.DEBUG, entries );
                    }
                    catch ( ClassNotFoundException e )
                    {
                        List entries = Arrays.asList( new Object[] { e, "Proceeding with taglib resolution." } );
                        log.log( CommonLogLevels.DEBUG, entries );
                    }
                }
            }
        }

        // Return null.
        finally
        {
            if ( defStream != null )
            {
                try
                {
                    defStream.close();
                }
                catch ( IOException e )
                {
                }
            }
        }

        List entries = Arrays.asList( new Object[] {
            "Returning taglib: ",
            tlib,
            " from prefixed definition file strategy for taglib {prefix: ",
            prefix,
            ", taglib: ",
            taglib,
            "} using classloader: ",
            cloader } );

        log.log( CommonLogLevels.DEBUG, entries );

        return tlib;
    }
}