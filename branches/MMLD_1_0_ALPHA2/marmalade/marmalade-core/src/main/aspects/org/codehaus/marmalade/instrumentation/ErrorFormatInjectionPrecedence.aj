/* Created on Aug 10, 2004 */
package org.codehaus.marmalade;


/**
 * @author jdcasey
 */
public aspect ErrorFormatInjectionPrecedence {
    
    declare precedence: *, SourceLineMetaDataInjector, ResourceBundleErrorMessageInjector;

}
