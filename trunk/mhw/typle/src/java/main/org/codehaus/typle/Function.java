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
    private final NamedTypeList formalParameters;

    public Function(Type returnType) {
        this.returnType = returnType;
        this.formalParameters = new NamedTypeList();
    }

    public Function(Type returnType, NamedType[] formalParameters) {
        this.returnType = returnType;
        this.formalParameters = NamedTypeList.fromArray(formalParameters);
    }

    private Function(Type returnType, NamedTypeList formalParameters) {
        this.returnType = returnType;
        this.formalParameters = formalParameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public NamedTypeList getFormalParameters() {
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

    public Function addFormalParameter(NamedType parameter) {
        return new Function(returnType, formalParameters.add(parameter));
    }
}
