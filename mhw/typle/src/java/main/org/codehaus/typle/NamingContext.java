/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A <code>NamingContext</code> carries information that is used while
 * attempting to lookup a type by name. This includes the following:
 * 
 * <ul>
 * <li>A list of namespaces that should be searched when looking for the
 * type.
 * </ul>
 * 
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
final class NamingContext {
    private final NamingContext next;
    private final String namespace;

    public NamingContext(String namespace) {
        this.next = null;
        this.namespace = namespace;
    }

    private NamingContext(NamingContext tail, String namespace) {
        this.next = tail;
        this.namespace = namespace;
    }

    public NamingContext addNamespace(String namespace) {
        return new NamingContext(this, namespace);
    }

    public java.util.Iterator iterator() {
        return new Iterator(this);
    }

    private static class Iterator implements java.util.Iterator {
        NamingContext head;

        Iterator(NamingContext head) {
            this.head = head;
        }

        public boolean hasNext() {
            return head != null;
        }

        public Object next() {
            String namespace = head.namespace;
            head = head.next;
            return namespace;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
