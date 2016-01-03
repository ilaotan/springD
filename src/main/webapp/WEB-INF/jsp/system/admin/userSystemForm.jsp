<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>表单页</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<script type="text/javascript" src="${assets}/resource/common/js/md5.js"></script>
<script type="text/javascript" src="${assets}/resource/thirdparty/jquery-validation-1.13.1/jquery.validate.min.js"></script>
 <style>
 	label.error{
 		color:red;
 		text-align: left;
 		margin-bottom: -5px;
 	}
 </style>
<script type="text/javascript">
	$(function() {
		//jquery validator
		$("#userForm").validate({
			submitHandler : function(form) {
				
				var pwd = $('#pwd').val();
				if(pwd){
					$('#md5pwd').val(CryptoJS.MD5(md5_salt+pwd));
				}
				form.submit();
			},
			//设置规则
			rules : {
				username : {
					required: true,
					minlength: 3
				},
				phone :  {
					minlength: 11,
					maxlength: 11,
					digits:true
				},
				email :  {
					email: true,
					maxlength: 63
				}
			},
			//设置错误信息
			messages : {
				username : {
					required : "请填写用户名",
					minlength : $.validator.format("用户名长度不能小于{0}个字符")
				},
				phone : {
					required : "请填写手机号",
					minlength : $.validator.format("请输入11位手机号"),
					maxlength : $.validator.format("请输入11位手机号"),
					digits:"手机号请只填写数字"
				},
				email : {
					required : "请填写邮箱",
					email: "邮箱格式不对",
					maxlength : $.validator.format("邮箱长度不能大于{0}个字符")
				}
			}
			,errorPlacement: function(error, element) { //指定错误信息位置 
				if ( element.is(":radio") ){
					error.appendTo(element.parent().parent().children(".help-inline").empty()); //将错误信息添加当前元素的父结点后面 
				}else{
					error.appendTo(element.parent().children(".help-inline").empty()); //将错误信息添加当前元素的父结点后面 
				}
			}
		});

	});
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
					<li><a href="${ctx }/system/user/showList.do">用户管理</a><span class="divider">/</span></li>
				</ul>
				<h4>用户管理</h4>
			</div>
		</div>
		<div class="pull-right menu-right">

		</div>
	</div>
	
	
	<!-- 主内容区 -->
	<div class="contentpanel">
		<div class="row">
			<div class="widget box">
				<div class="widget-header"> <h4><i class="fa fa-reorder"></i>系统账号管理</h4> </div>
	            <div class="widget-content">
		            <c:if test="${message!=null}">
						<div class="alert alert-${messageType }">
			            	${message}
						</div>
					</c:if>
	            	<form id="userForm" class="form-horizontal row-border" action="" method="post">
	            		
	            		<div class="control-group">
	                        <label class="control-label" for="username"><span class="txt-impt">*</span>用户名：</label>
	                        <div class="controls">
	                          <input type="text" name="username" id="username" class="span4" value="${user.username }" />
	                          <span class="help-inline">不少于6个字符(数字、字母、下划线)</span>
	                        </div>
	                    </div>
	                     <div class="control-group">
	                        <label class="control-label" for="pwd">密码：</label>
	                        <div class="controls">
	                          <input type="password" name="pwd" id="pwd" class="span4" value="" />
	                          <input type="hidden" name="md5pwd" id="md5pwd" class="span4" value="" />
	                          <span class="help-inline"><c:if test="${op=='修改' }">若不修改密码，此处留空</c:if></span>
	                        </div>
	                    </div>
	                    <div class="control-group">
	                        <label class="control-label" for="phone"><span class="txt-impt">*</span>手机号：</label>
	                        <div class="controls">
	                          <input type="text" name="phone" id="phone" class="span4" value="${user.phone }" />
	                          <span class="help-inline"></span>
	                        </div>
	                    </div>
	                    <div class="control-group">
	                        <label class="control-label" for="email"><span class="txt-impt">*</span>邮箱：</label>
	                        <div class="controls">
	                          <input type="text" name="email" id="email" class="span4" value="${user.email }" />
	                          <span class="help-inline"></span>
	                        </div>
	                    </div>
	                    <div class="control-group">
	                        <label class="control-label" for="sex"><span class="txt-impt">*</span>性别：</label>
	                        <div class="controls">
	                      		<label class="radio inline"><input name="sex" type="radio" value="1" <c:if test="${user.sex eq '1' }">checked="checked"</c:if>  />男</label>
								<label class="radio inline"><input name="sex" type="radio" value="2" <c:if test="${user.sex eq '2' }">checked="checked"</c:if>  />女</label>
	                          <span class="help-inline"></span>
	                        </div>
	                    </div>
	                    <div class="control-group">
	                        <label class="control-label" for="roleId"><span class="txt-impt">*</span>权限：</label>
	                        <div class="controls">
					 			<select name="roleId" >
									<c:forEach items="${roleMap}" var="entry"> 
										<c:if test="${entry.key==user.roleId}"> 
											<option value='${entry.key}' selected >${entry.value}</option>
										</c:if>
										<c:if test="${entry.key!=user.roleId}"> 
											<option value="${entry.key}" >${entry.value}</option>
										</c:if> 
									</c:forEach> 
								</select>
								<span class="help-inline"></span>
	                        </div>
	                    </div>
	                    <div class="control-group form-actions">
	                        <div class="span7">
	                        	<input type="submit" class="btn btn-primary btn-medium" value="${op }">
	                        	<input type="button" class="btn btn-default btn-medium" value="返回" onclick="javascript:backToList('${ctx }/system/user/showList.do', '${pn}', '');" />
	                        </div>
	                    </div>
	            	</form>
	            </div>
			</div>
		</div>
	</div>
</body>
</html>
