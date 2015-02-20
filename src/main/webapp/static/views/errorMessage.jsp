<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${errors != null}">
	<div class="alert alert-danger">
		<h4>${errors.size()}Errors :</h4>
		<ul>
			<c:forEach items="${errors}" var="error">
				<li><c:out value="${error}" /></li>
			</c:forEach>
		</ul>
	</div>
</c:if>