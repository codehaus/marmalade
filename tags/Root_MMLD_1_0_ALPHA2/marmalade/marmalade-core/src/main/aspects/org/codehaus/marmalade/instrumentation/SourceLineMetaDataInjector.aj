/* Created on Aug 10, 2004 */
package org.codehaus.marmalade.instrumentation;

import org.codehaus.marmalade.MarmaladeException;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;

import java.io.PrintWriter;
import java.io.PrintStream;

/**
 * @author jdcasey
 */
public aspect SourceLineMetaDataInjector extends ErrorEnhancer {
    
    private MarmaladeTagInfo MarmaladeExecutionException._tagInfo;
    
    Object around(MarmaladeException ex): getAnyMessage(ex)
    {
        String result = getOriginalMessage(ex);
        if(ex instanceof MarmaladeExecutionException) {
            MarmaladeTagInfo mti = ((MarmaladeExecutionException)ex)._tagInfo;
            if(mti == null) {
                result = "<<script-source not captured>>";
            }
            else {
                result = LineLocalUtil.formatForSingleLine(mti);
            }
        
            result = result + ": ";
        }
        
        result = result + proceed(ex);
        
        return result;
    }
    
    private pointcut exceptionConstruction(MarmaladeExecutionException ex):
        execution(MarmaladeExecutionException+.new(..)) &&
        this(ex);
    
    private pointcut tagThrowingException(MarmaladeTag tag):
        execution(* MarmaladeTag+.*(..)) &&
        this(tag);
    
    // Retrieve the taginfo from the originating tag.
    before(MarmaladeTag tag, MarmaladeExecutionException ex) :
        exceptionConstruction(ex) &&
        cflow(tagThrowingException(tag))
    {
        ex._tagInfo = tag.getTagInfo();
    }

}
