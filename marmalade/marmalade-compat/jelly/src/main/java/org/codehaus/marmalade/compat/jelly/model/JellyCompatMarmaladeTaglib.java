/* Created on Jun 28, 2004 */
package org.codehaus.marmalade.compat.jelly.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.jelly.Jelly;
import org.apache.commons.jelly.TagLibrary;
import org.codehaus.marmalade.compat.jelly.JellyCompatUncheckedException;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.model.AbstractMarmaladeTagLibrary;
import org.codehaus.marmalade.model.MarmaladeTag;

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
        String tlibClass = natives.getProperty( tagInfo.getTaglib(  ) );

        if ( ( tlibClass == null ) || ( tlibClass.length(  ) < 1 ) )
        {
            tlibClass = tagInfo.getTaglib(  );
        }

        TagLibrary tlib = null;

        synchronized ( this )
        {
            tlib = ( TagLibrary ) taglibCache.get( tlibClass );

            if ( tlib == null )
            {
                try
                {
                    Class tlibCls = Class.forName( tlibClass );

                    tlib = ( TagLibrary ) tlibCls.newInstance(  );
                    taglibCache.put( tlibClass, tlib );
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

        return new JellyCompatMarmaladeTag( tlib );
    }

    public void registerTag( String name, Class tagClass )
    {
        throw new UnsupportedOperationException( 
            "This is not an open tag library, and cannot have new tags registered to it." );
    }
}
