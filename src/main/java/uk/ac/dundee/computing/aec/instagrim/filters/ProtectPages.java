/*
 * The java code follows the Java Programming Style Guidelines 7.0 from 
 * Geotechnical Software Services available at this address:
 * http://geosoft.no/development/javastyle.html .
 * Some rules are still not applied yet.
 * However, some rules won't be followed:
 * 1. No underscore suffix at the end of private variables (r8)
 * 2. No space between a function and its parenthesis (r74). Instead, parenthesis
 * may be wrapped around space. So function ( parameter ) instead of 
 * function (parameter).
 * 4. Abbreviations and the use of init is okay (r17, r24)
 * 5. Statements and variable declarations don't need to be aligned (r77, r78)
 * 6. Class names don't have to be nouns (would make some class' names long and
 * poorly representative for servlets and filters).
 */
package uk.ac.dundee.computing.aec.instagrim.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 * @author Andy Cobley, Louis-Marie Matthews
 * @version 1.0.1
 */
public class ProtectPages
  implements Filter
{
  private static final boolean DEBUG = true;

  // The filter configuration object we are associated with.  If
  // this value is null, this filter instance is not currently
  // configured.
  private FilterConfig filterConfig;
  
  
  
  public ProtectPages() {
    filterConfig = null;
  }
  
  
  
  /**
   * Init method for this filter
   */
  public void init(FilterConfig filterConfig)
  {
    this.filterConfig = filterConfig;
    if (filterConfig != null && DEBUG) {
      log("ProtectPages:Initializing filter");
    }
  }
  
  
  
  /**
   *
   * @param request The servlet request we are processing
   * @param response The servlet response we are creating
   * @param chain The filter chain we are processing
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet error occurs
   */
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain)
    throws IOException, ServletException
  {
    System.out.println( "ProtectPages#doFilter(…) : called." );
    if (DEBUG) {
      log("ProtectPages:doFilter()");
    }
    if ( ! LoggedIn.isLoggedIn( request ) ) {
      HttpServletRequest hr = (HttpServletRequest) request;
      System.out.println( "ProtectPages#doFilter(…) : not logged in, forwarding to login." );
      request.setAttribute( "error_message", "You need to be logged-in to access this content." );
      request.setAttribute( "previous_page", hr.getRequestURL().toString() );
      RequestDispatcher rd = request.getRequestDispatcher( "/login/" );
      // rd.forward(request, response);
      ((HttpServletResponse)response).sendRedirect( ((HttpServletRequest)request).getContextPath() + "/login" );
      // doAfterProcessing(request, response);
      return;
    }
    System.out.println( "ProtectPages#doFilter(…) : logged in, continuing request." );
    try {
      chain.doFilter(request, response);
    } catch (IOException | ServletException exception) {
      sendError(exception, (HttpServletResponse) response);
    }
  }
  
  
  
  private void sendError(Throwable throwable, HttpServletResponse response) throws IOException
  {
    System.out.println (getStackTrace (throwable));
    response.reset();
    response.sendError (HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
  }
  
  
  
  public static String getStackTrace(Throwable t)
  {
    PrintWriter pw = new PrintWriter( new StringWriter());
    String stackTrace = null;
    try {
      t.printStackTrace(pw);
      stackTrace = pw.toString();
    }
    finally {
      pw.close();
    }
    return stackTrace;
  }
  
  
  
  public void log(String msg)
  {
    filterConfig.getServletContext().log(msg);
  }
  
  
  
  private void doBeforeProcessing (ServletRequest request, ServletResponse response)
    throws IOException, ServletException
  {
    if (DEBUG) {
      log("ProtectPages:DoBeforeProcessing");
    }

    // Write code here to process the request and/or response before
    // the rest of the filter chain is invoked.
    // For example, a logging filter might log items on the request object,
    // such as the parameters.
    /*
     for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
     String name = (String)en.nextElement();
     String values[] = request.getParameterValues(name);
     int n = values.length;
     StringBuffer buf = new StringBuffer();
     buf.append(name);
     buf.append("=");
     for(int i=0; i < n; i++) {
     buf.append(values[i]);
     if (i < n-1)
     buf.append(",");
     }
     log(buf.toString());
     }
     */
  }
  
  
  
  private void doAfterProcessing(ServletRequest request, ServletResponse response)
    throws IOException, ServletException
  {
    if (DEBUG) {
      log("ProtectPages:DoAfterProcessing");
    }

    // Write code here to process the request and/or response after
    // the rest of the filter chain is invoked.
    // For example, a logging filter might log the attributes on the
    // request object after the request has been processed. 
    /*
     for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
     String name = (String)en.nextElement();
     Object value = request.getAttribute(name);
     log("attribute: " + name + "=" + value.toString());

     }
     */
    // For example, a filter might append something to the response.
    /*
     PrintWriter respOut = new PrintWriter(response.getWriter());
     respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
     */
  }
  
  
  
  /**
   * Destroy method for this filter
   */
  public void destroy()
  {
  }
  
  
  
  /**
   * Return a String representation of this object.
   */
  @Override
  public String toString()
  {
    if (filterConfig == null) {
      return ("ProtectPages()");
    }
    StringBuilder sb = new StringBuilder("ProtectPages(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }
}