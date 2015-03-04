<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<mylib:pagination page="${page}" search="${search}" order="${order}" language="${language}"/>
	</div>
</footer>
