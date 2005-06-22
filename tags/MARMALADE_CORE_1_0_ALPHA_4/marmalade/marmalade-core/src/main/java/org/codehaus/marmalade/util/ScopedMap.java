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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Create a local copy of all mappings in the super-map.
 * 
 * @author John Casey
 */
public class ScopedMap
    implements Map
{
    
    private Map superMap;

    private Map thisMap;

    private ScopedMapEntriesSet keySet;

    private ScopedMapEntriesSet valueSet;

    private ScopedMapEntriesSet entrySet;

    public ScopedMap()
    {
        init( Collections.EMPTY_MAP );
    }

    public ScopedMap( Map superMap )
    {
        init( superMap );
    }

    private void init( Map superMap )
    {
        Map m = superMap;

        if ( m == null )
        {
            m = Collections.EMPTY_MAP;
        }

        this.superMap = m;

        this.thisMap = new HashMap();

        this.entrySet = new ScopedMapEntriesSet( superMap, thisMap, null );
        this.keySet = new ScopedMapEntriesSet( superMap, thisMap, Boolean.TRUE );
        this.valueSet = new ScopedMapEntriesSet( superMap, thisMap, Boolean.FALSE );
    }
    
    public Map getSuperMap()
    {
        return superMap;
    }

    public Map getLocalMap()
    {
        return thisMap;
    }

    private void update()
    {
        entrySet.update();
        keySet.update();
        valueSet.update();
    }

    public int size()
    {
        return superMap.size() + thisMap.size();
    }

    public void clear()
    {
        thisMap.clear();
        update();
    }

    public boolean isEmpty()
    {
        return superMap.isEmpty() && thisMap.isEmpty();
    }

    public boolean containsKey( Object key )
    {
        return thisMap.containsKey( key ) || superMap.containsKey( key );
    }

    public boolean containsValue( Object value )
    {
        return thisMap.containsValue( value ) || superMap.containsValue( value );
    }

    public Collection values()
    {
        return valueSet;
    }

    public void putAll( Map t )
    {
        boolean changed = false;

        for ( Iterator it = t.keySet().iterator(); it.hasNext(); )
        {
            Object key = it.next();

            if ( !superMap.containsKey( key ) )
            {
                thisMap.put( key, t.get( key ) );
                changed = true;
            }
        }

        if ( changed )
        {
            update();
        }
    }

    public Set entrySet()
    {
        return entrySet;
    }

    public Set keySet()
    {
        return keySet;
    }

    public Object get( Object key )
    {
        Object result = thisMap.get( key );

        if ( result == null )
        {
            result = superMap.get( key );
        }

        return result;
    }

    public Object remove( Object key )
    {
        Object result = thisMap.remove( key );

        if ( result != null )
        {
            update();
        }

        return result;
    }

    public Object put( Object key, Object value )
    {
        Object result = null;

        result = thisMap.put( key, value );
        update();

        return result;
    }

    public String toString()
    {
        return "ScopedMap:\n==========================\nlocal:\n\t" + String.valueOf( thisMap ) + "\n\nparent:\n\t"
            + String.valueOf( superMap );
    }
}