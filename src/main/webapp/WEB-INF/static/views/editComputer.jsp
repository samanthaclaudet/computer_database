<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<jsp:include page="header.jsp"/>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${ idComputer }
                    </div>
                    
                    <jsp:include page="errorMessage.jsp" />
                    
                    <h1><spring:message code="label.editComputer" /></h1>

 						<form:form modelAttribute="computerDTO" action="edit-computer" method="POST">
                        <fieldset>
                        	<input type="hidden" id="id" name="id" value="${idComputer}">
                            <div class="form-group">
                                <label for="name"><spring:message code="label.computerName" /></label>
                                <spring:message code="label.computerName" var="computerNamePlaceholder"/>
                                <form:input type="text" class="form-control" id="name" name="name" placeholder="${ computerNamePlaceholder }" value="${ computer.name }" path="name"/>
								<form:errors path="name" cssClass="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.introducedDate" /></label>
                                <spring:message code="label.introducedDate" var="introducedDatePlaceholder"/>
                                <form:input type="datetime-local" class="form-control" id="introduced" name="introduced" placeholder="${ introducedDatePlaceholder }" value="${ computer.introduced }" path="introduced"/>
                                <span class="errorintroduced">  <spring:message code="label.invalidDate" /> </span>
								<form:errors path="introduced" cssClass="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.discontinuedDate" /></label>
                                <spring:message code="label.discontinuedDate" var="discontinuedDatePlaceholder"/>
                                <form:input type="datetime-local" class="form-control" id="discontinued" name="discontinued" placeholder="${ discontinuedDatePlaceholder }" value="${ computer.discontinued }" path="discontinued"/>
                            	<span class="errordiscontinued">  <spring:message code="label.invalidDate" /> </span>
								<form:errors path="discontinued" cssClass="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="label.company" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<option value = ${ computerDTO.company.id }> ${ computerDTO.company.name } </option>
                                	<option value = 0> -- </option>
                                    <c:forEach items="${ companies }" var="company" varStatus="boucle">
           								<option value = ${ company.id }> ${ company.name } </option>
				       				</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="editComputer" value="<spring:message code="label.edit" />" class="btn btn-primary">
                            <spring:message code="label.or" />
                            <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel" /></a>
                        </div>
						</form:form>
                </div>
            </div>
        </div>
    </section>
    
    <script type="text/javascript">
		var strings = new Array();
		strings['error_date_alert'] = "<spring:message code='label.errorDate' javaScriptEscape='true' />";
		strings['error_regex'] = "<spring:message code='label.regex' javaScriptEscape='true' />";
	</script>
    
    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/static/js/checkdate.js"/>"></script>
    
</body>
</html>