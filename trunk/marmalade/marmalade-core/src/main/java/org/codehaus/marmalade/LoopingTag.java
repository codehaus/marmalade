/* Created on Apr 18, 2004 */
package org.codehaus.marmalade;

/**
 * @author jdcasey
 */
public interface LoopingTag{

  public abstract void breakLoop();

  public abstract void continueLoop();
}