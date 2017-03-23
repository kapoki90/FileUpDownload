<%@page import="calendar.com.myproject.MyCalendar"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>오리지날달력</title>
<%-- <link rel="stylesheet" href="<c:url value="/view/calendarstyle.css" />" type="text/css">
 --%>
<style type="text/css">
@CHARSET "UTF-8";
a:LINK {text-decoration: none; color: navy;}
a:VISITED {text-decoration: none; color: green;}
a:HOVER {text-decoration: none; color: red;}
a:ACTIVE {text-decoration: none; color: maroon;}

th {color: navy; font-size: 25pt;}
tr {height: 60px;}
td.sun {color: red; text-align: center; font-size: 20pt; font-weight: bold; width: 100px;}
td.etc {text-align: center; font-size: 20pt; font-weight: bold; width: 100px;}
td.sat {color: blue; text-align: center; font-size: 20pt; font-weight: bold; width: 100px;}

td.day {border: 1pt solid; border-color: gray; text-align: right; vertical-align: top;}
td.sunday {border: 1pt solid; border-color: gray; text-align: right; vertical-align: top; color: red;}
td.satday {border: 1pt solid; border-color: gray; text-align: right; vertical-align: top; color: blue;}
td.hday {border: 1pt solid; border-color: gray; text-align: right; vertical-align: top; color: red;}
td.before {border: 1pt solid; border-color: gray; text-align: right; vertical-align: top; font-size: 12px;
	background: #8983F0;}
td.after {border: 1pt solid; border-color: gray; text-align: right; vertical-align: top; font-size: 12px;
	background: #B0FCAB;}
span {font-size: 12px;}
</style>

</head>
<body>
<%
//	달력이 최초로 실행될 때 출력할 년, 월을 컴퓨터 시스템에서 받는다.
	Calendar calendar = Calendar.getInstance();
	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH) + 1;
	
//	페이지 이동 하이퍼링크를 클릭했을 때 넘어오는 년, 월을 받는다.
	try {
		year = Integer.parseInt(request.getParameter("year"));
		month = Integer.parseInt(request.getParameter("month"));
		if(month <= 0) {
			year--;
			month = 12;
		}
		if(month >= 13) {
			year++;
			month = 1;
		}
	} catch(Exception e) { }
	
%>
<table width="700" align="center" border="0">
	<tr>
		<th>
			<input type="button" value="이전달" onclick="location.href='?year=<%=year %>&month=<%=month-1 %>'">
		</th>
		<th colspan="5">
			<a href="?year=<%=year %>&month=<%=month-1 %>">&lt;&lt;</a>
			<%=year %>년 <%=month %>월
			<a href="?year=<%=year %>&month=<%=month+1 %>">&gt;&gt;</a>
		</th>
		<th>
<%
			out.println("<input type='button' value='다음달' onclick='location.href=\"?year=" + year 
					+ "&month=" + (month+1) + "\"'>");
%>
			<input type="button" value="다음달" onclick="location.href='?year=<%=year %>&month=<%=month+1 %>'">
		</th>
	</tr>
	<tr>
		<td class="sun">일</td>
		<td class="etc">월</td>
		<td class="etc">화</td>
		<td class="etc">수</td>
		<td class="etc">목</td>
		<td class="etc">금</td>
		<td class="sat">토</td>
	</tr>
	<tr>
<%
	int start = MyCalendar.weekDay(year, month, 1);
//	1일이 출력될 요일의 위치를 맞추기 위해 1일의 요일만큼 반복하며 빈 칸을 출력한다.
//	for(int i=1 ; i<=start ; i++) {
//		out.println("<td>&nbsp;</td>");
//	}

//	1일이 출력되기 전에 전달의 날짜를 출력한다.
	int count;
	if(month == 1) {
		count = MyCalendar.lastDay(year-1, 12) - start + 1;
	} else {
		count = MyCalendar.lastDay(year, month-1) - start + 1;
	}
	for(int i=1 ; i<=start ; i++) {
		out.println("<td class='before'>" + (month == 1 ? 12 : month-1) + "/" + count++ + "</td>");
	}

//	1일 부터 달력을 출력할 달의 마지막 날짜까지 반복하며 날짜를 출력한다.
	int week = 0;
	for(int i=1 ; i<=MyCalendar.lastDay(year, month) ; i++) {
		
		week = MyCalendar.weekDay(year, month, i);
		if(month == 1 && i == 1) {
			out.println("<td class='hday'>" + i + "<br/><span>신정</span></td>");
		} else if(month == 3 && i == 1) {
			out.println("<td class='hday'>" + i + "<br/><span>삼일절</span></td>");
		} else if(month == 5 && i == 5) {
			out.println("<td class='hday'>" + i + "<br/><span>어린이날</span></td>");
		} else if(month == 6 && i == 6) {
			out.println("<td class='hday'>" + i + "<br/><span>현충일</span></td>");
		} else if(month == 8 && i == 15) {
			out.println("<td class='hday'>" + i + "<br/><span>광복절</span></td>");
		} else if(month == 10 && i == 3) {
			out.println("<td class='hday'>" + i + "<br/><span>개천절</span></td>");
		} else if(month == 10 && i == 9) {
			out.println("<td class='hday'>" + i + "<br/><span>한글날</span></td>");
		} else if(month == 12 && i == 25) {
			out.println("<td class='hday'>" + i + "<br/><span>크리스마스</span></td>");
		} else if(year == 2017 && month == 10 && i == 4) {
			out.println("<td class='hday'>" + i + "<br/><span>추석</span></td>");
		} else if(year == 2017 && month == 10 && i == 5) {
			out.println("<td class='hday'>" + i + "<br/><span>추석연휴</span></td>");
		} else if(year == 2017 && month == 10 && i == 6) {
			out.println("<td class='hday'>" + i + "<br/><span>대체공휴일</span></td>");
		} else {
			switch(week) {
				case 0: out.println("<td class='sunday'>" + i + "</td>"); break;
				case 6: out.println("<td class='satday'>" + i + "</td>"); break;
				default: out.println("<td class='day'>" + i + "</td>");
			}
		}
		
//		출력한 날짜가 토요일이고 그 달의 마지막 날짜가 아니면 줄을 바꾼다.
		if(week == 6 && i != MyCalendar.lastDay(year, month)) {
			out.println("</tr><tr>");
		}
	}

//	마지막 날짜의 다음 요일 부터 토요일 까지 반복하며 다음달의 날짜를 출력한다.
	count = 1;
	for(int i=week+1 ; i<7 ; i++) {
		out.println("<td class='after'>" + (month == 12 ? 1 : month+1) + "/" + count++ + "</td>");
	}
%>
	</tr>
	
	<tr>
		<td colspan="7">
		<form action="?" method="post">
			<select name="year">
<%
	for(int i=1950 ; i<=2050 ; i++) {
		if(i == calendar.get(Calendar.YEAR)) {
			out.println("<option selected='selected'>" + i + "</option>");
		} else {
			out.println("<option>" + i + "</option>");
		}
	}
%>
			</select>년 
			<select name="month">
<%
	for(int i=1 ; i<=12 ; i++) {
		if(i == calendar.get(Calendar.MONTH) + 1) {
			out.println("<option selected='selected'>" + i + "</option>");
		} else {
			out.println("<option>" + i + "</option>");
		}
	}
%>
			</select>월
			<input type="submit" value="보기"/>
		</form>
		</td>
	</tr>
	
</table>
</body>
</html>
















