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

    public Type getReturnType() {
        return returnType;
    }

    public BindingList getFormalParameters() {
        return formalParameters;
    }

    public String getName() {
        return "(" + formalParameters.signature() + "): "
               + returnType.getSignature();
    }

    public String getSignature() {
        return "(" + formalParameters.signature() + "): "
               + returnType.getSignature();
    }

    public Function addFormalParameter(Binding parameter) {
        return new Function(returnType, formalParameters.add(parameter));
    }
}
