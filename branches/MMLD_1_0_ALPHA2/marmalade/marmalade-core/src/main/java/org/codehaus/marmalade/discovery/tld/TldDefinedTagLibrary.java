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
/* Created on Apr 12, 2004 */
package org.codehaus.marmalade.discovery.tld;

import org.codehaus.marmalade.metamodel.AbstractMarmaladeTagLibrary;

/**
 * @author jdcasey
 */
public class TldDefinedTagLibrary
    extends AbstractMarmaladeTagLibrary
{
    private String shortName;

    private String tldVersion;

    public TldDefinedTagLibrary()
    {
    }

    public void setShortname( String shortName )
    {
        this.shortName = shortName;
    }

    public void setTlibversion( String tlibversion )
    {
        this.tldVersion = tlibversion;
    }

    public void registerTag( String name, Class tagClass )
    {
        super.registerTag( name, tagClass );
    }

    public String getShortName()
    {
        return shortName;
    }

    public String getTldVersion()
    {
        return tldVersion;
    }
}