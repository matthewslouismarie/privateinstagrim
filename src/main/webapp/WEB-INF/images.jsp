<%-- 
  Document   : UsersImages
  Created on : Sep 24, 2014, 2:52:48 PM
  Author   : Andy Cobley, Louis-Marie Matthews
  Page used by the servlet Image. Shouldn't be accessed directly.
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.models.ImageModel"%>
<%@page import="java.util.UUID"%>
<%@page import="java.util.Iterator"%>
<%String context = ((HttpServletRequest)request).getContextPath();%>
<%String username = (String) request.getAttribute( "username" );%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <% request.setAttribute("pageName", username ); %>
    <%@include file="/WEB-INF/jspf/commonhead.jspf" %>
  </head>
  <body>
    <%@include file="/WEB-INF/jspf/commonnav.jspf" %>
    
    <%@include file="/WEB-INF/jspf/commonheader.jspf" %>
    <main>
      <div class="container">
        <h1><%=username%></h1>
        <%
          java.util.LinkedList<java.util.UUID> uuids = (java.util.LinkedList<java.util.UUID>) request.getAttribute("images_uuid");
          if (uuids == null) {
        %>
        <p>No Image found</p>
        <%
        } else {
          Iterator<java.util.UUID> iterator;
          iterator = uuids.iterator();
          while (iterator.hasNext()) {
            java.util.UUID uuid = (java.util.UUID) iterator.next();

        %>
        <h3><%=ImageModel.getTitle(uuid)%></h3>
        <p><a href="<%=((HttpServletRequest)request).getContextPath()%>/image/<%=uuid%>" ><img src="<%=((HttpServletRequest)request).getContextPath()%>/thumb/<%=uuid%>"></a></p>
        <%
          if ( ImageModel.isOwner(uuid, LoggedIn.getUsername(request))) { %>
        <p><a href="<%=((HttpServletRequest)request).getContextPath()%>/edit-image/<%=uuid%>">Edit image</a></p>
       <% }
            }
          }
        %>
      </div>
    </main>
    
    <%@include file="/WEB-INF/jspf/commonfooter.jspf" %>
  </body>
</html>
