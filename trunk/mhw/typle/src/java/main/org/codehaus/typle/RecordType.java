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
public final class RecordType extends TypeHelper implements Type {
    private BindingList fields;

    public RecordType() {
        super(null);
        this.fields = new BindingList();
    }

    public RecordType(String name) {
        super(name);
        if (name == null)
            throw new NullPointerException("name is null");
        this.fields = new BindingList();
    }

    public RecordType(Binding[] fields) {
        super(null);
        this.fields = BindingList.fromArray(fields);
    }

    public RecordType(String name, Binding[] fields) {
        super(name);
        if (name == null)
            throw new NullPointerException("name is null");
        this.fields = BindingList.fromArray(fields);
    }

    private RecordType(String name, BindingList fields) {
        super(name);
        this.fields = fields;
    }

    public RecordType addField(String name, Type type) {
        return new RecordType(super.getTypeName(),
                              fields.add(new Binding(name, type)));
    }

    public BindingList getFields() {
        return fields;
    }

    public Type getField(String name) {
        Binding t = fields.get(name);
        return (t == null)? null : t.getType();
    }

    public String getTypeName() {
        String s = super.getTypeName();
        if (s == null) {
            s = toString();
        }
        return s;
    }

    public String toString() {
        return "[" + fields.signature() + "]";
    }

    public void resolvePlaceHolders() throws TypeLookupException {
        fields.resolvePlaceHolders();
    }
}
