/* Created on Jul 14, 2004 */
package org.codehaus.marmalade.compat.jelly.runtime;

import java.util.Iterator;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.expression.Expression;
import org.codehaus.marmalade.compat.jelly.util.SingleValueIterator;

/**
 * @author jdcasey
 */
public class MarmaladeCompatJellyExpression implements Expression {

    private final Object value;

    public MarmaladeCompatJellyExpression(Object value) {
        this.value = value;
    }

    public String getExpressionText() {
        return String.valueOf(value);
    }

    public Object evaluate(JellyContext context) {
        return value;
    }

    public String evaluateAsString(JellyContext context) {
        return String.valueOf(value);
    }

    public boolean evaluateAsBoolean(JellyContext context) {
        boolean result = false;
        
        if(value instanceof Boolean) {
            result = ((Boolean)value).booleanValue();
        }
        else {
            result = Boolean.valueOf(String.valueOf(value)).booleanValue();
        }
        
        return result;
    }

    public Iterator evaluateAsIterator(JellyContext context) {
        Iterator result = null;
        if(value instanceof Iterator) {
            result = (Iterator)value;
        }
        else {
            result = new SingleValueIterator(value);
        }
        
        return result;
    }

    public Object evaluateRecurse(JellyContext context) {
        return value;
    }

    public String toString() {
        return "MarmaladeCompatJellyExpression [value=" + value + "]";
    }
}
