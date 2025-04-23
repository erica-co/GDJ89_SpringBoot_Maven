<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="./templates/header.jsp"></c:import>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="./templates/sidebar.jsp"></c:import>
	
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="./templates/topbar.jsp"></c:import>
				<div class="container-fluid">
					<sec:authorize access="hasRole('ADMIN')">
						<h3>Admin View</h3>
					</sec:authorize>
					
					<sec:authorize access="isAuthenticated">
						<h3>USERNAME : <sec:authentication property="principal.name"/></h3> 
					</sec:authorize>
				
					<p>
					<spring:message code="welcome" var="m"></spring:message>
					</p>
					<p>
					<spring:message code="hello"></spring:message>
					</p>
					<p>
					<spring:message code="hi" text="안녕하세요"></spring:message>
					</p>
					<!-- contents 내용 -->
					<h3>${m}</h3>
					
					<spring:message code="welcome.login" arguments="${user.username}-${user.name}" argumentSeparator="-"></spring:message>
					
					
				</div>
			</div>
			<!-- End Content -->
			<c:import url="./templates/foot.jsp"></c:import>
		</div>
		<!-- End Content-wrapper -->	
	</div>
	<!-- End wrapper -->

	
	<c:import url="./templates/footer.jsp"></c:import>
</body>
</html>