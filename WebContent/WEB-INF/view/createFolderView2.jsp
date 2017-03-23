<!-- fileList.jsp  -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
th {
	color: #FFFFFF;
	
	background: #a90329; /* Old browsers */
	/* IE9 SVG, needs conditional override of 'filter' to 'none' */
	background: url(data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiA/Pgo8c3ZnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgd2lkdGg9IjEwMCUiIGhlaWdodD0iMTAwJSIgdmlld0JveD0iMCAwIDEgMSIgcHJlc2VydmVBc3BlY3RSYXRpbz0ibm9uZSI+CiAgPGxpbmVhckdyYWRpZW50IGlkPSJncmFkLXVjZ2ctZ2VuZXJhdGVkIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSIgeDE9IjAlIiB5MT0iMCUiIHgyPSIwJSIgeTI9IjEwMCUiPgogICAgPHN0b3Agb2Zmc2V0PSIwJSIgc3RvcC1jb2xvcj0iI2E5MDMyOSIgc3RvcC1vcGFjaXR5PSIxIi8+CiAgICA8c3RvcCBvZmZzZXQ9IjQ0JSIgc3RvcC1jb2xvcj0iIzhmMDIyMiIgc3RvcC1vcGFjaXR5PSIxIi8+CiAgICA8c3RvcCBvZmZzZXQ9IjEwMCUiIHN0b3AtY29sb3I9IiM2ZDAwMTkiIHN0b3Atb3BhY2l0eT0iMSIvPgogIDwvbGluZWFyR3JhZGllbnQ+CiAgPHJlY3QgeD0iMCIgeT0iMCIgd2lkdGg9IjEiIGhlaWdodD0iMSIgZmlsbD0idXJsKCNncmFkLXVjZ2ctZ2VuZXJhdGVkKSIgLz4KPC9zdmc+);
	background: -moz-linear-gradient(top,  #a90329 0%, #8f0222 44%, #6d0019 100%); /* FF3.6+ */
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#a90329), color-stop(44%,#8f0222), color-stop(100%,#6d0019)); /* Chrome,Safari4+ */
	background: -webkit-linear-gradient(top,  #a90329 0%,#8f0222 44%,#6d0019 100%); /* Chrome10+,Safari5.1+ */
	background: -o-linear-gradient(top,  #a90329 0%,#8f0222 44%,#6d0019 100%); /* Opera 11.10+ */
	background: -ms-linear-gradient(top,  #a90329 0%,#8f0222 44%,#6d0019 100%); /* IE10+ */
	background: linear-gradient(to bottom,  #a90329 0%,#8f0222 44%,#6d0019 100%); /* W3C */
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#a90329', endColorstr='#6d0019',GradientType=0 ); /* IE6-8 */
}

td {
	text-align: center;
	font-size: 9pt;
}
</style>
</head>
<body>
	<form action="createFolderInFolder.do" method="post" >
		<fieldset>
			<table style="border: 1px; width: 600px;">		
				<tr>
				<td>
				<input type="hidden" name="idx" value="${dto.idx}"/>
				<input type="hidden" name="ref" value="${dto.ref}"/>
				<input type="hidden" name="lev" value="${dto.lev}"/>
				<input type="hidden" name="seq" value="${dto.seq}"/>
				<input type="hidden" name="folderName" value="${dto.folderName}"/>
				</td>
				</tr>
				<tr>
					<th style="width: 100px">Number</th>
					<th style="width: 150px">Date</th>
					<th style="width: 150px">FileName</th>
					<th style="width: 100px">FileSize</th>
				</tr>
					<tr>
							<td width="100px"></td>
		 					<td width="150px"></td>
							<td width="150px"><input type="text" name="NewfolderName" required="required"/></td>
							<td width="100px"></td>
							<td><input type="submit" value="Create"/></td>
							<td><input type="button" value="Cancel" onclick="location.href='list.do'"/></td>
					</tr>
				<c:if test="${ !empty list }">
					<c:forEach items="${ list }" var="list">
						<tr>
							<td width="100px">${ list.idx }</td>
							<%-- <td width="150px">${ list.wdate }</td> --%>
		 					<td width="150px">
		 					<fmt:parseDate value="${list.wdate}" var="parsedDate" pattern="yyyy-M-dd.HH.mm. ss. S"/>
		 					<fmt:formatDate value="${parsedDate}"
								pattern="yyyy-MM-dd hh:mm:ss"/></td>
								
							<c:if test="${ list.fileName ne null}">
							<td width="150px">${ list.fileName }</td>
							</c:if>
							<c:if test="${ list.fileName eq null}">
							<c:set var="fileRoot" value="${fn:split(list.folderName, '/')}" />
							<td width="150px"><a href="folderContentView.do?idx=${list.idx}&ref=${list.ref}&lev=${list.lev}"><img src=".\images\folder.png" />${fileRoot[fn:length(fileRoot)-1]}</a></td>
							</c:if>
						
							<c:if test="${ list.fileSize/1024>1024}">
							<td width="100px"><fmt:formatNumber value="${list.fileSize/1024/1024 }" pattern="0.00"/>mb</td>
							</c:if>
							<c:if test="${ list.fileSize/1024<1024}">
							<td width="100px"><fmt:formatNumber value="${list.fileSize/1024}" pattern="0.00"/>kb</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</fieldset>
	</form>
</body>
</html>