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
/* Created on Mar 25, 2004 */
package org.codehaus.marmalade.model;

import org.codehaus.marmalade.el.ExpressionEvaluationException;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;

import java.util.Iterator;

/**
 * @author jdcasey
 */
public interface MarmaladeAttributes
{
    public ExpressionEvaluator getExpressionEvaluator(  );

    public Object getValue( String name, Class type,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException;

    public Object getValue( String namespace, String name, Class type,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException;

    public Object getValue( String name, Class type,
        MarmaladeExecutionContext context, Object defaultVal )
        throws ExpressionEvaluationException;

    public Object getValue( String namespace, String name, Class type,
        MarmaladeExecutionContext context, Object defaultVal )
        throws ExpressionEvaluationException;

    /** Assume Object.class is the type. */
    public Object getValue( String name, MarmaladeExecutionContext context )
        throws ExpressionEvaluationException;

    /** Assume Object.class is the type. */
    public Object getValue( String namespace, String name,
        MarmaladeExecutionContext context )
        throws ExpressionEvaluationException;

    /** Assume Object.class is the type. */
    public Object getValue( String name, MarmaladeExecutionContext context,
        Object defaultVal ) throws ExpressionEvaluationException;

    public Object getValue( String namespace, String name,
        MarmaladeExecutionContext context, Object defaultVal )
        throws ExpressionEvaluationException;

    public Iterator iterator(  );
}
