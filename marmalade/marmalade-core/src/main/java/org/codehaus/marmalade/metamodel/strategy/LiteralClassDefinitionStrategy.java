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

import org.codehaus.marmalade.model.MarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class LiteralClassDefinitionStrategy implements TaglibDefinitionStrategy
{
    public LiteralClassDefinitionStrategy(  )
    {
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib )
    {
        ClassLoader cloader = getClass(  ).getClassLoader(  );
        MarmaladeTagLibrary tlib = null;

        try
        {
            Class tlCls = cloader.loadClass( taglib );

            if ( MarmaladeTagLibrary.class.isAssignableFrom( tlCls ) )
            {
                tlib = ( MarmaladeTagLibrary ) tlCls.newInstance(  );
            }
        }

        // Ignore these, and return null.
        catch ( InstantiationException e )
        {
        }
        catch ( IllegalAccessException e )
        {
        }
        catch ( ClassNotFoundException e )
        {
        }

        return tlib;
    }
}
