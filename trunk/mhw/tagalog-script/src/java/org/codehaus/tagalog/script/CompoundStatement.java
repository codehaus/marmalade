/*
 * $Id$
 */

package org.codehaus.tagalog.script;

/**
 * A list of statements. Statement lists are built by calling the
 * {@link #addStatement} method repeatedly, adding each statement
 * in the order they should be executed. Once the list of statements
 * is complete the {@link #closeStatementList} method should be called.
 * <code>addStatement</code> should not be called after
 * <code>closeStatementList</code> has been called.
 * {@link #execute} should not be called before
 * <code>closeStatementList</code> has been called.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public interface CompoundStatement extends Statement {
    void addStatement(Statement statement);

    void closeStatementList();
}
