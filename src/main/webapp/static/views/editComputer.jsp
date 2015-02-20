<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

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
                    <div class="label label-default pull-right">
                        id: ${ idComputer }
                    </div>
                    
                    <jsp:include page="errorMessage.jsp" />
                    
                    <h1>Edit Computer</h1>

                    <form action="edit-computer" method="POST">
                        <input type="hidden" value="0"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input required type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${ computer.name }">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><span>Introduced date</span></label>
                                <input type="datetime-local" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value=${ computer.introduced }>
                            	<span class="errorintroduced"> Invalid Date.(yyyy-mm-dd HH:mm)</span>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><span>Discontinued date</span></label>
                                <input type="datetime-local" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value=${ computer.discontinued }>
                            	<span class="errordiscontinued"> Invalid Date.(yyyy-mm-dd HH:mm)</span>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<option value = ${ computer.company.id }> ${ computer.company.name } </option>
                                	<option value = 0> -- </option>
                                    <c:forEach items="${ companies }" var="company" varStatus="boucle">
           								<option value = ${ company.id }> ${ company.name } </option>
				       				</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
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