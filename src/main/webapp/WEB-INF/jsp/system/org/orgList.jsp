<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>组织机构新增</title>
<link href="${ctx}/resource/thirdparty/treeTable/themes/vsStyle/treeTable.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resource/common/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/resource/thirdparty/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	
	$('#treeTable').treeTable({expandLevel : 5});
});
</script>
<style type="text/css">
table,td,th {  border: 1px solid #8DB9DB; padding:5px; border-collapse: collapse; font-size:16px; }
</style>
</head>
<body>
<table id="treeTable">
	<tr>
		<th>ID</th>
		<th>组织名称</th>
		<th>类别</th>
		<th>状态</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${list}" var="org">
	<tr id="${org.id }" pId="${org.parentId }">
		<td>${org.id }</td>
		<td>${org.name }</td>
		<td>${org.id }</td>
		<td>${org.id }</td>
		<td>
			<a href="${ctx }/org/form.do?id=${org.id}">编辑</a>
			<a href="${ctx }/org/form.do?pid=${org.id}">添加子组织</a>
			<a href="${ctx }/org/delete.do?id=${org.id}">删除</a>
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>