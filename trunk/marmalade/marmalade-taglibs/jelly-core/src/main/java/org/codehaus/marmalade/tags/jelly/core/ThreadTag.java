/* Created on Apr 20, 2004 */
package org.codehaus.marmalade.tags.jelly.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.codehaus.marmalade.MarmaladeAttributes;
import org.codehaus.marmalade.MarmaladeExecutionContext;
import org.codehaus.marmalade.MarmaladeExecutionException;
import org.codehaus.marmalade.abstractions.AbstractMarmaladeTag;
import org.codehaus.marmalade.defaults.DefaultContext;


/**
 * @author jdcasey
 */
public class ThreadTag extends AbstractMarmaladeTag{
  
  public static final String FILE_ATTRIBUTE = "file";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String ERR_FILE_ATTRIBUTE = "errFile";
  public static final String IN_FILE_ATTRIBUTE = "inFile";

  public ThreadTag(){
  }

  protected void doExecute(MarmaladeExecutionContext context) throws MarmaladeExecutionException{
    MarmaladeAttributes attrs = getAttributes();
    
    DefaultContext ctx = new DefaultContext();
    
    Object outFile = attrs.getValue(FILE_ATTRIBUTE, ctx);
    if(outFile != null) {
      PrintWriter out = null;
      if(outFile instanceof File) {
        try{
          out = new PrintWriter(new BufferedWriter(new FileWriter((File)outFile)));
        }
        catch(IOException e){
          throw new MarmaladeExecutionException("Error opening thread\'s output file for writing.", e);
        }
      }
      else {
        try{
          out = new PrintWriter(new BufferedWriter(new FileWriter((String)outFile)));
        }
        catch(IOException e){
          throw new MarmaladeExecutionException("Error opening thread\'s output file for writing.", e);
        }
      }
      ctx.setOutWriter(out);
    }
    
    Object errFile = attrs.getValue(ERR_FILE_ATTRIBUTE, ctx);
    if(errFile != null) {
      PrintWriter err = null;
      if(errFile instanceof File) {
        try{
          err = new PrintWriter(new BufferedWriter(new FileWriter((File)errFile)));
        }
        catch(IOException e){
          throw new MarmaladeExecutionException("Error opening thread\'s error output file for writing.", e);
        }
      }
      else {
        try{
          err = new PrintWriter(new BufferedWriter(new FileWriter((String)errFile)));
        }
        catch(IOException e){
          throw new MarmaladeExecutionException("Error opening thread\'s error output file for writing.", e);
        }
      }
      ctx.setErrWriter(err);
    }
    
    Object inFile = attrs.getValue(IN_FILE_ATTRIBUTE, ctx);
    if(inFile != null) {
      BufferedReader in = null;
      if(inFile instanceof File) {
        try{
          in = new BufferedReader(new FileReader((File)inFile));
        }
        catch(FileNotFoundException e){
          throw new MarmaladeExecutionException("Error opening thread\'s input file for reading.", e);
        }
      }
      else {
        try{
          in = new BufferedReader(new FileReader((String)inFile));
        }
        catch(FileNotFoundException e){
          throw new MarmaladeExecutionException("Error opening thread\'s input file for reading.", e);
        }
      }
      ctx.setInReader(in);
    }
    
    String threadName = (String)attrs.getValue(NAME_ATTRIBUTE, String.class, context);
    Thread thread = null;
    if(threadName == null && threadName.length() > 0) {
      thread = new Thread(new ChildRunner(ctx));
    }
    else {
      thread = new Thread(new ChildRunner(ctx), threadName);
    }
    thread.setPriority(Thread.currentThread().getPriority() -1);
  }
  
  private class ChildRunner implements Runnable{
    private MarmaladeExecutionContext ctx;
    
    ChildRunner(MarmaladeExecutionContext ctx){
      this.ctx = ctx;
    }
    
    public void run() {
      try{
        processChildren(ctx);
      }
      catch(MarmaladeExecutionException e){
        e.printStackTrace(ctx.getErrWriter());
      }
    }
  }
}
