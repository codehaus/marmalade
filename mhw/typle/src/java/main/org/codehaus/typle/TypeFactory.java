/*
 * $Id$
 */

package org.codehaus.typle;

/**
 * Factory for {@link Type} objects.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public interface TypeFactory {
    Type lookup(String name);
}
