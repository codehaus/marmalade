/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import junit.framework.TestCase;

/**
 * @author jdcasey
 */
public class BeanTagWrapperTest
    extends TestCase
{
    
    public void testShouldConstructWithBeanExecuteMethodEmptyPropertiesAndEL() throws SecurityException, NoSuchMethodException {
        Class cls = TestBeanWithExecCtx.class;
        Method meth = cls.getMethod("execute", new Class[] {MarmaladeExecutionContext.class});
        BeanTagWrapper wrapper = new BeanTagWrapper(new TestBeanWithExecCtx(), meth, Collections.EMPTY_LIST, new BareBonesExpressionEvaluator());
    }

    public void testShouldConstructWithBeanExecuteMethodEmptyPropertiesAndNullEL() throws SecurityException, NoSuchMethodException {
        Class cls = TestBeanWithExecCtx.class;
        Method meth = cls.getMethod("execute", new Class[] {MarmaladeExecutionContext.class});
        BeanTagWrapper wrapper = new BeanTagWrapper(new TestBeanWithExecCtx(), meth, Collections.EMPTY_LIST, null);
    }

    public void testShouldExecuteWithContextExecuteParam() throws SecurityException, NoSuchMethodException, MarmaladeExecutionException {
        Class cls = TestBeanWithExecCtx.class;
        Method meth = cls.getMethod("execute", new Class[] {MarmaladeExecutionContext.class});
        
        List params = new LinkedList();
        params.add("${context}");
        
        TestBeanWithExecCtx bean = new TestBeanWithExecCtx();
        BeanTagWrapper wrapper = new BeanTagWrapper(bean, meth, params, null);
        
        wrapper.execute(new DefaultContext());
        
        assertTrue("bean should have executed", bean.didExecute());
    }

    public void testShouldExecuteWithContextMapExecuteParam() throws SecurityException, NoSuchMethodException, MarmaladeExecutionException {
        Class cls = TestBeanWithExecMap.class;
        Method meth = cls.getMethod("execute", new Class[] {Map.class});
        
        List params = new LinkedList();
        params.add("${contextMap}");
        
        TestBeanWithExecMap bean = new TestBeanWithExecMap();
        BeanTagWrapper wrapper = new BeanTagWrapper(bean, meth, params, null);
        
        wrapper.execute(new DefaultContext());
        
        assertTrue("bean should have executed", bean.didExecute());
    }

    public void testShouldExecuteWithNoExecuteParam() throws SecurityException, NoSuchMethodException, MarmaladeExecutionException {
        Class cls = TestBeanWithExecEmpty.class;
        Method meth = cls.getMethod("execute", new Class[] {});
        
        List params = new LinkedList();
        
        TestBeanWithExecEmpty bean = new TestBeanWithExecEmpty();
        BeanTagWrapper wrapper = new BeanTagWrapper(bean, meth, params, null);
        
        wrapper.execute(new DefaultContext());
        
        assertTrue("bean should have executed", bean.didExecute());
    }

    public void testShouldExecuteWithExecuteThrowingException() throws SecurityException, NoSuchMethodException {
        Class cls = TestBeanWithExecThrowingException.class;
        Method meth = cls.getMethod("execute", new Class[] {});
        
        List params = new LinkedList();
        
        TestBeanWithExecThrowingException bean = new TestBeanWithExecThrowingException();
        BeanTagWrapper wrapper = new BeanTagWrapper(bean, meth, params, null);
        
        try
        {
            wrapper.execute( new DefaultContext() );
            fail("Should have thrown a TestException wrapped in a MarmaladeExecutionException");
        }
        catch ( MarmaladeExecutionException e )
        {
            assertTrue("execution exception should contain real cause", (e.getCause() instanceof TestException));
        }
    }

}
