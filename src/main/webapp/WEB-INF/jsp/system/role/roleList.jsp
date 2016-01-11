<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>角色列表</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<script type="text/javascript">
function deleteRole(id, name){

	art.dialog({  
        content:"确定删除角色：" + name + "?",  
        opacity: 0.37,  // 透明度  
        fixed: true,//静止定位，用户滚动窗体时依然居中显示 
        lock: true,
        ok: function () {  
			$.ajax({
				type: "GET",
				url:"${ctx}/system/role/delete",
				data:{id : id},
				error: function(request) {
					artAlert("删除失败");
				},
				success: function(data) {
					if(data.message=="删除成功"){// 刷新页面
						// artPopMsg("删除成功");
						artDialog.popMsg("删除成功");
						$('#fenyeForm').delay(1000).submit();
					}else{
						artPopMsg(data.message);
					}
				}
			});
        },  
        close: function () {//关闭前跳转，避免用户点击右上角的x而停在界面  
        }  
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
					<li><a href="${ctx }/system/role/list">角色管理</a><span class="divider">/</span></li>
					<li><a href="">角色列表</a><span class="divider">/</span></li>
				</ul>
				<h4>角色管理</h4>
			</div>
		</div>
		<div class="pull-right menu-right">
			<a href="${ctx}/system/role/add" class="btn btn-success"><i class="fa fa-edit"></i>添加角色</a>
		</div>
	</div>
	<!-- 主内容区 -->
	<div class="contentpanel">
		<div class="row">
			<div class="pull-left">
				<form class="form-inline"  action="${ctx}/system/role/list" id ="fenyeForm" method="get">
					<div class="control-group">
						<div class="controls">
							<input type="text" class="form-control" size="25" name="roleName" value="${roleName}"  placeholder="请输入角色名称" />
							<select class="form-control" name="roleStatus">
								<option value="">角色状态</option>
								<option value="1" ${roleStatus == '1' ? 'selected' : '' }>正常</option>
								<option value="0" ${roleStatus == '0' ? 'selected' : '' }>禁用</option>
							</select>
							<input type="button" class="btn btn-success" value="查询" onclick="$('#fenyeForm').submit();" />
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="row">
			<table class="table table-bordered table-hover">
				<tr>
					<th>角色名称</th>
					<th>角色code</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:if test="${empty list}">
					<tr >
		            	<td colspan="4" style="text-align: center;">没有符合条件的数据</td>
		            </tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach items="${list}" var="role">
						<tr id="${role.id }">
							<td>${role.name }</td>
							<td>${role.code }</td>
							<td>${role.status == '1' ? '正常' : '禁用'}</td>
							<td>
								<c:if test="${role.code!='superadmin' }">
									<a href="${ctx }/system/role/form?id=${role.id}" >编辑</a>
									<a href="javascript:deleteRole('${role.id}', '${role.name}')">删除</a>
									<a href="javascript:void(0)" onclick="dialog('${ctx }/system/role/permissionForm?id=${role.id}','权限分配','','','')">权限管理</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
	</div>
	<!-- /页头 -->
</body>
</html>