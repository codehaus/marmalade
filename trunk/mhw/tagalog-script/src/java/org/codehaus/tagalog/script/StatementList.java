/*
 * $Id$
 */

package org.codehaus.tagalog.script;

import java.util.Iterator;
import java.util.List;

/**
 * <code>StatementList</code> implements basic grouping of statements.
 * The interface implies a two-phase construction process: first
 * statements are add to the group by calling {@link #addStatement},
 * then the list of statements is closed by calling
 * {@link #closeStatementList}.
 *
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class StatementList {
    private List statementList = new java.util.ArrayList();

    protected Statement[] statements;

    public void addStatement(Statement statement) {
        if (statementList == null)
            throw new IllegalStateException("addStatement called after"
                                            +" statement list completed");
        statementList.add(statement);
    }

    public void closeStatementList() {
        if (statementList == null)
            throw new IllegalStateException("statement list already closed");
        statements = (Statement[]) statementList.toArray(Statement.EMPTY_ARRAY);
        statementList = null;
    }

    public Statement[] getStatementList() {
        return statements;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("{ ");
        if (statementList != null) {
            Iterator i = statementList.iterator();
            while (i.hasNext()) {
                Statement stmt = (Statement) i.next();
                buf.append(stmt);
                buf.append("; ");
            }
        } else if (statements != null) {
            for (int i = 0; i < statements.length; i++) {
                buf.append(statements[i]);
                buf.append("; ");
            }
        }
        buf.append('}');
        return buf.toString();
    }
}
