package org.codehaus.marmalade.reader;

import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;

import java.io.IOException;

/**
 * @author jdcasey
 */
public interface ScriptReader
{

    public ScriptBuilder readScript( MarmaladeParsingContext context ) throws ScriptReadException, IOException;
    
}
