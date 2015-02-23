<%@ tag language="java" pageEncoding="UTF-8" description="Page template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mylib"%>

<%@ attribute name="page" required="true" type="com.excilys.cdb.model.Page"%>
<%@ attribute name="search" required="true"%>

<ul class="pagination">
	<li <c:if test="${page.idx == 0}">style="display:none;"</c:if>>
		<a href="<mylib:link target="dashboard" index="${page.idx}" nbPerPage="${page.nbComputerPerPage}" search="${search}"/>" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
		</a>
	</li>
	<c:forEach begin="${page.range[0]}" end="${page.range[1]-1}" var="index">
		<li <c:if test="${page.idx == index}">class="active"</c:if>>
			<a href="<mylib:link target="dashboard" index="${page.idx+1}" nbPerPage="${page.nbComputerPerPage}" search="${search}"/>">${index+1}</a>
		</li>
	</c:forEach>
	<li <c:if test="${page.idx+1 == page.nbPages}">style="display:none;"</c:if>>
		<a href="<mylib:link target="dashboard" index="${page.idx+2}" nbPerPage="${page.nbComputerPerPage}" search="${search}"/>" aria-label="Next">
			<span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group" >
   	<a type="button" href="<mylib:link target="dashboard" index="1" nbPerPage="10" search="${search}"/>" ${page.nbComputerPerPage == 10 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>10</a>
	<a type="button" href="<mylib:link target="dashboard" index="1" nbPerPage="50" search="${search}"/>" ${page.nbComputerPerPage == 50 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>50</a>
	<a type="button" href="<mylib:link target="dashboard" index="1" nbPerPage="100" search="${search}"/>" ${page.nbComputerPerPage == 100 ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>100</a>
</div>