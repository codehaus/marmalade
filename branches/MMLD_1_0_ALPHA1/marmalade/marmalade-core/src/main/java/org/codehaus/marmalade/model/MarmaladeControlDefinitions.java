/* Created on Jul 1, 2004 */
package org.codehaus.marmalade.model;


/**
 * @author jdcasey
 */
public final class MarmaladeControlDefinitions
{
    /** namespace for the marmalade control attributes and PIs. */
    public static final String MARMALADE_RESERVED_NS = "marmalade-control";

    /** control attribute to allow specification of the expression evaluator
     * via processing instruction.
     */
    public static final String EXPRESSION_EVALUATOR_ATTRIBUTE = "el";

    /** control attribute used as the default flag to preserve body
     * whitespace.
     */
    public static final String PRESERVE_BODY_WHITESPACE_ATTRIBUTE = "preserve-whitespace";

    /** control PI to allow specification of the expression evaluator
     * via processing instruction.
     */
    public static final String MARMALADE_EL_PI = MARMALADE_RESERVED_NS + ":"
        + EXPRESSION_EVALUATOR_ATTRIBUTE;

    /** Constants class; deny construction
     */
    private MarmaladeControlDefinitions(  )
    {
    }
}
