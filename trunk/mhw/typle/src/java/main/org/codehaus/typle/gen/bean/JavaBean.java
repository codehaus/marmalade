/*
 * $Id$
 */

package org.codehaus.typle.gen.bean;

import org.codehaus.typle.AbstractTypeAnnotation;
import org.codehaus.typle.TypeAnnotation;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public class JavaBean
    extends AbstractTypeAnnotation
    implements TypeAnnotation
{
    private final boolean generate;

    private final String beanName;

    private final JavaBeanType beanType;

    private JavaBean(boolean generate, String beanName,
        JavaBeanType beanType)
    {
        this.generate = generate;
        this.beanName = beanName;
        this.beanType = beanType;
    }

    public JavaBean(String beanName, JavaBeanType type) {
        this(true, beanName, type);
    }

    private static final JavaBean DO_NOT_GENERATE
        = new JavaBean(false, null, null);

    public static JavaBean doNotGenerate() {
        return DO_NOT_GENERATE;
    }

    public boolean generate() {
        return generate;
    }

    public String getBeanName() {
        return beanName;
    }

    public JavaBeanType getBeanType() {
        return beanType;
    }
}
