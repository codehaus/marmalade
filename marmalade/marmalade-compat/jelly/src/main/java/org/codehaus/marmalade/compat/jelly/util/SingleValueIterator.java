/* Created on Jul 14, 2004 */
package org.codehaus.marmalade.compat.jelly.util;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public class SingleValueIterator implements Iterator {

    private Object value;
    private boolean valueHit = false;

    public SingleValueIterator(Object value) {
        this.value = value;
    }

    public void remove() {
        if(valueHit) {
            value = null;
        }
    }

    public boolean hasNext() {
        return !valueHit;
    }

    public Object next() {
        Object result = null;
        
        if(valueHit) {
            result = null;
        }
        else {
            result = value;
            valueHit = true;
        }
        
        return result;
    }

}
