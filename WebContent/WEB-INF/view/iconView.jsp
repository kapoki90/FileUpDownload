<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>iconView</title>

<style type="text/css">
	body p {
	&nbsp;overflow: hidden; 
	&nbsp;text-overflow: ellipsis;
	&nbsp;white-space: nowrap; 
	&nbsp;width: 100px;
	&nbsp;height: 20px;
	}
	hr { border: 0;}
</style>

</head>
<body>
	<c:set var="i" value="0" />
	<table border="1px">
	
		<input type="hidden" name="idx" value="${dto.idx}"/>
		<input type="hidden" name="ref" value="${dto.ref}"/>
		<input type="hidden" name="lev" value="${dto.lev}"/>
		<input type="hidden" name="seq" value="${dto.seq}"/>
		<input type="hidden" name="folderName" value="${dto.folderName}"/>
		
		<tr>
		<%-- <c:forEach var="변수" items="아이템" begin="시작값" end="끝값" step="증가값">  --%>
		<%-- <c:forEach var="list" items="${list}" begin="시작값" end="끝값" step="증가값"> --%>
		<c:forEach items="${list}" var="list">
			<c:set var="i" value="${i+1}"/>
			<c:choose>
				<c:when test="${list.fileExtension eq '.zip'}">
					<td><a href="fileDown.do?fileName=${ list.fileName }"><img src=".\images\zip.gif" /></a><hr/>
					<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
 					<fmt:formatDate value="${parsedDate}" pattern="yy-MM-dd hh:mm:ss"/><hr/>
					<a href="fileDown.do?fileName=${ list.fileName }&folderName=${list.folderName}"><p>${ list.fileName }</p></a></td>
				</c:when>
				<c:when test="${list.fileExtension eq '.txt'}">
					<td><a href="fileDown.do?fileName=${ list.fileName }"><img src=".\images\txt.gif" /></a><hr/>
					<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
 					<fmt:formatDate value="${parsedDate}" pattern="yy-MM-dd hh:mm:ss"/><hr/>
					<a href="fileDown.do?fileName=${ list.fileName }&folderName=${list.folderName}"><p>${ list.fileName }</p></a></td>
				</c:when>
				<c:when test="${list.fileExtension eq '.exe'}">
					<td><a href="fileDown.do?fileName=${ list.fileName }"><img src=".\images\exe.gif" /></a><hr/>
					<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
 					<fmt:formatDate value="${parsedDate}" pattern="yy-MM-dd hh:mm:ss"/><hr/>
					<a href="fileDown.do?fileName=${ list.fileName }&folderName=${list.folderName}"><p>${ list.fileName }</p></a></td>
				</c:when>
				<c:when test="${list.fileExtension eq '.gif'}">
					<td><a href="fileDown.do?fileName=${ list.fileName }"><img src=".\images\gif.gif" /></a><hr/>
					<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
 					<fmt:formatDate value="${parsedDate}" pattern="yy-MM-dd hh:mm:ss"/><hr/>
					<a href="fileDown.do?fileName=${ list.fileName }&folderName=${list.folderName}"><p>${ list.fileName }</p></a></td>
				</c:when>
				<c:when test="${list.fileExtension eq '.jpg'}">
					<td><a href="fileDown.do?fileName=${ list.fileName }"><img src=".\images\jpg.gif" /></a><hr/>
					<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
 					<fmt:formatDate value="${parsedDate}" pattern="yy-MM-dd hh:mm:ss"/><hr/>
					<a href="fileDown.do?fileName=${ list.fileName }&folderName=${list.folderName}"><p>${ list.fileName }</p></a></td>
				</c:when>
				<c:when test="${list.fileExtension eq '.folder'}">
					<td><a href="iconFolderContentView.do?idx=${list.idx}&ref=${list.ref}&lev=${list.lev}"><img src=".\images\Bigfolder.png" /></a><hr/>
					<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
 					<fmt:formatDate value="${parsedDate}" pattern="yy-MM-dd hh:mm:ss"/><hr/>
					<c:set var="fileRoot" value="${fn:split(list.folderName, '/')}" />
					<a href="iconFolderContentView.do?idx=${list.idx}&ref=${list.ref}&lev=${list.lev}">${fileRoot[fn:length(fileRoot)-1]}</a></td>
				</c:when>
				<c:otherwise>
     				<td><a href="fileDown.do?fileName=${ list.fileName }"><img src=".\images\non.gif" /></a><hr/>
     				<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
 					<fmt:formatDate value="${parsedDate}" pattern="yy-MM-dd hh:mm:ss"/><hr/>
					<a href="fileDown.do?fileName=${ list.fileName }&folderName=${list.folderName}"><p>${ list.fileName }</p></a></td>
     		   	</c:otherwise>
			</c:choose>
			
			<c:if test="${i%5 eq '0'}">
				</tr>
				<tr>
			</c:if>
		</c:forEach>
		</tr>
	</table>
	<input type="button" value="back" onclick="location.href='javascript:history.back()'"/>
</body>
</html>