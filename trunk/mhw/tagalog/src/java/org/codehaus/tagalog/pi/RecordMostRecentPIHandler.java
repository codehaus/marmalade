/*
 * $Id$
 */

package org.codehaus.tagalog.pi;

import java.util.Map;

/**
 * A processing instruction handler that records the most recent data
 * provided to each processing instruction target in a document.
 * Each processing instruction target results in a context mapping from a
 * {@link PIHashKey} to a {@link List} of <code>String</code> data values.
 * Each processing instruction target results in a context mapping from a
 * {@link PIHashKey} to the most recent <code>String</code> data value.
 * Subsequent processing instructions with the same target will
 * replace earlier ones.
 *
 * <p>
 * The approach of using <code>PIHashKey</code> values to distinguish
 * processing instructions from other data in the context <code>Map</code>
 * requires that the context is held in a <code>Map</code>
 * that supports keys of different types, such as {@link HashMap}.
 * It does not work correctly with {@link TreeMap} because the keys are
 * not comparable to each other.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class RecordMostRecentPIHandler implements PIHandler {
    public static final RecordMostRecentPIHandler INSTANCE = new RecordMostRecentPIHandler();

    /**
     * Protected constructor to prevent instantiation but allow
     * extension.
     */
    protected RecordMostRecentPIHandler() {
    }

    public void processingInstruction(String target, String data, Map context) {
        context.put(new PIHashKey(target), data);
    }
}
