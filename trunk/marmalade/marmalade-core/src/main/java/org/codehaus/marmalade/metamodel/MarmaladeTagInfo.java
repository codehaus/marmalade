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
package org.codehaus.marmalade.metamodel;


/**
 * @author jdcasey
 */
public class MarmaladeTagInfo
{
    private String scheme;
    private String taglib;
    private String element;
    private String prefix;
    private int sourceLine = -1;

    private String filename;
    private String sourceFile;
    
    public MarmaladeTagInfo(  )
    {
    }

    public String getElement(  )
    {
        return element;
    }

    public void setElement( String element )
    {
        this.element = element;
    }

    public String getScheme(  )
    {
        return scheme;
    }

    public void setScheme( String scheme )
    {
        this.scheme = scheme;
    }

    public String getTaglib(  )
    {
        return taglib;
    }

    public void setTaglib( String taglib )
    {
        this.taglib = taglib;
    }

    public String getPrefix(  )
    {
        return prefix;
    }

    public void setPrefix( String prefix )
    {
        this.prefix = prefix;
    }

    public int getSourceLine(  )
    {
        return sourceLine;
    }

    public void setSourceLine( int sourceLine )
    {
        this.sourceLine = sourceLine;
    }

    public void setSourceFile( String sourceFile )
    {
        this.sourceFile = sourceFile;
    }

    public String getSourceFile(  )
    {
        return sourceFile;
    }
    
}
