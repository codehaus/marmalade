/* Created on May 18, 2004 */
package org.codehaus.marmalade.modelbuilder.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.codehaus.marmalade.model.MarmaladeTagLibrary;


/**
 * @author jdcasey
 */
public class PrefixedDefFileDefinitionStrategy implements TaglibDefinitionStrategy{

  public PrefixedDefFileDefinitionStrategy(){
  }

  public MarmaladeTagLibrary resolve(String prefix, String taglib){
    ClassLoader cloader = getClass().getClassLoader();
    MarmaladeTagLibrary tlib = null;
    
    InputStream defStream = null;
    try{
      defStream = cloader.getResourceAsStream("META-INF/" + prefix + "/" + taglib + ".def");
      if(defStream != null) {
        String className = null;
        try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(defStream));
          className = reader.readLine();
          reader.close();
        }
        // Ignore this and return null.
        catch(IOException e ) {}
        
        if(className != null && className.length() > 0) {
          try {
            Class tlCls = cloader.loadClass(className);
            if(MarmaladeTagLibrary.class.isAssignableFrom(tlCls)) {
              tlib = (MarmaladeTagLibrary)tlCls.newInstance();
            }
          }
          // Ignore these, and return null.
          catch (InstantiationException e) {}
          catch (IllegalAccessException e) {}
          catch (ClassNotFoundException e) {}
        }
      }
    }
    // Return null.
    finally {
      if(defStream != null) {
        try {defStream.close();} catch(IOException e) {}
      }
    }
    
    return tlib;
  }

}
