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
package org.codehaus.marmalade.metamodel.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class PrefixedDefFileDefinitionStrategy
    implements TaglibDefinitionStrategy
{
    public PrefixedDefFileDefinitionStrategy(  )
    {
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        ClassLoader cloader = getClass(  ).getClassLoader(  );
        MarmaladeTagLibrary tlib = null;

        InputStream defStream = null;

        try
        {
            defStream = cloader.getResourceAsStream( "META-INF/" + prefix + "/"
                    + taglib + ".def" );

            if ( defStream != null )
            {
                String className = null;

                try
                {
                    BufferedReader reader = new BufferedReader( new InputStreamReader( 
                                defStream ) );

                    className = reader.readLine(  );
                    reader.close(  );
                }

                // Ignore this and return null.
                catch ( IOException e )
                {
                    //TODO: log this exception
                }

                if ( ( className != null ) && ( className.length(  ) > 0 ) )
                {
                    try
                    {
                        Class tlCls = cloader.loadClass( className );

                        if ( MarmaladeTagLibrary.class.isAssignableFrom( tlCls ) )
                        {
                            tlib = ( MarmaladeTagLibrary ) tlCls.newInstance(  );
                        }
                    }

                    // Ignore these, and return null.
                    catch ( InstantiationException e )
                    {
                        //TODO: log this exception
                    }
                    catch ( IllegalAccessException e )
                    {
                        //TODO: log this exception
                    }
                    catch ( ClassNotFoundException e )
                    {
                        //TODO: log this exception
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
                    defStream.close(  );
                }
                catch ( IOException e )
                {
                }
            }
        }

        return tlib;
    }
}
