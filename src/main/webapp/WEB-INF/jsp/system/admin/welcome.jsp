<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>欢迎</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<script type="text/javascript" src="${assets}/resource/common/js/md5.js"></script>
<script type="text/javascript" src="${assets}/resource/thirdparty/jquery-validation-1.13.1/jquery.validate.min.js"></script>
 </style>
<script type="text/javascript">
</script>
<style>
</style>
</head>
<body>
	<!-- 页头 -->
	<div class="pageheader">
		<div class="media pull-left">
			<div class="pageicon pull-left">
				<i class="fa fa-home"></i>
			</div>
			<div class="media-body">
				<ul class="breadcrumb">
					<li><a href="${ctx }/system/admin.do"><i class="fa fa-home"></i></a><span class="divider">/</span></li>
					<li><a href="${ctx }/system/user/showList.do">welcome</a><span class="divider">/</span></li>
				</ul>
				<h4>欢迎使用</h4>
			</div>
		</div>
		<div class="pull-right menu-right">

		</div>
	</div>

</body>
</html>
