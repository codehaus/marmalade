/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import org.codehaus.typle.Binding;
import org.codehaus.typle.Function;
import org.codehaus.typle.JavaNames;
import org.codehaus.typle.src.BoilerPlateComment;
import org.codehaus.typle.src.SourceContainer;

/**
 * Simple helper methods for creating Java artefacts.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaHelper {
    // prevent instantiation
    private JavaHelper() {
    }

    private static final String BOILERPLATE_COMMENT
        = "/*\n"
        + " * Automatically generated. Do not edit.\n"
        + " */\n\n";

    /**
     * Create the basic contents of a Java source file. This comprises
     * a boilerplate 'do not edit' comment, the package declaration and
     * the class declaration.
     *
     * @param source The source container to add the content to.
     * @param className The name of the class that is being generated.
     * @return The <code>JavaClass</code> that represents the public
     * class added to the <code>SourceContainer</code>.
     */
    public static JavaClass initSourceFile(SourceContainer source,
        String className)
    {
        source.add(new BoilerPlateComment(BOILERPLATE_COMMENT, 1));
        source.add(new JavaPackage(JavaNames.packageName(className)));
        JavaClass srcClass = new JavaClass(JavaNames.className(className));
        source.add(srcClass);
        return srcClass;
    }

    public static void addGetter(SourceContainer source, Binding field) {
        String methodName;
        Function signature;
        String body;

        methodName = JavaNames.toCamelCase(field.getName());
        methodName = "get" + JavaNames.upperCaseFirstLetter(methodName);
        signature = new Function(field.getType(), null);
        body = "return " + field.getName() + ";";
        source.add(new Method(methodName, signature, body));
    }
}
