/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTagLibrary;


/**
 * @author jdcasey
 */
public interface MarmaladeTagBuilder{
    
  public static final String MARMALADE_RESERVED_NS = "marmalade";
  public static final String EXPRESSION_EVALUATOR_ATTRIBUTE = "el";
  
  
  public MarmaladeTagLibrary getTagLibrary();
  
  public MarmaladeTagInfo getTagInfo();
  
  public MarmaladeTag build() throws MarmaladeModelBuilderException;

  public void setExpressionEvaluator( ExpressionEvaluator el );

  public ExpressionEvaluator getExpressionEvaluator();
}
