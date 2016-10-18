/*
 * The java code follows the Java Programming Style Guidelines 7.0 from 
 * Geotechnical Software Services available at this address:
 * http://geosoft.no/development/javastyle.html .
 * Some rules are still not applied yet.
 * However, some rules won't be followed:
 * 1. No underscore suffix at the end of private variables (r8)
 * 2. No space between a function and its parenthesis (r74)
 * 3. Class and package names (can't be changed, lead to problems) (r3)
 * 4. Abbreviations and the use of init is okay (r17, r24)
 * 5. Statements and variable declarations don't need to be aligned (r77, r78)
 */
package uk.ac.dundee.computing.aec.instagrin.filters;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Louis-Marie Matthews
 * @version 1.0
 */
@WebFilter(
  filterName = "HiddenJsp",
  urlPatterns = {
    "/ImageNotFound.jsp",
    "/UsersPics.jsp",
  },
  dispatcherTypes = {
    DispatcherType.REQUEST,
    DispatcherType.FORWARD,
    DispatcherType.INCLUDE
  }
)
public class HiddenJsp
  implements Filter
{
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain)
    throws IOException, ServletException
  {
    ((HttpServletResponse)response).sendError(HttpServletResponse.SC_NOT_FOUND );
  }
  
  
  
  public void init(FilterConfig filterConfig) {
    
  }
  
  
  
  public void destroy() {
    
  }
}