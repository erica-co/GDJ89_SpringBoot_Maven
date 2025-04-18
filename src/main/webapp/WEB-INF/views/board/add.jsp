<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
   <link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-bs4.min.css" rel="stylesheet">
   
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
	
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<c:import url="/WEB-INF/views/templates/topbar.jsp"></c:import>
				<div class="container-fluid">
					
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">
								${kind}글작성
							</h6>
						</div>
						<div class="card-body">
						<form method="post" enctype="multipart/form-data">
						  <div class="form-group">
						    <label for="user">User</label>
						    <input type="text" name="userName" class="form-control" id="user" >
						  </div>
						  <div class="form-group">
						    <label for="title">Title</label>
						    <input type="text" name="boardTitle" class="form-control" id="title" >
						  </div>

						  <div class="form-group">
						    <label for="contents">Contents</label>
						    <textarea name="boardContents" class="form-control" id="contents" rows="6"></textarea>
						  </div>
						  
						  <div>
						  	<input type="file" name="attaches"><br>
						  	<input type="file" name="attaches"><br>
						  	<input type="file" name="attaches"><br>
						  </div>
						  
						  <button type="submit" class="btn btn-primary">Submit</button>
						</form>
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
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote-bs4.min.js"></script>
	<script>
      $('#contents').summernote({
        tabsize: 2,
        height: 300
      });
    </script>
</body>
</html>