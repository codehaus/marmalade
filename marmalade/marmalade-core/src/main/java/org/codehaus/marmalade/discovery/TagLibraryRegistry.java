package org.codehaus.marmalade.discovery;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.monitor.log.CommonLogLevels;
import org.codehaus.marmalade.monitor.log.MarmaladeLog;
import org.codehaus.marmalade.tags.passthrough.PassThroughTagLibrary;

public class TagLibraryRegistry
    implements TaglibResolutionStrategy
{
    
    private Map taglibs = new HashMap();
    private MarmaladeTagLibrary defaultTaglib = new PassThroughTagLibrary();
    private MarmaladeLog log;
    
    public void registerTagLibrary( String prefix, String taglib, MarmaladeTagLibrary library )
    {
        taglibs.put( prefix + ":" + taglib, library );
    }
    
    public TagLibraryRegistry withRegisteredTagLibrary( String prefix, String taglib, MarmaladeTagLibrary library )
    {
        registerTagLibrary( prefix, taglib, library );
        return this;
    }
    
    public void setDefaultTagLibrary( MarmaladeTagLibrary library )
    {
        this.defaultTaglib = library;
    }
    
    public TagLibraryRegistry withDefaultTagLibrary( MarmaladeTagLibrary library )
    {
        setDefaultTagLibrary( library );
        return this;
    }

    public MarmaladeTagLibrary resolve( String prefix, String taglib, ClassLoader classloader )
    {
        String key = prefix + ":" + taglib;
        
        MarmaladeTagLibrary lib = (MarmaladeTagLibrary) taglibs.get( key );
        
        if ( lib == null )
        {
            log( CommonLogLevels.DEBUG, "Using default taglib: " + defaultTaglib + " for taglib uri: \'" + key + "\'" );
            lib = defaultTaglib;
        }
        
        return lib;
    }

    private void log( String level, String message )
    {
        if ( log != null )
        {
            log.log( level, message );
        }
    }

    public void setLog( MarmaladeLog log )
    {
        this.log = log;
    }

}
