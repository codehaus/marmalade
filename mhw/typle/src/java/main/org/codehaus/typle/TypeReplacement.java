/*
 * $Id$
 */

package org.codehaus.typle;

import java.util.Map;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class TypeReplacement extends AbstractTypeVisitor {
    private Map replacements;

    public TypeReplacement(Map replacements) {
        this.replacements = replacements;
    }

    public Type visit(Type type) {
        Type replacement = (Type) replacements.get(type);
        if (replacement != null)
            return replacement;
        else
            return super.visit(type);
    }
}
