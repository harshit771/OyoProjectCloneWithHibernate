<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
$(function(){
    var dtToday = new Date();
    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
     day = '0' + day.toString();
    var maxDate = year + '-' + month + '-' + day;
    $('#chDate').attr('min', maxDate);
    $('#choutDate').attr('min', maxDate);
});

</script>
<body>
<jsp:useBean id="dao" class="infinite.oyoproject.OyoDAO" />
<form action="RoomBook.jsp" method="get" style="text-align: center">
	Room Id:
	<select name="roomId" >
		<c:forEach var="rid" items="${dao.showRooms()}">
			<option value="${rid }">
			<c:out value="${rid}" />
			</option>
		</c:forEach>
	</select><br/><br/>
	<input type="submit" value="Submit" /><br/><br/>
</form>
<c:if test="${param.roomId !=null && param.city==null}">
	<form action="RoomBook.jsp" method="get" style="text-align: center">
		Room Id:
		<input type="text" name="roomId" value="${param.roomId }" /><br/><br/>
		Customer Name:
		<input type="text" name="custName" /><br/><br/>
		City:
		<input type="text" name="city" /><br/><br/>
		CheckIn Date:
		<input type="date" id="chDate" name="chDate" /><br/><br/>
		CheckOut Date:
		<input type="date" id="choutDate"  name="choutDate" />""<br/><br/>
		<input type="submit" value="Book"/>
	</form>
</c:if>
<c:if test="${param.roomId!=null && param.chDate != null && param.choutDate != null}">
	<jsp:useBean id="book" class="infinite.oyoproject.Booking"/>
	<jsp:setProperty property="roomId" name="book"/>
	<jsp:setProperty property="custName" name="book"/>
	<jsp:setProperty property="city" name="book"/>
	
	
	<fmt:parseDate value="${param.chDate}" pattern="yyyy-MM-dd" var="cdate1" />
	<c:set var="sqlDate1" value="${dao.convertDate(cdate1)}" />
	
	<fmt:parseDate value="${param.choutDate}" pattern="yyyy-MM-dd" var="cdate2" />
	<c:set var="sqlDate2" value="${dao.convertDate(cdate2)}" />
	
	<jsp:setProperty property="chDate" name="book" value="${sqlDate1}"/>
	<jsp:setProperty property="choutDate" name="book" value="${sqlDate2}"/>
	<c:out value="${dao.bookRoom(book)}" />
</c:if>
</body>
</html>