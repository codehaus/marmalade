/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.tags.httpunit.structure.AbstractWebResponseSubTag;

/**
 * @author jdcasey
 */
public abstract class AbstractAssertionTag extends AbstractWebResponseSubTag{

  protected AbstractAssertionTag(MarmaladeTagInfo tagInfo){
    super(tagInfo);
  }

  protected abstract boolean test(MarmaladeExecutionContext context)
  throws MarmaladeExecutionException;
  
  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    setUp(context);
    processChildren(context);
    if(!test(context)) {
      throw buildException();
    }
  }
  
  protected void setUp(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
  }

  protected void tearDown(){
  }

  protected HttpAssertionFailedException buildException(){
    return new HttpAssertionFailedException("Assertion failed.");
  }


  protected void doReset(){
    tearDown();
    super.doReset();
  }
}
