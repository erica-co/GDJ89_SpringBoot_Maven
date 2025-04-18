<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>

		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/topbar.jsp"></c:import>
				<div class="container-fluid">
					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">${kind}</h1>

					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">DataTables
								Example</h6>
						</div>
						<div class="card-body">
						
							 <form method="get" >
							<div class="col-md-3 input-group mb-3">
							    <select name="kind" class="form-control col-3" id="exampleFormControlSelect1">
							      <option value="k1">제목</option>
							      <option value="k3">작성자</option>
							      <option value="k2">내용</option>
							    </select>
							 
							  <input type="text" name="search" class="form-control col-6" aria-label="Recipient's username" aria-describedby="button-addon2">
							  <div class="input-group-append">
							    <button class="btn btn-secondary" type="submit" id="button-addon2">찾기</button>
							  </div>
							</div>
							  </form>
						
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>Num</th>
											<th>Title</th>
											<th>User</th>
											<th>Date</th>
											<th>Hit</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${list}" var="vo">
											<tr>
												<td>${vo.boardNum}</td>
												<td><a href="detail?boardNum=${vo.boardNum}"> ${vo.boardTitle}</a></td>
												<td>${vo.userName}</td>
												<td>${vo.boardDate}</td>
												<td>${vo.boardHit}</td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
							<div class="row justify-content-between">
							<nav aria-label="Page navigation example col-md-6">
							  <ul class="pagination">
							    <li class="page-item">
							      <a class="page-link" href="./list?page=${pager.start-1}&kind=${pager.kind}&search=${pager.search}" aria-label="Previous">
							        <span aria-hidden="true">&laquo;</span>
							      </a>
							    </li>
							    <c:forEach begin="${pager.start}" end="${pager.end}" var="i">
							    <li class="page-item"><a class="page-link" href="./list?page=${i}&kind=${pager.kind}&search=${pager.search}">${i}</a></li>
							    </c:forEach>
							    <li class="page-item">
							      <a class="page-link" href="./list?page=${pager.next?pager.end+1:pager.end}&kind=${pager.kind}&search=${pager.search}" aria-label="Next">
							        <span aria-hidden="true">&raquo;</span>
							      </a>
							    </li>
							  </ul>
							</nav>							
							
							<div class="">
								<a href="./add" class="btn btn-primary">글작성</a>
							</div>
						</div>	
						</div>
					</div>


				</div>
			</div>
			<!-- End Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content-wrapper -->
	</div>
	<!-- End wrapper -->


	<c:import url="/WEB-INF/views/templates/footer.jsp"></c:import>
</body>
</html>