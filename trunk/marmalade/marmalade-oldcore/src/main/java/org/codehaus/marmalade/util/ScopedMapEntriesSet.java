/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jdcasey
 */
public class ScopedMapEntriesSet implements Set {
  
  private Map superMap;
  private Map thisMap;
  private Boolean extractKey;
  
  private List entries = new ArrayList();
  
  public ScopedMapEntriesSet(Map superMap, Map thisMap, Boolean extractKey) {
    this.superMap = superMap;
    this.thisMap = thisMap;
    this.extractKey = extractKey;
    update();
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

  public int size() {
    return entries.size();
  }

  public void clear() {
    thisMap.clear();
    for (Iterator it = entries.iterator(); it.hasNext();) {
      ScopedMapEntry entry = (ScopedMapEntry)it.next();
      if(entry.isMutable()){
        it.remove();
      }
    }
  }

  public boolean isEmpty() {
    return thisMap.isEmpty() && superMap.isEmpty();
  }

  public Object[] toArray() {
    Object[] objects = new Object[entries.size()];
    for (int i = 0; i < objects.length; i++) {
      objects[i] = extract((ScopedMapEntry)entries.get(i));
    }
    return objects;
  }

  public boolean add(Object o) {
    throw new UnsupportedOperationException("Add operation is not supported.");
  }

  public boolean contains(Object o) {
    for (Iterator it = entries.iterator(); it.hasNext();) {
      if(extract((ScopedMapEntry)it.next()).equals(o)){
        return true;
      }
    }
    return false;
  }

  public boolean remove(Object o) {
    for (Iterator it = entries.iterator(); it.hasNext();) {
      ScopedMapEntry entry = (ScopedMapEntry)it.next();
      if(extract(entry).equals(o)){
        if(entry.isMutable()){
          it.remove();
          thisMap.remove(entry.getKey());
          return true;
        }
        else{
          return false;
        }
      }
    }
    
    return false;
  }

  public boolean addAll(Collection c) {
    throw new UnsupportedOperationException("Add-all operation is not supported.");
  }

  public boolean containsAll(Collection c) {
    for (Iterator it = entries.iterator(); it.hasNext();) {
      if(!c.contains(extract((ScopedMapEntry)it.next()))){
        return false;
      }
    }
    return true;
  }

  public boolean removeAll(Collection c) {
    boolean changed = false;
    for (Iterator it = entries.iterator(); it.hasNext();) {
      ScopedMapEntry entry = (ScopedMapEntry)it.next();
      if(c.contains(extract(entry))){
        if(entry.isMutable()){
          it.remove();
          thisMap.remove(entry.getKey());
          changed = true;
        }
      }
    }
    
    return changed;
  }

  public boolean retainAll(Collection c) {
    boolean changed = false;
    for (Iterator it = entries.iterator(); it.hasNext();) {
      ScopedMapEntry entry = (ScopedMapEntry)it.next();
      if(!c.contains(extract(entry))){
        if(entry.isMutable()){
          it.remove();
          thisMap.remove(entry.getKey());
          changed = true;
        }
      }
    }
    
    return changed;
  }
  
  public Iterator iterator() {
    return new ScopedMapEntriesIterator(this, extractKey);
  }

  public Object[] toArray(Object[] a) {
    for (int i = 0; i < a.length; i++) {
      a[i] = entries.get(i);
    }
    
    if(entries.size() > a.length){
      throw new ArrayIndexOutOfBoundsException(a.length);
    }
    else{
      return a;
    }
  }

  Iterator entryIterator(){
    return entries.iterator();
  }
  
  void removeEntry(ScopedMapEntry entry){
    if(entry.isMutable()){
      entries.remove(entry);
      thisMap.remove(entry.getKey());
    }
  }
  
  void update(){
    for (Iterator it = superMap.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      Object key = entry.getKey();
      if(!thisMap.containsKey(key)){
        ScopedMapEntry sme = new ScopedMapEntry(entry, false);
        entries.add(sme);
      }
    }
    
    for (Iterator it = thisMap.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      ScopedMapEntry sme = new ScopedMapEntry(entry, true);
      entries.add(sme);
    }
  }

}
