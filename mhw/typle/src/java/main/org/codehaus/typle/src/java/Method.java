/*
 * $Id$
 */

package org.codehaus.typle.src.java;

import java.io.IOException;

import org.codehaus.typle.BindingList;
import org.codehaus.typle.Function;
import org.codehaus.typle.src.AbstractSourceArtefact;
import org.codehaus.typle.src.SourceArtefact;
import org.codehaus.typle.src.SourceFileWriter;

/**
 * @author Mark H. Wilkinson
 * @version $Revision$
 */
public final class Method
    extends AbstractSourceArtefact
    implements SourceArtefact
{
    private final Modifier[] modifiers;

    private final String name;

    private final Function signature;

    private final String body;

    private static final Modifier[] DEFAULT_MODIFIERS = new Modifier[] {
        Modifier.PUBLIC,
    };

    public Method(String name, Function signature, String body) {
        this(DEFAULT_MODIFIERS, name, signature, body);
    }

    public Method(Modifier[] modifiers, String name, Function signature,
        String body)
    {
        this.modifiers = modifiers;
        this.name = name;
        this.signature = signature;
        this.body = body;
    }

    public String getMethodName() {
        return name;
    }

    public void write(SourceFileWriter writer) throws IOException {
        String mods = Modifier.toString(modifiers);
        String space = (mods.length() == 0)? "" : " ";
        writer.print(mods + space);
        writer.println(formatSignature(name, signature) + " {");
        writer.println(body);
        writer.println("}");
    }

    private String formatSignature(String name, Function signature) {
        BindingList parameters = signature.getFormalParameters();
        StringBuffer buf = new StringBuffer();
        buf.append(signature.getReturnType().getTypeName());
        buf.append(' ');
        buf.append(name);
        buf.append('(');
        buf.append(signature.getFormalParameters().signature());
        buf.append(')');
        return buf.toString();
    }
}
