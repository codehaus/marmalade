/* Created on Jul 1, 2004 */
package org.codehaus.marmalade.model;

/**
 * @author jdcasey
 */
public final class MarmaladeControlDefinitions {

    public static final String MARMALADE_RESERVED_NS = "marmalade-control";
    public static final String EXPRESSION_EVALUATOR_ATTRIBUTE = "el";
    
    public static final String MARMALADE_EL_PI = MARMALADE_RESERVED_NS + ":" + EXPRESSION_EVALUATOR_ATTRIBUTE;
    
    /** control attribute used as the default flag to preserve body
     * whitespace.
     */
    public static final String PRESERVE_BODY_WHITESPACE_ATTRIBUTE = "marmalade:preserve-whitespace";
    
    /** Constants class; deny construction
     */
    private MarmaladeControlDefinitions() {
    }

}
