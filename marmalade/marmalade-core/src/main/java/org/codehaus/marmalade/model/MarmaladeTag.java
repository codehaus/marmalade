/*
 *
 * Copyright (c) 2004 John Dennis Casey
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MetaAttributes;
import org.codehaus.marmalade.runtime.ConfigurationException;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/** Represents a marmalade tag. Simply used as a common place to specify behavior in the future.
 *
 * @author John Casey
 */
public interface MarmaladeTag
{
    Object getBody( MarmaladeExecutionContext context )
        throws ExpressionEvaluationException;

    Object getBody( MarmaladeExecutionContext context, Class targetType )
        throws ExpressionEvaluationException;

    MarmaladeAttributes getAttributes(  );

    void setExpressionEvaluator(ExpressionEvaluator el);
    
    ExpressionEvaluator getExpressionEvaluator(  )
        throws ConfigurationException;

    void execute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException;

    void setParent( MarmaladeTag parent );

    void addChild( MarmaladeTag child );

    MarmaladeTag getParent(  );

    MarmaladeTagInfo getTagInfo(  );

    void setAttributes(MarmaladeAttributes attributes);

    void appendBodyText(String string);
}
