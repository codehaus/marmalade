/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import org.apache.commons.jelly.Jelly;
import org.apache.commons.jelly.TagLibrary;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTagLibrary;
import org.codehaus.marmalade.model.MarmaladeTag;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author jdcasey
 */
public class JellyCompatMarmaladeTaglib extends AbstractMarmaladeTagLibrary
{
    public static final String JELLY_NATIVE_TAGLIBS_DEFINITION = "jelly.properties";
    private Properties natives = new Properties(  );
    private Map taglibCache = new TreeMap(  );

    public JellyCompatMarmaladeTaglib(  )
    {
        loadJellyNativeTaglibs(  );
    }

    private void loadJellyNativeTaglibs(  )
    {
        InputStream stream = null;

        try
        {
            stream = Jelly.class.getResourceAsStream( JELLY_NATIVE_TAGLIBS_DEFINITION );

            if ( stream != null )
            {
                natives.load( stream );
            }
        }
        catch ( IOException e )
        {
            throw new JellyCompatUncheckedException( e );
        }
        finally
        {
            if ( stream != null )
            {
                try
                {
                    stream.close(  );
                }
                catch ( IOException e )
                {
                    throw new JellyCompatUncheckedException( e );
                }
            }
        }
    }

    public MarmaladeTag createTag( MarmaladeTagInfo tagInfo )
    {
        return new JellyCompatMarmaladeTag( this );
    }

    public void registerTagLibrary( String name, TagLibrary taglib )
    {
        taglibCache.put( name, taglib );
    }

    public void registerTagLibrary( String name, String taglibClass )
    {
        natives.setProperty( name, taglibClass );
    }

    public TagLibrary getTagLibrary( String name )
    {
        TagLibrary tlib = null;

        synchronized ( this )
        {
            tlib = ( TagLibrary ) taglibCache.get( name );

            if ( tlib == null )
            {
                try
                {
                    String tlibClass = natives.getProperty( name );

                    if ( ( tlibClass == null ) || ( tlibClass.length(  ) < 1 ) )
                    {
                        tlibClass = name;
                    }

                    Class tlibCls = Class.forName( tlibClass );

                    tlib = ( TagLibrary ) tlibCls.newInstance(  );
                    taglibCache.put( name, tlib );
                }
                catch ( ClassNotFoundException e )
                {
                    throw new JellyCompatUncheckedException( e );
                }
                catch ( InstantiationException e )
                {
                    throw new JellyCompatUncheckedException( e );
                }
                catch ( IllegalAccessException e )
                {
                    throw new JellyCompatUncheckedException( e );
                }
            }
        }

        return tlib;
    }

    public void registerTag( String name, Class tagClass )
    {
        throw new UnsupportedOperationException( 
            "This is not an open tag library, and cannot have new tags registered to it." );
    }
}
