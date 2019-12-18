<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="<%=request.getContextPath() %>/static/pages/js/videoList.js?v=1.1" type="text/javascript"></script>

	<!-- BEGIN PAGE HEADER-->
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
	    <ul class="page-breadcrumb">
	        <li>
	            <span>首页</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>视频信息</span>
	            <i class="fa fa-circle"></i>
	        </li>
	        <li>
	            <span>视频列表</span>
	        </li>
	    </ul>
	</div>
	<!-- END PAGE BAR -->
	<!-- END PAGE HEADER-->
        
    <!-- 视频信息列表 jqgrid start -->
	<div class="row">
	
		<!-- 搜索内容 -->
		<div class="col-md-12">
			<br/>
				<form id="searchVideoListForm" class="form-inline" method="post" role="form">
					<div class="form-group">
						<label class="sr-only" for="userId">发布者ID:</label>
						<input id="userId" name="userId" type="text" class="form-control" placeholder="发布者ID" />
					</div>
					<div class="form-group">
						<label class="sr-only" for="videoDesc">描述:</label>
						<input id="videoDesc" name="videoDesc" type="text" class="form-control" placeholder="描述" />
					</div>
					<button id="searchVideoListButton" class="btn yellow-casablanca" type="button">搜    索</button>
				</form>
			</div>

    	<div class="col-md-12">
			<br/>
			<div class="videoList_wrapper">
			    <table id="videoList"></table>
			    <div id="videoListPager"></div>
			</div>
		</div>
	</div>
	<!-- 视频信息列表 jqgrid end -->
	
