/* Created on Aug 24, 2004 */
package org.codehaus.marmalade.lb.model.beans;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.codehaus.marmalade.el.BareBonesExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.TagInstantiationException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.DefaultContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

/**
 * @author jdcasey
 */
public class BeanBoundTagFactoryTest
    extends TestCase
{
    
    public void testShouldConstructWithBeanClassExecMethodConstructorArgsPropertiesMethodParamsAndEL() throws SecurityException, NoSuchMethodException {
        Class cls = TestBeanWithExecCtx.class;
        Method meth = cls.getMethod("execute", new Class[] {MarmaladeExecutionContext.class});
        
        List constructorArgs = new LinkedList();
        Map properties = new TreeMap();
        List methodParams = new LinkedList();
        ExpressionEvaluator el = new BareBonesExpressionEvaluator();
        
        MarmaladeTagInfo tagInfo = new MarmaladeTagInfo();
        tagInfo.setTaglib("unitTest");
        tagInfo.setScheme("test");
        tagInfo.setElement("test");
        
        BeanBoundTagFactory factory = new BeanBoundTagFactory(tagInfo, cls, meth, constructorArgs, properties, methodParams, el);
    }

    public void testShouldConstructWithBeanClassExecMethodConstructorArgsPropertiesMethodParamsAndNullEL() throws SecurityException, NoSuchMethodException {
        Class cls = TestBeanWithExecCtx.class;
        Method meth = cls.getMethod("execute", new Class[] {MarmaladeExecutionContext.class});
        
        List constructorArgs = new LinkedList();
        Map properties = new TreeMap();
        List methodParams = new LinkedList();
        ExpressionEvaluator el = null;
        
        MarmaladeTagInfo tagInfo = new MarmaladeTagInfo();
        tagInfo.setTaglib("unitTest");
        tagInfo.setScheme("test");
        tagInfo.setElement("test");
        
        BeanBoundTagFactory factory = new BeanBoundTagFactory(tagInfo, cls, meth, constructorArgs, properties, methodParams, el);
    }

    public void testShouldCreateBeanWithConstructorArgAndContextMethodParamAndCreateWrapperTag() throws SecurityException, NoSuchMethodException, TagInstantiationException, MarmaladeExecutionException {
        Class cls = TestBeanWithConstructorArg.class;
        Method meth = cls.getMethod("execute", new Class[] {MarmaladeExecutionContext.class});
        
        List constructorArgs = new LinkedList();
        constructorArgs.add("test");
        
        Map properties = new TreeMap();
        List methodParams = new LinkedList();
        methodParams.add("${context}");
        
        ExpressionEvaluator el = null;
        
        MarmaladeTagInfo tagInfo = new MarmaladeTagInfo();
        tagInfo.setTaglib("unitTest");
        tagInfo.setScheme("test");
        tagInfo.setElement("test");
        
        BeanBoundTagFactory factory = new BeanBoundTagFactory(tagInfo, cls, meth, constructorArgs, properties, methodParams, el);
        
        MarmaladeTag tag = factory.newTag();
        
        assertTrue("tag should be a bean wrapper", (tag instanceof BeanTagWrapper));
        
        MarmaladeExecutionContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        String arg = (String)ctx.getVariable(TestBeanWithConstructorArg.ARG_VAR, null);
        assertEquals("arg should be same as input arg", "test", arg);
    }

    public void testShouldCreateBeanWithPropertyAndContextMethodParamAndCreateWrapperTag() throws SecurityException, NoSuchMethodException, TagInstantiationException, MarmaladeExecutionException {
        Class cls = TestBeanWithProperty.class;
        Method meth = cls.getMethod("execute", new Class[] {MarmaladeExecutionContext.class});
        
        List constructorArgs = new LinkedList();
        
        Map properties = new TreeMap();
        properties.put("property", "test");
        
        List methodParams = new LinkedList();
        methodParams.add("${context}");
        
        ExpressionEvaluator el = new BareBonesExpressionEvaluator();
        
        MarmaladeTagInfo tagInfo = new MarmaladeTagInfo();
        tagInfo.setTaglib("unitTest");
        tagInfo.setScheme("test");
        tagInfo.setElement("test");
        
        BeanBoundTagFactory factory = new BeanBoundTagFactory(tagInfo, cls, meth, constructorArgs, properties, methodParams, el);
        
        MarmaladeTag tag = factory.newTag();
        
        assertTrue("tag should be a bean wrapper", (tag instanceof BeanTagWrapper));
        
        MarmaladeExecutionContext ctx = new DefaultContext();
        tag.execute(ctx);
        
        String property = (String)ctx.getVariable(TestBeanWithProperty.PROPERTY_VAR, null);
        assertEquals("property should be same as input property", "test", property);
    }

}
