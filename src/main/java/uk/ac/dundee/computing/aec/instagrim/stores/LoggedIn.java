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
package uk.ac.dundee.computing.aec.instagrim.stores;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Objects of this class are used to store authentification information in the 
 * session.
 * 
 * @author Andy Cobley, Louis-Marie Matthews
 * @version 1.0.1
 */
public class LoggedIn
{
  private boolean loggedIn;
  private String username;
  
  
  
  public LoggedIn(boolean loggedIn, String username)
  {
    this.loggedIn = loggedIn;
    this.username = username;
  }
  
  
  
  public String getUsername()
  {
    return username;
  }
  
  
  
  public boolean isLoggedIn()
  {
    return loggedIn;
  }
  
  
  
  public void logIn()
  {
    loggedIn = true;
  }
  
  
  
  public void logOut() {
    loggedIn = false;
  }
  
  
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  
  
  public static String getUsername(ServletRequest request) {
    LoggedIn lg = (LoggedIn) ((HttpServletRequest)request).getSession()
      .getAttribute("LoggedIn");
    return lg.getUsername();
  }
  
  
  
  public static boolean isLoggedIn(ServletRequest request) {
    LoggedIn lg = (LoggedIn) ((HttpServletRequest)request).getSession()
      .getAttribute("LoggedIn");
    if ( lg != null ) {
      return lg.isLoggedIn();
    }
    return false;
  }
  
  
  
  public static void logOut(ServletRequest request) {
    LoggedIn lg = (LoggedIn) ((HttpServletRequest)request).getSession()
      .getAttribute("LoggedIn");
    if ( lg != null ) {
      lg.logOut();
    }
  }
}