/*
 * $Id$
 */

package org.codehaus.tagalog;

/**
 * A simple value object representing a location in a source document.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public class Location {
    private final String filename;

    private final int line;

    private final int column;

    /**
     * Create a location object.
     *
     * @param filename Filename of the source document, or <code>null</code>
     * if unknown.
     * @param line Line number within the source document, or -1 if unknown.
     * @param column Column number within the source document, or -1 if
     * unknown.
     *
     * @throws IllegalArgumentException In the case where no line information
     * is provided, but a column number is provided.
     */
    public Location(String filename, int line, int column) {
        if (line == -1 && column != -1)
            throw new IllegalArgumentException("column specified but no line");
        this.filename = filename;
        this.line = line;
        this.column = column;
    }

    /**
     * Return the filename.
     * @return Returns the filename, or <code>null</code> if the filename is
     * unknown.
     */
    public final String getFilename() {
        return filename;
    }

    /**
     * Return the line number, or -1 if the line number is unknown.
     * @return Returns the line number, or -1 if the line number is unknown.
     */
    public final int getLine() {
        return line;
    }

    /**
     * Return the column, or -1 if the column number is unknown.
     * @return Returns the column, or -1 if the column number is unknown.
     */
    public final int getColumn() {
        return column;
    }

    /**
     * Convert the location into a suitable string representation.
     * String returned contains the filename, line number and column
     * number separated by <code>:</code> characters.
     * If the filename was <code>null</code> it is omitted.
     * If the line number or column number were unknown they are
     * omitted.
     * In the worst case it is possible for this method to return the
     * empty string.
     *
     * @return String representation of the location.
     */
    public String toString() {
        StringBuffer b = new StringBuffer();

        if (filename != null) {
            b.append(filename);
            if (line != -1)
                b.append(':');
        }
        if (line != -1)
            b.append(line);
        if (column != -1) {
            b.append(':');
            b.append(column);
        }
        return b.toString();
    }
}
