/*
 * $Id$
 */

package org.codehaus.tagalog.script;

/**
 * Abstract base class implementing basic grouping of statements.
 * The interface implies a two-phase construction process: first
 * statements are add to the group by calling {@link #addStatement},
 * then the list of statements is closed by calling
 * {@link #closeStatementList}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public abstract class AbstractCompoundStatement
    implements CompoundStatement
{
    private StatementList statementList = new StatementList();

    public void addStatement(Statement statement) {
        statementList.addStatement(statement);
    }

    public void closeStatementList() {
        statementList.closeStatementList();
    }

    protected Statement[] getStatementList() {
        return statementList.getStatementList();
    }

    public String toString() {
        return statementList.toString();
    }
}
