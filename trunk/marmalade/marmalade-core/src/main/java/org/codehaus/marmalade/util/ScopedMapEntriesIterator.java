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
/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.util;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class ScopedMapEntriesIterator implements Iterator
{
    private ScopedMapEntriesSet collection;
    private Iterator entryIterator;
    private Boolean extractKey;
    private ScopedMapEntry current;

    public ScopedMapEntriesIterator( ScopedMapEntriesSet collection,
        Boolean extractKey )
    {
        this.collection = collection;
        this.entryIterator = collection.entryIterator(  );
        this.extractKey = extractKey;
    }

    public Object extract( ScopedMapEntry entry )
    {
        if ( extractKey == null )
        {
            return entry;
        }
        else if ( Boolean.TRUE == extractKey )
        {
            return entry.getKey(  );
        }
        else
        {
            return entry.getValue(  );
        }
    }

    public void remove(  )
    {
        if ( current == null )
        {
            throw new IllegalArgumentException( 
                "You must call next() before calling remove()." );
        }
        else
        {
            collection.removeEntry( current );
        }
    }

    public boolean hasNext(  )
    {
        return entryIterator.hasNext(  );
    }

    public Object next(  )
    {
        current = ( ScopedMapEntry ) entryIterator.next(  );

        return extract( current );
    }
}
