<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>菜单列表</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<link href="${ctx}/resource/thirdparty/treeTable/themes/vsStyle/treeTable.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resource/thirdparty/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	// 展开五层
	$('#treeTable').treeTable({expandLevel : 5});
});

// 删除菜单
function deleteMenu(id) {

	art.dialog.confirm("确定要删除吗?",function(){
		//ok
		$.post("${ctx }/system/menu/delete?id=" + id,{},function(data){
			if(data.message=="删除成功"){
				window.location.href = '${ctx}/system/menu/list';
			}else{
				artAlert(data.message);
			}
		},"json");
	},function(){
		//cancel
		art.dialog.close();
	});
}
</script>
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
                <li><a href="${ctx }/system/admin"><i class="fa fa-home"></i></a><span class="divider">/</span></li>
                <li><a href="${ctx }/system/menu/list">菜单管理</a><span class="divider">/</span></li>
            </ul>
            <h4>菜单信息</h4>
        </div>
    </div>
    <div class="pull-right menu-right">
        <%-- <a href="" class="btn">刷新</a>
        <a href="javascript:void(0)" class="btn btn-primary" id="edit"><i class="fa fa-edit"></i>修改信息</a> --%>
    </div>
</div>
<!-- /页头 -->

<!-- 主内容区 -->
<div class="contentpanel">
	<div class="row">
		<table id="treeTable" class="list-table table table-bordered table-hover">
			<thead>
				<tr>
					<th>菜单名称</th>
					<th>菜单URL</th>
					<th>操作</th>
		        </tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
					<tr >
		            	<td colspan="6" style="text-align: center;">无数据</td>
		            </tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach items="${list}" var="menu">
			                <tr id="${menu.id }" pId="${menu.parentId }">
								<td>${menu.name }</td>
								<td>${menu.url }</td>
								<td>
									<a href="${ctx }/system/menu/form?id=${menu.id}">编辑</a>
									<a href="${ctx }/system/menu/form?pid=${menu.id}">添加子菜单</a>
									<a href="javascript:void(0);" onclick="deleteMenu('${menu.id}')">删除</a>
								</td>
							</tr>
			        </c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>