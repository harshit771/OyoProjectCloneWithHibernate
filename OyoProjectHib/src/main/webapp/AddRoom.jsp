<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="get" action="AddRoom.jsp">
  Room Type:
  <input type="text" name="type"><br/><br/>
  Cost Per Day:
  <input type="text" name="costperday"><br/><br/>
  <input type="submit" value="Add"><br/>
</form>
<c:if test="${param.costperday != null }">

<jsp:useBean id="room" class="infinite.oyoproject.Room" />
<jsp:setProperty property="*" name="room"/>
<jsp:useBean id="dao" class="infinite.oyoproject.OyoDAO" />
<c:out value="${dao.addRoom(room)}" />
</c:if>
</body>
</html>