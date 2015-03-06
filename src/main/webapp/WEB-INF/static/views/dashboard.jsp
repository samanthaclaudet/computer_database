<!-- <!DOCTYPE html> -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<jsp:include page="header-dashboard.jsp" />

	<section id="main">
	<div class="container">
		<h1 id="homeTitle">${ nbcomputers } <spring:message code="label.computerFound" /></h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="<spring:message code="label.searchName" />" /> 
					<input type="submit" id="searchsubmit" value="<spring:message code="label.filter" />"
						class="btn btn-primary" />


				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="add-computer"><spring:message code="label.addComputer" /></a>
				<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="label.editComputer" /></a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="delete-computer" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th><a class="navbar-brand" href="<mylib:link target="dashboard" index="${page.idx+1}" nbPerPage="${page.nbComputerPerPage}" search="${search}" order="computerName" language="${language}"/>"><spring:message code="label.computerName" /></a></th>
					<th><a class="navbar-brand" href="<mylib:link target="dashboard" index="${page.idx+1}" nbPerPage="${page.nbComputerPerPage}" search="${search}" order="introduced" language="${language}"/>"><spring:message code="label.introducedDate" /></a></th>
					<!-- Table header for Discontinued Date -->
					<th><a class="navbar-brand" href="<mylib:link target="dashboard" index="${page.idx+1}" nbPerPage="${page.nbComputerPerPage}" search="${search}" order="discontinued" language="${language}"/>"><spring:message code="label.discontinuedDate" /></a></th>
					<!-- Table header for Company -->
					<th><a class="navbar-brand" href="<mylib:link target="dashboard" index="${page.idx+1}" nbPerPage="${page.nbComputerPerPage}" search="${search}" order="company" language="${language}"/>"><spring:message code="label.company" /></a></th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<c:choose>
				<c:when test="${ nbcomputers == 0 }">
					<p><spring:message code="label.noFound" /></p>
				</c:when>
				<c:otherwise>
					<tbody id="results">
						<c:forEach items="${ page.computers }" var="computer"
							varStatus="boucle">
							<tr>
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value=${ computer.id }></td>
								<td><a href="edit-computer?id=${ computer.id }" onclick="">${ computer.name }</a>
								</td>
								<td>${ computer.introduced }</td>
								<td>${ computer.discontinued }</td>
								<td>${ computer.company.name }</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	</section>

	<script type="text/javascript">
		var strings = new Array();
		strings['delete_confirm_msg'] = "<spring:message code='label.deleteConfirm' javaScriptEscape='true' />";
		strings['view_button'] = "<spring:message code='label.view' javaScriptEscape='true' />";
		strings['edit_button'] = "<spring:message code='label.edit' javaScriptEscape='true' />";
	</script>

	<c:if test="${ nbcomputers != 0 }">
		<jsp:include page="footer.jsp" />
	</c:if>

	<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/static/js/dashboard.js"/>"></script>

</body>
</html>