/* Created on Jun 29, 2004 */
package org.codehaus.marmalade.reader.xml;

import org.codehaus.marmalade.el.ExpressionEvaluator;
import org.codehaus.marmalade.el.ExpressionEvaluatorFactory;
import org.codehaus.marmalade.metamodel.DefaultRawAttributes;
import org.codehaus.marmalade.metamodel.MarmaladeTagBuilder;
import org.codehaus.marmalade.metamodel.MarmaladeTagInfo;
import org.codehaus.marmalade.metamodel.MarmaladeTagLibrary;
import org.codehaus.marmalade.metamodel.MarmaladeTaglibResolver;
import org.codehaus.marmalade.metamodel.ScriptBuilder;
import org.codehaus.marmalade.metamodel.TaglibResolutionException;
import org.codehaus.marmalade.model.MarmaladeControlDefinitions;
import org.codehaus.marmalade.parsing.MarmaladeParsingContext;
import org.codehaus.marmalade.parsing.ParserHint;
import org.codehaus.marmalade.reader.ScriptReadException;
import org.codehaus.marmalade.reader.ScriptReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jdcasey
 */
public class Xpp3ScriptReader
    implements ScriptReader
{
    public static final Pattern EL_PI_PATTERN = Pattern.compile( MarmaladeControlDefinitions.MARMALADE_EL_PI + " (.+)" );

    public Xpp3ScriptReader()
    {
    }

    public ScriptBuilder readScript( MarmaladeParsingContext context ) throws ScriptReadException, IOException
    {
        try
        {
            validateParsingContext( context );

            XmlPullParserFactory xpp3Factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xpp3Factory.newPullParser();

            parser.setFeature( XmlPullParser.FEATURE_PROCESS_NAMESPACES, true );

            parser.setInput( context.getInput() );

            MarmaladeTagBuilder currentBuilder = null;
            ParserHint currentParserHint = null;

            ExpressionEvaluatorFactory factory = context.getExpressionEvaluatorFactory();
            ExpressionEvaluator defaultEl = context.getDefaultExpressionEvaluator();

            int eventType = parser.getEventType();

            while ( eventType != XmlPullParser.END_DOCUMENT )
            {
                if ( eventType == XmlPullParser.START_TAG )
                {
                    if ( (currentParserHint != null) && !currentParserHint.parseChildren() )
                    {
                        context.getInput().startRecording();
                    }
                    else
                    {
                        MarmaladeTagBuilder parent = null;

                        if ( currentBuilder != null )
                        {
                            parent = currentBuilder;
                        }

                        MarmaladeTagInfo tagInfo = new MarmaladeTagInfo();

                        currentBuilder = new MarmaladeTagBuilder();

                        currentBuilder.startParsing( context );

                        if ( parent != null )
                        {
                            currentBuilder.setParent( parent );
                            parent.addChild( currentBuilder );
                        }

                        String ns = parser.getNamespace();
                        int colonIdx = ns.indexOf( ":" );
                        String scheme = (colonIdx > 0) ? (ns.substring( 0, colonIdx )) : (null);
                        String taglib = (colonIdx > 0) ? (ns.substring( colonIdx + 1 )) : (ns);

                        tagInfo.setElement( parser.getName() );
                        tagInfo.setScheme( scheme );
                        tagInfo.setTaglib( taglib );
                        tagInfo.setPrefix( parser.getPrefix() );
                        tagInfo.setSourceLine( parser.getLineNumber() );
                        tagInfo.setSourceColumn( parser.getColumnNumber() );
                        tagInfo.setSourceFile( context.getInputLocation() );

                        DefaultRawAttributes attributes = new DefaultRawAttributes();
                        int attributeCount = parser.getAttributeCount();

                        for ( int i = 0; i < attributeCount; i++ )
                        {
                            String aPrefix = parser.getAttributePrefix( i );
                            String aNamespace = parser.getAttributeNamespace( i );
                            String aName = parser.getAttributeName( i );
                            String aValue = parser.getAttributeValue( i );

                            attributes.addAttribute( aPrefix, aNamespace, aName, aValue );

                            if ( MarmaladeControlDefinitions.MARMALADE_RESERVED_NS.equals( aNamespace )
                                && MarmaladeControlDefinitions.EXPRESSION_EVALUATOR_ATTRIBUTE.equals( aName ) )
                            {
                                defaultEl = factory.getExpressionEvaluator( aValue );
                            }
                        }

                        currentBuilder.setAttributes( attributes );

                        currentBuilder.setTagInfo( tagInfo );

                        MarmaladeTaglibResolver resolver = context.getTaglibResolver();
                        MarmaladeTagLibrary tagLibrary = resolver.resolve( scheme, taglib );

                        currentParserHint = tagLibrary.getParserHint( parser.getName() );

                        currentBuilder.setTagLibrary( tagLibrary );
                        currentBuilder.setExpressionEvaluator( defaultEl );
                    }
                }
                else if ( eventType == XmlPullParser.TEXT )
                {
                    if ( (currentParserHint != null) && currentParserHint.parseChildren() )
                    {
                        if ( currentBuilder != null )
                        {
                            currentBuilder.addText( parser.getText() );
                        }
                    }
                }
                else if ( eventType == XmlPullParser.END_TAG )
                {
                    if ( currentBuilder != null )
                    {
                        if ( (currentParserHint != null) && !currentParserHint.parseChildren() )
                        {
                            String body = context.getInput().getRecordedInput();

                            currentBuilder.addText( body );
                        }

                        MarmaladeTagBuilder parent = currentBuilder.getParent();

                        if ( parent != null )
                        {
                            currentBuilder = parent;

                            MarmaladeTagInfo tagInfo = parent.getTagInfo();
                            MarmaladeTagLibrary tagLibrary = parent.getTagLibrary();

                            currentParserHint = tagLibrary.getParserHint( tagInfo.getElement() );
                        }
                    }
                }
                else if ( eventType == XmlPullParser.PROCESSING_INSTRUCTION )
                {
                    String piText = parser.getText();
                    Matcher matcher = EL_PI_PATTERN.matcher( piText );

                    if ( matcher.matches() )
                    {
                        String elType = matcher.group( 1 );

                        defaultEl = factory.getExpressionEvaluator( elType );
                    }
                }

                eventType = parser.nextToken();
            }

            ScriptBuilder builder = new ScriptBuilder( context.getInputLocation(), currentBuilder );

            return builder;
        }
        catch ( XmlPullParserException e )
        {
            throw new ScriptReadException( e );
        }
        catch ( TaglibResolutionException e )
        {
            throw new ScriptReadException( e );
        }
    }

    private void validateParsingContext( MarmaladeParsingContext context )
    {
        if ( context.getInput() == null )
        {
            throw new IllegalStateException( "context input cannot be null" );
        }
        else if ( !context.getTaglibResolver().hasStrategies() )
        {
            context.addTaglibDefinitionStrategies( MarmaladeTaglibResolver.DEFAULT_STRATEGY_CHAIN );
        }
    }
}