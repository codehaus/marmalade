/*
 * $Id$
 */
package org.codehaus.typle.src.java;

import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.typle.src.SourceFilePrintWriter;
import org.codehaus.typle.src.SourceFileWriter;

/**
 * Implementation of {@link SourceFileWriter} for Java that utilises 
 * {@link java.io.PrintWriter}.
 * @author Kristopher Brown
 * @version $Revision$ $Date$
 */
public class JavaSourceFileWriter implements SourceFileWriter {
    
    private static final String EMPTY_STR = "";
    private static final String DEFAULT_INDENT = "    ";
    private static final int BUFFER_SIZE = 80;
    
    /**
     * Underlying PrintWriter to utilise
     */
    private SourceFileWriter w;
    
    /**
     * The indent
     */
    private String indent;

    /**
     * Indicates if the writer is at the very start of a new line.
     */
    private boolean newLine;
    
    /**
     * The current indent level
     */
    private int indentLevel;


    public JavaSourceFileWriter(PrintWriter writer) {
        this(writer, DEFAULT_INDENT);
    }
    
    public JavaSourceFileWriter(PrintWriter writer, String indent) {
        this(new SourceFilePrintWriter(writer), indent);
    }

    public JavaSourceFileWriter(SourceFileWriter writer) {
        this(writer, DEFAULT_INDENT);
    }
    
    public JavaSourceFileWriter(SourceFileWriter writer, String indent) {
        this.w = writer;
        this.indent = indent;

        newLine = true;
        indentLevel = 0;
    }

    public void print(String str) throws IOException {
        doPrint(str);
    }

    public void println(String str) throws IOException {
        doPrint(str);
        w.println();
        newLine = true;
    }

    public void println() throws IOException {
        doPrint(EMPTY_STR);
        w.println();
        newLine = true;
    }

    private void doPrint(String str) throws IOException {

        if (str.trim().length() == 0) {
            return;
        }

        if (str.startsWith("}")) {
            indentLevel--;
        }

        if (newLine) {
            writeIndent();
            newLine = false;
        }

        if (str.endsWith("{")) {
            indentLevel++;
        }

        w.print(str);
    }

    private void writeIndent() throws IOException {
        for (int i = 0; i < indentLevel; i++) {
            w.print(indent);
        }
    }
}
