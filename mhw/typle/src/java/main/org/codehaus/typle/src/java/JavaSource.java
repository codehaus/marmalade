/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.util.Comparator;

import org.codehaus.typle.src.BoilerPlateComment;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class JavaSource implements Comparator {
    public static final JavaSource COMPARATOR = new JavaSource();

    private static final Class[] ARTEFACT_ORDER = new Class[] {
        BoilerPlateComment.class,
        JavaPackage.class,
        Import.class,
        JavaClass.class,
        Field.class,
    };

    // ensure use of singleton
    private JavaSource() {
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2) {
        int artefactTypeComparison = artefactTypeComparison(o1, o2);
        if (artefactTypeComparison != 0)
            return artefactTypeComparison;
        if (o1 instanceof BoilerPlateComment) {
            return boilerPlateCommentCompare((BoilerPlateComment) o1,
                                             (BoilerPlateComment) o2);
        } else if (o1 instanceof JavaPackage) {
            return packageCompare((JavaPackage) o1, (JavaPackage) o2);
        } else if (o1 instanceof Import) {
            return importCompare((Import) o1, (Import) o2);
        } else if (o1 instanceof JavaClass) {
            return javaClassCompare((JavaClass) o1, (JavaClass) o2);
        } else if (o1 instanceof Field) {
            return fieldCompare((Field) o1, (Field) o2);
        } else {
            throw new IllegalStateException("cannot compare "
                                            + o1.getClass().getName());
        }
    }

    private int artefactTypeComparison(Object o1, Object o2) {
        int o1Type = -1;
        int o2Type = -1;

        for (int i = 0; i < ARTEFACT_ORDER.length; i++) {
            Class c = ARTEFACT_ORDER[i];
            if (c.isInstance(o1))
                o1Type = i;
            if (c.isInstance(o2))
                o2Type = i;
        }
        if (o1Type == -1)
            throw new IllegalArgumentException(o1.getClass().getName()
                                + " is not a legitimate Java source artefact");
        if (o2Type == -1)
            throw new IllegalArgumentException(o2.getClass().getName()
                                + " is not a legitimate Java source artefact");
        return o1Type - o2Type;
    }

    private int boilerPlateCommentCompare(BoilerPlateComment o1,
                                          BoilerPlateComment o2)
    {
        return o1.getPriority() - o2.getPriority();
    }

    /**
     * @return 0, because all packages are considered equal.
     */
    private int packageCompare(JavaPackage o1, JavaPackage o2) {
        return 0;
    }

    private int importCompare(Import o1, Import o2) {
        return o1.getImportSpecification()
                 .compareTo(o2.getImportSpecification());
    }

    private int javaClassCompare(JavaClass o1, JavaClass o2) {
        Modifier o1AccessLevel = o1.getAccessLevel();
        Modifier o2AccessLevel = o2.getAccessLevel();

        if (o1AccessLevel != o2AccessLevel) {
            if (o1AccessLevel == Modifier.PUBLIC)
                return -1;
            if (o2AccessLevel == Modifier.PUBLIC)
                return 1;
        }
        return o1.getClassName().compareTo(o2.getClassName());
    }

    private int fieldCompare(Field o1, Field o2) {
        return o1.getFieldName().compareTo(o2.getFieldName());
    }
}
