/* Created on Apr 22, 2004 */
package org.codehaus.marmalade.tags.httpunit;

import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;
import org.codehaus.marmalade.tags.httpunit.structure.*;


/**
 * @author jdcasey
 */
public abstract class AbstractAssertionTag extends AbstractWebResponseSubTag{

  protected AbstractAssertionTag(){
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
