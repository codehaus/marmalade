/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.util;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class ScopedMapEntriesIterator implements Iterator {
  
  private ScopedMapEntriesSet collection;
  private Iterator entryIterator;
  private Boolean extractKey;
  
  private ScopedMapEntry current;

  public ScopedMapEntriesIterator(ScopedMapEntriesSet collection, Boolean extractKey) {
    this.collection = collection;
    this.entryIterator = collection.entryIterator();
    this.extractKey = extractKey;
  }
  
  public Object extract(ScopedMapEntry entry){
    if(extractKey == null){
      return entry;
    }
    else if(Boolean.TRUE == extractKey){
      return entry.getKey();
    }
    else{
      return entry.getValue();
    }
  }

  public void remove() {
    if(current == null){
      throw new IllegalArgumentException("You must call next() before calling remove().");
    }
    else{
      collection.removeEntry(current);
    }
  }

  public boolean hasNext() {
    return entryIterator.hasNext();
  }

  public Object next() {
    current = (ScopedMapEntry)entryIterator.next();
    return extract(current);
  }

}
