<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<jsp:useBean id="dao" class="infinite.oyoproject.OyoDAO" />
<form action="Billing.jsp" method="get" style="text-align: center">
	Room Id:
	<select name="roomId" >
		<c:forEach var="rid" items="${dao.showBookRooms()}">
			<option value="${rid }">
			<c:out value="${rid}" />
			</option>
		</c:forEach>
	</select><br/><br/>
	<input type="submit" value="Submit" /><br/><br/>
</form>


<c:if test="${param.roomId != null }">
<form action="Billing.jsp" method="get" style="text-align: center">
		 Room Id:
		  <input type="text" name="roomId" value="${param.roomId }" /><br/><br/>
		 Book Id:
		  <input type="text" name="bookId"  /><br/><br/>
		  <input type="submit" value="Submit" /><br/><br/></form>
</c:if>

<c:if test="${param.bookId != null }">
<c:set var="id" value="${param.bookId }"/>
   
    <c:out value="${dao.billingRoom(id)}" />
</c:if>
</body>
</html>