package org.codehaus.marmalade.tags.lang;

import org.codehaus.marmalade.model.AbstractMarmaladeTag;
import org.codehaus.marmalade.model.MarmaladeTag;
import org.codehaus.marmalade.runtime.MarmaladeExecutionContext;
import org.codehaus.marmalade.runtime.MarmaladeExecutionException;
import org.codehaus.marmalade.runtime.TagExecutionException;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author jdcasey
 *
 * Created on Feb 7, 2005
 */
public class IdTag
    extends AbstractMarmaladeTag
{
    
    public static final String VALUE_ATTR = "value";
    
    private static Map idsOnSystem = new WeakHashMap();

    protected void doExecute( MarmaladeExecutionContext context )
        throws MarmaladeExecutionException
    {
        String id = (String) getAttributes().getValue(VALUE_ATTR, String.class, context);
        if(id == null || id.trim().length() < 1)
        {
            id = (String)getBody(context, String.class);
        }
        
        if(id == null || id.trim().length() < 1)
        {
            throw new TagExecutionException(getTagInfo(), "You must specify either the \'" + VALUE_ATTR + "\' attribute or a tag-body for the id tag.");
        }
        
        if(idsOnSystem.get(id) != null)
        {
            throw new TagExecutionException(getTagInfo(), "You cannot have two script entities labelled with id: \'" + id + "\'.");
        }
        
        idsOnSystem.put(id, new WeakReference(id));
        
        MarmaladeTag parent = getParent();
        if(parent != null && (parent instanceof IdTagParent))
        {
            ((IdTagParent)parent).setId(id);
        }
    }
    
}
