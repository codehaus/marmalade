/* Created on Mar 24, 2004 */
package org.codehaus.marmalade.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.marmalade.modelbuilder.MarmaladeTagInfo;

/** Represents base-level common functionality for all marmalade tag libraries.
 * 
 * @author John Casey
 */
public abstract class AbstractMarmaladeTagLibrary implements MarmaladeTagLibrary 
{
  
  private Map registeredTags = new TreeMap();

  protected AbstractMarmaladeTagLibrary() {
  }

  public MarmaladeTag createTag(MarmaladeTagInfo tagInfo){
    MarmaladeTag tag = null;
    
    try{
      Class tagClass = (Class)registeredTags.get(tagInfo.getElement());
      Object[] params = {tagInfo};
      Class[] paramTypes = {MarmaladeTagInfo.class};
      Constructor constructor = tagClass.getConstructor(paramTypes);
      tag = (MarmaladeTag)constructor.newInstance(params);
    }
    catch(SecurityException e){
      throw new TagInstantiationException(e);
    }
    catch(NoSuchMethodException e){
      throw new TagInstantiationException(e);
    }
    catch(IllegalArgumentException e){
      throw new TagInstantiationException(e);
    }
    catch(InstantiationException e){
      throw new TagInstantiationException(e);
    }
    catch(IllegalAccessException e){
      throw new TagInstantiationException(e);
    }
    catch(InvocationTargetException e){
      throw new TagInstantiationException(e);
    }
    
    return tag;
  }

  public void registerTag(String name, Class tagClass){
    registeredTags.put(name, tagClass);
  }
}
