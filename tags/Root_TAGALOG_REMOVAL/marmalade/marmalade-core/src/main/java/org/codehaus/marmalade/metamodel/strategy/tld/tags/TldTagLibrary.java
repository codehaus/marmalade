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
package org.codehaus.marmalade.metamodel.strategy.tld.tags;

import org.codehaus.tagalog.AbstractTagLibrary;

/**
 * @author jdcasey
 */
public class TldTagLibrary extends AbstractTagLibrary
{
    public static final String NS_URL = "http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd";
    public static final String TAGLIB_TAG = "taglib";
    public static final String TLIBVERSION_TAG = "tlibversion";
    public static final String SHORTNAME_TAG = "shortname";
    public static final String TAG_TAG = "tag";
    public static final String NAME_TAG = "name";
    public static final String TAGCLASS_TAG = "tagclass";

    public TldTagLibrary(  )
    {
        registerTag( TAGLIB_TAG, TaglibTag.class );
        registerTag( TLIBVERSION_TAG, TlibversionTag.class );
        registerTag( SHORTNAME_TAG, ShortnameTag.class );
        registerTag( TAG_TAG, TagTag.class );
        registerTag( NAME_TAG, NameTag.class );
        registerTag( TAGCLASS_TAG, TagclassTag.class );
    }
}
