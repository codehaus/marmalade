/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.util;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author jdcasey
 */
public class ScopedMapEntry implements Entry {
  
  private Map.Entry entry;
  private boolean mutable = true;

  public ScopedMapEntry(Map.Entry entry, boolean mutable) {
    this.entry = entry;
    this.mutable = mutable;
  }
  
  public boolean isMutable(){
    return mutable;
  }

  public Object getKey() {
    return entry.getKey();
  }

  public Object getValue() {
    return entry.getValue();
  }

  public Object setValue(Object value) {
    if(!mutable){
      throw new UnsupportedOperationException("Specified map entry is immutable.");
    }
    else{
      return entry.setValue(value);
    }
  }

}
