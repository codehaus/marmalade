/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Class of objects that represent functions. A function has an ordered list
 * of named, typed parameters (the formal parameters) and a return type.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class Function implements Type {
    private Type returnType;
    private final BindingList formalParameters;

    /**
     * Create a new <code>Function</code> object, with the specified return
     * type and parameters.
     *
     * @param returnType The return type of the function. Must not be null.
     * @param formalParameters The formal parameters of the function, or
     * <code>null</code> if there are no parameters.
     */
    public Function(Type returnType, Binding[] formalParameters) {
        if (returnType == null)
            throw new NullPointerException("return type is null");
        this.returnType = returnType;
        if (formalParameters == null)
            this.formalParameters = new BindingList();
        else
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

    public void resolvePlaceHolders() throws TypeLookupException {
        if (returnType instanceof TypePlaceHolder) {
            returnType = ((TypePlaceHolder) returnType).resolve();
        }
        formalParameters.resolvePlaceHolders();
    }
}
