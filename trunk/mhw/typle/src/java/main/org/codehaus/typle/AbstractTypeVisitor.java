/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class AbstractTypeVisitor {
    public Type visit(Type type) {
        if (type instanceof AnnotatedType) {
            return visitAnnotatedType((AnnotatedType) type);
        } else if (type instanceof Function) {
            return visitFunction((Function) type);
        } else if (type instanceof RecordType) {
            return visitRecord((RecordType) type);
        } else {
            return type;
        }
    }

    protected AnnotatedType visitAnnotatedType(AnnotatedType type) {
        Type before = type.getAnnotatedType();
        Type after = visit(before);

        if (!after.equals(before)) {
            type.setAnnotatedType(after);
        }
        return type;
    }

    protected Binding visitBinding(Binding binding) {
        Type before = binding.getType();
        Type after = visit(before);

        if (!after.equals(before))
            return new Binding(binding.getName(), after);
        else
            return binding;
    }

    protected Object visitBindingList(BindingList bindingList) {
        Binding[] newBindings = new Binding[bindingList.size()];
        boolean changed = false;

        for (int i = 0; i < bindingList.size(); i++) {
            Binding before = bindingList.get(i);
            Binding after = visitBinding(before);

            newBindings[i] = after;
            if (!after.equals(before))
                changed = true;
        }
        if (changed)
            return BindingList.fromArray(newBindings);
        else
            return bindingList;
    }

    protected Type visitFunction(Function function) {
        visitBindingList(function.getFormalParameters());
        visit(function.getReturnType());
        return function;
    }

    protected Type visitRecord(RecordType record) {
        BindingList before = record.getFields();
        BindingList after = (BindingList) visitBindingList(before);

        if (!after.equals(before))
            return new RecordType(record.getTypeName(), after.toArray());
        else
            return record;
    }
}
