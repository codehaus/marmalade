/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * A <code>RecordType</code> is a data type for items that contain an
 * unordered set of named values.
 *
 * @author Mark Wilkinson
 * @version $Revision$
 */
public final class RecordType implements Type {
    private NamedTypeList fields;

    public RecordType() {
        this.fields = new NamedTypeList();
    }

    public RecordType(NamedType[] fields) {
        this.fields = NamedTypeList.fromArray(fields);
    }

    private RecordType(NamedTypeList fields) {
        this.fields = fields;
    }

    public RecordType addField(String name, Type type) {
        return new RecordType(fields.add(new NamedType(name, type)));
    }

    public Type getField(String name) {
        NamedType t = fields.get(name);
        return (t == null)? null : t.getType();
    }

    /**
     * @see com.fintricity.messaging.type.Type#getName()
     */
    public String getName() {
        return "record {" + fields.signature() + "}";
    }

    /**
     * @see com.fintricity.messaging.type.Type#getName()
     */
    public String getSignature() {
        return "{" + fields.signature() + "}";
    }
}
