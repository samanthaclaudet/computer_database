<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:if test="${errors != null}">
	<div class="alert alert-danger">
		<h4>${errors.size()}<spring:message code="label.errors" /> :</h4>
		<ul>
			<c:forEach items="${errors}" var="error">
				<li><c:out value="${error}" /></li>
			</c:forEach>
		</ul>
	</div>
</c:if>