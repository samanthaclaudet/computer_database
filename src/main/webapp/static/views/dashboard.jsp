<!-- <!DOCTYPE html> -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<jsp:include page="header.jsp" />
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard" name="search" value="">
			Application - Computer Database </a>
	</div>
	</header>

	<section id="main">
	<div class="container">
		<h1 id="homeTitle">${ nbcomputers } Computers found</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">

					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="add-computer">Add Computer</a>
				<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
				<a class="btn btn-success" id="deleteCompany" href="delete-company">Delete Company</a>
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
					<th>Computer name</th>
					<th>Introduced date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued date</th>
					<!-- Table header for Company -->
					<th>Company</th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<c:choose>
				<c:when test="${ nbcomputers == 0 }">
					<p>No computers found</p>
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

	<c:if test="${ nbcomputers != 0 }">
		<jsp:include page="footer.jsp" />
	</c:if>

	<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/static/js/dashboard.js"/>"></script>

</body>
</html>