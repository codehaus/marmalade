/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class Function implements Type {
    private final Type returnType;
    private final BindingList formalParameters;

    public Function(Type returnType) {
        this.returnType = returnType;
        this.formalParameters = new BindingList();
    }

    public Function(Type returnType, Binding[] formalParameters) {
        this.returnType = returnType;
        this.formalParameters = BindingList.fromArray(formalParameters);
    }

    private Function(Type returnType, BindingList formalParameters) {
        this.returnType = returnType;
        this.formalParameters = formalParameters;
    }

    public Function addFormalParameter(String name, Type type) {
        return new Function(returnType,
                            formalParameters.add(new Binding(name, type)));
    }

    public Type getReturnType() {
        return returnType;
    }

    public BindingList getFormalParameters() {
        return formalParameters;
    }

    public String getTypeName() {
        return "(" + formalParameters.signature() + "): "
               + returnType.getTypeName();
    }

    public Type getWrappedType() {
        return null;
    }

    public String toString() {
        return getTypeName();
    }

    public void resolvePlaceHolders() {
    }
}
