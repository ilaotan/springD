<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>修改密码</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<style type="text/css">
	label.error{
		color:red;
		text-align: left;
	}
	.ywz_zhuce_huixian {
		width: 115px;
	}
	.ywz_zhuce_hongxian {
		width: 115px;
	}
	.ywz_zhuce_hongxian2 {
		width: 115px;
	}
	.ywz_zhuce_hongxian3{
		width: 115px;
	}
	.ywz_zhuce_hongxianwenzi{
		width: 115px;
	}
	.ywz_zhuce_hongxianwenzi{
		width: 115px;
	}
	.ywz_zhuce_hongxianwenzi{
		width: 115px;
	}
	.ywz_zhuce_hongxianwenzi{
		width: 115px;
	}
</style>
<script type="text/javascript" src="${assets}/resource/common/js/md5.js"></script>
<script type="text/javascript" src="${assets}/resource/thirdparty/jquery-validation-1.13.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${assets}/resource/common/js/placeholders.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#changePwdForm").validate({
		submitHandler : function(form) {
			if (_r == null || _r < 2) {
				alert("密码强度过低");
				return false;
			}
			// 密码第一次加密
			var pwd = $('#oldPwd').val();
			$('#hopassword').val(CryptoJS.MD5(md5_salt+pwd));
			pwd = $('#newPwd').val();
			$('#hpassword').val(CryptoJS.MD5(md5_salt+pwd));
			form.submit();
		},
		//设置规则
		rules : {
			oldPwd :  {
				required: true
			},
			newPwd :  {
				required: true,
				minlength: 6,
				maxlength: 20
			},
			reNewPwd :  {
				required: true,
				equalTo: "#newPwd"
			}
		},
		//设置错误信息
		messages : {
			oldPwd : {
				required : "请填写旧密码"
			},
			newPwd : {
				required : "请填写新密码",
				minlength : $.validator.format("密码长度不能小于{0}个字符"),
				maxlength : $.validator.format("密码长度不能大于{0}个字符")
			},
			reNewPwd : {
				required : "请填写确认密码",
				equalTo : "密码填写不一致"
			}
		}
		,errorPlacement: function(error, element) { //指定错误信息位置 
			error.appendTo(element.parent().children(".help-inline").empty()); //将错误信息添加当前元素的父结点后面 
		}
	});

	$('#newPwd').focus(function () {
		$('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
		$('#newPwd').keyup();
	});
	
	$('#newPwd').keyup(function () {
		var __th = $(this);
		// 没有任何输入
		if (!__th.val()) {
			Primary();
			return;
		}
		// 不足六位一定是“弱”
		if (__th.val().length < 6) {
			Weak();
			return;
		}
		_r = checkPassword(__th);
		if (_r < 1) {
			Primary();
			return;
		}
		if (_r > 0 && _r < 2) {
			Weak();
		} else if (_r >= 2 && _r < 4) {
			Medium();
		} else if (_r >= 4) {
			Tough();
		}
	});
});
</script>
</head>
<body>
<div class="pageheader">
    <div class="media pull-left">
        <div class="pageicon pull-left">
            <i class="fa fa-home"></i>
        </div>
        <div class="media-body">
            <ul class="breadcrumb">
                <li><a href="${ctx }/system/admin"><i class="fa fa-home"></i></a><span class="divider">/</span></li>
            </ul>
            <h4>修改密码</h4>
        </div>
    </div>
    <div class="pull-right menu-right">
        <%-- <a href="" class="btn">刷新</a> --%>
    </div>
</div>
<div class="contentpanel">
	<div class="row">
		<div class="widget box">
			<div class="widget-header"> <h4><i class="fa fa-reorder"></i>修改密码</h4> </div>
            <div class="widget-content">
	            <c:if test="${message!=null}">
					<div class="alert alert-danger">
		            	${message}
					</div>
				</c:if>
            	<form id="changePwdForm" class="form-horizontal row-border" action="" method="post">
            		<div class="control-group">
                        <label class="control-label" for="oldPwd"><span class="txt-impt">*</span>旧密码：</label>
                        <div class="controls">
                          <input type="password" name="oldPwd" id="oldPwd" class="span4">
                          <input type="hidden" name="hopassword" id="hopassword"/>
                          <span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="newPwd"><span class="txt-impt">*</span>新密码：</label>
                        <div class="controls">
                          <input type="password" name="newPwd" id="newPwd" class="span4" >
                          <input type="hidden" name="hpassword" id="hpassword"/>
                          <span class="help-inline">6-20位（区分大小写）</span>
                          <div style="width:370px;">
	                          <div id="pwdLevel_1" class="ywz_zhuce_huixian"> </div>
	                          <div id="pwdLevel_2" class="ywz_zhuce_huixian"> </div>
	                          <div id="pwdLevel_3" class="ywz_zhuce_huixian"> </div>
	                          <div class="ywz_zhuce_hongxianwenzi"> 弱</div>
	                          <div class="ywz_zhuce_hongxianwenzi"> 中</div>
	                          <div class="ywz_zhuce_hongxianwenzi"> 强</div>
                          </div>
                        </div>
                    </div>
                   <div class="control-group">
                        <label class="control-label" for="reNewPwd"><span class="txt-impt">*</span>新密码确认：</label>
                        <div class="controls">
                          <input type="password" name="reNewPwd" id="reNewPwd" class="span4">
                          <span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group form-actions">
                        <div class="span7" style="margin-left: 92px;">
                        	<input type="submit" class="btn btn-primary btn-medium" value="保存">
                        </div>
                    </div>
            	</form>
            </div>
		</div>
	</div>
<!-- /主内容区 -->
</div>
</body>
</html>
