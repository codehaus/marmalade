
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
