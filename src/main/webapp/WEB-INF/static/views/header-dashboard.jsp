<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<head>
	<title><spring:message code="label.computerDatabase" /></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<!-- Bootstrap -->	
	<link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
	<link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet" media="screen">
	<link href="<c:url value="/static/css/main.css"/>" rel="stylesheet" media="screen">
</head>

<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> <spring:message code="label.applicationComputerDatabase" /> </a>

            <div class="btn-group btn-group-sm pull-right" role="group">
				<a type="button" href="<mylib:link target="dashboard" index="${page.idx+1}" nbPerPage="${page.nbComputerPerPage}" search="${search}" order="${order}" language="fr" />" ${pageContext.response.locale == 'fr' ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>fr</a>
				<a type="button" href="<mylib:link target="dashboard" index="${page.idx+1}" nbPerPage="${page.nbComputerPerPage}" search="${search}" order="${order}" language="en" />" ${pageContext.response.locale == 'en' ? 'class="btn btn-primary"' : 'class="btn btn-default"'}>en</a>
			</div>
        </div>
    </header>
    