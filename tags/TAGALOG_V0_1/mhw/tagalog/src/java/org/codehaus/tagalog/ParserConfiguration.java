/*
 * $Id$
 */

package org.codehaus.tagalog;

import java.util.Map;

import org.codehaus.tagalog.pi.PIHandler;

/**
 * The collection of information that defines the behaviour of a parser.
 * Instances of this class can be shared by many parser instances, reducing
 * the overhead of setting up a parser. The parser configuration specifies
 * how a parser should process XML elements during the parse; it does not
 * specify anything about how the parser is connected to a source of
 * XML parse events such as a SAX parser.
 *
 * <p>
 * The typical use of this class would be something like:
 * <pre>
 * config = new ParserConfiguration();
 * config.addTagLibrary(PeopleTagLibrary.NS_URI,
 *                      new PeopleTagLibrary());
 * config.setDefaultNamespace("tagalog:people");
 * factory = new TagalogSAXParserFactory(config);
 * </pre>
 * <code>factory</code> can now be used to create any number of parser
 * objects. Factory objects are thread-safe and so can be shared across
 * any number of threads; they would normally be singleton objects.
 * Once a <code>ParserConfiguration</code> has been passed to a
 * factory the factory is considered the owner of the configuration.
 * <code>ParserConfiguration</code>s should not be modified after they
 * have been used to create a factory, nor should they be shared by
 * multiple factory objects.
 *
 * @author <a href="mailto:mhw@kremvax.net">Mark Wilkinson</a>
 * @version $Revision$
 */
public final class ParserConfiguration {
    private String defaultNamespaceUri;

    private FallbackTagLibraryResolver fallbackResolver;

    private Map prefixResolvers = new java.util.TreeMap();

    private PIHandler processingInstructionHandler;

    /**
     * Construct a <code>ParserConfiguration</code> that uses
     * {@link SimpleTagLibraryResolver} as the fallback tag library
     * resolver.
     */
    public ParserConfiguration() {
        this(new SimpleTagLibraryResolver());
    }

    /**
     * Construct a <code>ParserConfiguration</code> that uses the specified
     * fallback tag library resolver.
     *
     * @param resolver The fallback tag library resolver.
     */
    public ParserConfiguration(FallbackTagLibraryResolver resolver) {
        fallbackResolver = resolver;
    }

    /**
     * Set the namespace that will be used if the document does not
     * specify a namespace itself.
     *
     * @param namespaceUri The URI of the default namespace.
     */
    public void setDefaultNamespace(String namespaceUri) {
        defaultNamespaceUri = namespaceUri;
    }

    /**
     * Return the namespace that will be used if the document does not
     * specify a namespace itself.
     *
     * @return namespaceUri The URI of the default namespace.
     */
    public String getDefaultNamespace() {
        return defaultNamespaceUri;
    }

    /**
     * Associate a tag library with a URI. Note that tag libraries that are
     * registered with the prefix-based tag library resolvers will be found
     * first.
     *
     * @param uri URI of the tag library.
     * @param tagLibrary The tag library itself.
     */
    public void addTagLibrary(String uri, TagLibrary tagLibrary) {
        if (uri.length() == 0)
            throw new IllegalArgumentException("uri is empty");
        if (tagLibrary == null)
            throw new NullPointerException("tag library is null");
        fallbackResolver.addTagLibrary(uri, tagLibrary);
    }

    /**
     * Register a <code>PrefixTagLibraryResolver</code> with the parser.
     *
     * @param resolver The tag library resolver to register.
     * @throws IllegalArgumentException if the prefix returned by the resolver
     * is empty.
     */
    public void addTagLibraryResolver(PrefixTagLibraryResolver resolver) {
        String uriPrefix = resolver.uriPrefix();
        if (uriPrefix.length() == 0)
            throw new IllegalArgumentException("resolver prefix is empty");
        prefixResolvers.put(uriPrefix, resolver);
    }

    TagLibrary findTagLibrary(String uri) {
        TagLibrary tagLibrary = null;
        int colon;

        if (uri.length() == 0) {
            if (defaultNamespaceUri != null)
                uri = defaultNamespaceUri;
            else
                throw new IllegalArgumentException("uri is empty,"
                                       + " and no default has been specified");
        }
        colon = uri.indexOf(':');
        if (colon != -1) {
            String prefix = uri.substring(0, colon);
            Object o = prefixResolvers.get(prefix);
            TagLibraryResolver resolver = (TagLibraryResolver) o;
            if (resolver != null)
                tagLibrary = resolver.resolve(uri.substring(colon + 1));
        }
        if (tagLibrary == null) {
            tagLibrary = fallbackResolver.resolve(uri);
        }
        return tagLibrary;
    }

    /**
     * Set the processing instruction handler for the parser configuration.
     * Tagalog provides some useful processing instruction handlers in the
     * {@link <a href="pi/package-summary.html">org.codehaus.tagalog.pi</a>}
     * package.
     * If this method is not called, or the parser configuration handler
     * is <code>null</code>, processing instructions are ignored.
     *
     * @param handler Processing instruction handler to use.
     */
    public void setProcessingInstructionHandler(PIHandler handler) {
        processingInstructionHandler = handler;
    }

    /**
     * Return the processing instruction handler to use for this parser
     * configuration, or <code>null</code> if none is being used.
     *
     * @return the processing instruction handler, or <code>null</code>.
     */
    public PIHandler getProcessingInstructionHandler() {
        return processingInstructionHandler;
    }
}
