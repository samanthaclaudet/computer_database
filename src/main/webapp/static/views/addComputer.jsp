<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<jsp:include page="header.jsp"/>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    
                    <jsp:include page="errorMessage.jsp" />
                    
                    <h1>Add Computer</h1>
                    <form action="add-computer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input required type="text" class="form-control" id="computerName" placeholder="Computer name" name="computerName">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><span>Introduced date</span></label>
                                <input type="datetime-local" class="form-control" id="introduced" placeholder="Introduced date" name="introduced">
                                <span class="errorintroduced"> Invalid Date.(yyyy-mm-dd HH:mm)</span>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><span>Discontinued date</span></label>
                                <input type="datetime-local" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued">
                            	<span class="errordiscontinued"> Invalid Date.(yyyy-mm-dd HH:mm)</span>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                	<option value = 0> -- </option>
                                    <c:forEach items="${ companies }" var="company" varStatus="boucle">
           								<option value = ${ company.id }> ${ company.name } </option>
				       				</c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" id="addComputer" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/static/js/checkdate.js"/>"></script>
    
</body>
</html>