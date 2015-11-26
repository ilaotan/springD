<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>幼儿园管理后台</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<script type="text/javascript">

function deleteUser(uid, name){

	art.dialog({  
        content:"确定删除用户：" + name + "?",  
        opacity: 0.37,  // 透明度  
        fixed: true,//静止定位，用户滚动窗体时依然居中显示 
        lock: true,
        ok: function () {  
			$.ajax({
				type: "GET",
				url:"${ctx}/system/user/delete.do",
				data:{uid : uid},
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
					<li><a href="${ctx }/system/admin.do"><i class="fa fa-home"></i></a><span class="divider">/</span></li>
					<li><a href="${ctx }/system/user/showList.do">用户管理</a><span class="divider">/</span></li>
					<li><a href="">用户列表</a><span class="divider">/</span></li>
				</ul>
				<h4>用户管理</h4>
			</div>
		</div>
		<div class="pull-right menu-right">
			<a href="${ctx}/system/usersystem/add.do" class="btn btn-primary"><i class="fa fa-edit"></i>添加账号</a>
		</div>
	</div>
	
	<!-- 主内容区 -->
	<div class="contentpanel">
		<div class="row">
			<div class="pull-left">
				<form class="form-inline"  action="${ctx}/system/usersystem/showList.do" id ="fenyeForm" method="get">
					<div class="control-group">
						<div class="controls">
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
							<input type="text" class="form-control" size="25" name="cxUserName" value="${cxUserName}"  placeholder="请输入用户名" />
							<input type="text" class="form-control" size="25" name="cxPhone" value="${cxPhone}"  placeholder="请输入用户手机号" />
							<%--
							<input type="text" class="form-control" size="25" name="cxEmail" value="${cxEmail}"  placeholder="请输入用户邮箱" />
							<select class="form-control" name="cxSex">
								<option value="0">用户性别</option>
								<option value="1" ${cxSex == '1' ? 'selected' : '' }>男</option>
								<option value="2" ${cxSex == '2' ? 'selected' : '' }>女</option>
							</select>
							 --%>
							<input type="button" class="btn" value="查询" onclick="$('#fenyeForm').submit();" />
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="row">
			<table class="table table-bordered table-hover">
				<tr>
					<th>用户名</th>
					<th>手机号</th>
					<th>邮箱</th>
					<th>状态</th>
					<th>性别</th>
					<th>注册时间</th>
					<%-- <th>上次登陆ip</th>
					<th>上次登陆时间</th> --%>
					<th>权限</th>
					<th>操作</th>
				</tr>
				<c:if test="${empty page.list}">
					<tr >
		            	<td colspan="11" style="text-align: center;">没有符合条件的数据</td>
		            </tr>
				</c:if>
				<c:if test="${!empty page.list}">
					<c:forEach items="${page.list}" var="user">
						<tr id="${user.uid }">
							<td>${user.username }</td>
							<td>${user.phone }</td>
							<td>${user.email }</td>
							<td>${user.status == '1' ? '正常' : '未激活'}</td>
							<td>${user.sex == '2' ? '女' : (user.sex=='1'?'男':'未知')}</td>
							<td>
			                	<fmt:formatDate value="${user.regdate}"  type="date"/>
			                </td>
			                <%-- <td>${user.lastloginip}</td>
			                <td>
			                	<fmt:formatDate value="${user.lastlogindate}"  type="date"/>
			                </td> --%>
			                <td>${user.roleName}</td>
			                <td>
			                	<a href="${ctx }/system/usersystem/update.do?uid=${user.uid}&pn=${page.pageNo}">编辑</a>
			                	<a href="javascript:deleteUser('${user.uid}', '${user.username}')">删除</a>
			                	<%-- <a href="javascript:void(0)" onclick="dialog('${ctx }/system/user/permissionForm.do?id=${user.uid}','权限分配','','','')">权限管理</a> --%>
			                </td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
		<div class="pagination pagination-right">
			${page}
		</div>
	</div>
</body>
</html>
