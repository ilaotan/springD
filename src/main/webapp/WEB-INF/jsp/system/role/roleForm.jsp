<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>角色新增</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<script type="text/javascript" src="${assets}/resource/thirdparty/jquery-validation-1.13.1/jquery.validate.min.js"></script>
<script type="text/javascript">
//组织树

$(function(){
	//jquery validator
	$("#myform").validate({
		submitHandler: function(form){
			//loading('正在提交，请稍等...');
			form.submit();
		},
		//设置规则
		rules:{
			name:"required",
			age:{
				required:true,
			},
		},
		//设置错误信息
		messages:{
			name:{
				required:"请填写姓名",
			},
			age:{
				required:"请填写年龄"
			}
		},
	});
	
});
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
                <li><a href="${ctx }/system/role/list.do">角色管理</a><span class="divider">/</span></li>
            </ul>
            <h4>角色信息</h4>
        </div>
    </div>
    <div class="pull-right menu-right">
        <%-- <a href="" class="btn">刷新</a>
        <a href="javascript:void(0)" class="btn btn-primary" id="edit"><i class="fa fa-edit"></i>修改信息</a> --%>
    </div>
</div>
<!-- /页头 -->

<div class="contentpanel">
	<div class="row">
		<div class="widget box">
			<div class="widget-header"> <h4><i class="fa fa-reorder"></i>角色信息</h4> </div>
            <div class="widget-content">
	            <c:if test="${message!=null}">
					<div class="alert alert-${messageType }">
		            	${message}
					</div>
				</c:if>
            	<form action="${ctx }/system/role/save.do" class="form-horizontal row-border" method="post" id="myform">

                    <div class="control-group">
                        <label class="control-label" for="name"><span class="txt-impt">*</span>角色名称：</label>
                        <div class="controls">
                          <input type="text" name="name" id="name" value="${role.name }"/>
                          <span class="help-inline">2-20个字符</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="code"><span class="txt-impt">*</span>角色code：</label>
                        <div class="controls">
                         	<input type="text" name="code" id="code" value="${role.code }"/>
                         	<span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="status"><span class="txt-impt">*</span>角色状态：</label>
                        <div class="controls">
                         	<label class="radio inline"><input type="radio" name="status" value="1" <c:if test="${role.status eq '1'}">checked="checked"</c:if> />正常</label>
							<label class="radio inline"><input type="radio" name="status" value="0" <c:if test="${role.status eq '0'}">checked="checked"</c:if> />禁用</label>
                         	<span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="code"><span class="txt-impt">*</span>备注：</label>
                        <div class="controls">
                         	<input type="text" name="remark" id="remark" value="${role.remark }"/>
                         	<span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group form-actions">
                        <div class="span7">
                        	<input type="submit" class="btn btn-primary btn-medium" value="${op }" />
                        	<input type="button" class="btn btn-default btn-medium" value="返回" onclick="javascript:backToList('${ctx }/system/role/list.do', '', '');" />
                        </div>
                    </div>
                    <input type="hidden" name="id" id="id" value="${role.id }"/>
            	</form>
            </div>
		</div>
	</div>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;border:1px solid #ddd;background:#fff">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
</body>
</html>