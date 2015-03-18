<%@ tag language="java" pageEncoding="UTF-8" description="Link template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="target" required="true"%>
<%@ attribute name="index" required="true"%>
<%@ attribute name="nbPerPage" required="true"%>
<%@ attribute name="search" required="true"%>
<%@ attribute name="order" required="true"%>
<%@ attribute name="language" required="true"%>

<c:url value="${target}?page=${index}&nbPerPage=${nbPerPage}&search=${search}&order=${order}&language=${language}"/>