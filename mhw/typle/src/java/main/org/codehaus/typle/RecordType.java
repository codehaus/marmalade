/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A <code>RecordType</code> is a data type for items that contain an
 * unordered set of typed fields.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public final class RecordType implements Type {
    private BindingList fields;

    public RecordType() {
        this.fields = new BindingList();
    }

    public RecordType(Binding[] fields) {
        this.fields = BindingList.fromArray(fields);
    }

    private RecordType(BindingList fields) {
        this.fields = fields;
    }

    public RecordType addField(String name, Type type) {
        return new RecordType(fields.add(new Binding(name, type)));
    }

    public BindingList getFields() {
        return fields;
    }

    public Type getField(String name) {
        Binding t = fields.get(name);
        return (t == null)? null : t.getType();
    }

    public String getTypeName() {
        return "[" + fields.signature() + "]";
    }

    public String toString() {
        return getTypeName();
    }

    public Type getWrappedType() {
        return null;
    }

    public Type resolvePlaceHolders() {
        return null;
    }
}
