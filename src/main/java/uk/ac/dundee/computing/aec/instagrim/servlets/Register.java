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
package uk.ac.dundee.computing.aec.instagrim.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.instagrim.exception.NoUseableSessionException;
import uk.ac.dundee.computing.aec.instagrim.exception.UsernameNotAsciiException;
import uk.ac.dundee.computing.aec.instagrim.exception.UsernameTakenException;
import uk.ac.dundee.computing.aec.instagrim.models.User;

/**
 *
 * @author Andy Cobley, Louis-Marie Matthews
 * @version 1.0.1
 */
public class Register extends HttpServlet
{
  public void init(ServletConfig config)
    throws ServletException
  {
    // TODO Auto-generated method stub
  }
  
  
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    request.setAttribute( "already_treated", true );
    System.out.println("Register#doGet(…): called.");
    RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
    rd.forward(request, response);
  }
  
  
  
  /**
   * Handles the registration process once the user submits the desired
   * credentials of their future account.
   * TODO: More verification should be done (does one user already have this 
   * username?). It is currently possible to change anyone's password provided 
   * that you know their username.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    request.setAttribute( "already_treated", true );
    
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    
    try {
      User.registerUser(username, password);
    } catch (NoUseableSessionException e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
      return;
    } catch (UsernameNotAsciiException e) {
      request.setAttribute( "already_treated", true );
      RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
      request.setAttribute("details_error", "The username must be an ASCII-US string.");
      rd.forward(request, response);
      return;
    } catch (UsernameTakenException e) {
      request.setAttribute( "already_treated", true );
      RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
      request.setAttribute("details_error", "The username already exists.");
      rd.forward(request, response);
      return;
    }
    
    request.getSession().setAttribute( "confirmation_message", "You have sucessfully created an account.");
    response.sendRedirect(((HttpServletRequest)request).getContextPath() );
  }
}