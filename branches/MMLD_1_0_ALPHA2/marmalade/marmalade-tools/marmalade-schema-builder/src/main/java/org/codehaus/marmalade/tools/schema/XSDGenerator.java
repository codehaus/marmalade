// TODO Attach license header here.
package org.codehaus.marmalade.tools.schema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.marmalade.util.RegexSupport;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;

/**
 * @author jdcasey
 *
 * Created on Jan 4, 2005
 */
public class XSDGenerator
{
    
    private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    
    private static final String SCHEMA_ELEMENT = "schema";
    private static final String ELEMENT_ELEMENT = "element";
    private static final String ATTRIBUTE_ELEMENT = "attribute";
    
    private static final String NAME_ATTRIBUTE = "name";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String TYPE_ATTRIBUTE = "type";
    
    private static final String ELEMENT_JAVADOC_TAG = "element";
    private static final String NAME_JAVADOC_ATTR = "name";
    
    private static final String ATTRIBUTE_JAVADOC_TAG = "attribute";
    private static final String REQUIRED_JAVADOC_ATTR = "required";
    
    
    private String srcPath;
    private List includes = new ArrayList();
    private List excludes = new ArrayList();
    private String tagLibraryName;
    private String outputDirectory;

    public void setSourcePath(String srcPath)
    {
        this.srcPath = srcPath;
    }
    
    public void addInclude(String includePattern)
    {
        if(!includes.contains(includePattern))
        {
            includes.add(includePattern);
        }
    }
    
    public void setIncludes(List includes)
    {
        this.includes = includes;
    }
    
    public void addExclude(String excludePattern)
    {
        if(!excludes.contains(excludePattern))
        {
            excludes.add(excludePattern);
        }
    }
    
    public void setExcludes(List excludes)
    {
        this.excludes = excludes;
    }
    
    public void setTagLibraryName(String tagLibraryName)
    {
        this.tagLibraryName = tagLibraryName;
    }
    
    public void setOutputDirectory(String outputDirectory)
    {
        this.outputDirectory = outputDirectory;
    }
    
    public void generateSchema() throws IOException
    {
        JavaDocBuilder jdBuilder = JavaDocBuilder.load(new File(srcPath));
        
        XmlPullParserFactory xppFactory = null;
        try
        {
            xppFactory = XmlPullParserFactory.newInstance();
        }
        catch ( XmlPullParserException e )
        {
            throw new RuntimeException("This exception should be better handled in code.", e);
        }
        
        XmlSerializer serializer = null;
        try
        {
            serializer = xppFactory.newSerializer();
        }
        catch ( XmlPullParserException e )
        {
            throw new RuntimeException("This exception should be better handled in code.", e);
        }
        
        File dir = new File(outputDirectory);
        File xsd = new File(dir, tagLibraryName + ".xsd");
        
        BufferedWriter xsdOut = new BufferedWriter(new FileWriter(xsd));
        
        serializer.setOutput(xsdOut);
        
        serializer.startTag(XSD_NAMESPACE, "schema");
        
        JavaClass[] classes = jdBuilder.getClasses();
        
        String includeUberPattern = RegexSupport.buildUberClassPattern(includes);
        String excludeUberPattern = RegexSupport.buildUberClassPattern(excludes);
        
        for ( int i = 0; i < classes.length; i++ )
        {
            JavaClass currentClass = classes[i];
            String fqName = currentClass.getFullyQualifiedName();
            
            boolean include = false;
            
            if(fqName.matches(includeUberPattern))
            {
                include = true;
            }
            
            if(fqName.matches(excludeUberPattern))
            {
                include = false;
            }
            
            if(include)
            {
                generateSchemaForClass(currentClass, serializer);
            }
        }
    }

    private void generateSchemaForClass( JavaClass currentClass, XmlSerializer serializer )
    {
        
    }

}
