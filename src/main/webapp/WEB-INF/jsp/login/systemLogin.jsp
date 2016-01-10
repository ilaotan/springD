<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>系统管理员登录</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<script type="text/javascript" src="${assets}/resource/common/js/cookie.js"></script>
<script	type="text/javascript" src="${assets}/resource/thirdparty/jquery-validation-1.13.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${assets}/resource/common/js/md5.js"></script>
<script type="text/javascript">
if(window.top !== window.self){ window.top.location = window.location;}
function refreshCaptcha(){
	$(".captcha-img").attr("src", '${ctx }/captcha.svl?'+new Date().getTime());
}
$(function(){
	//如果cookie里保存了登录信息
	var loginInfo = $.cookie('ibabyuser');
	if(loginInfo!=''&&loginInfo!=null){
		var loginArr = loginInfo.split('@@');
		$('#username').val(loginArr[0]);
		$('#password').val(loginArr[1]);
		$('#remember').attr('checked','checked');
		$('#password').show();
		$('#fake_pass').hide();
	}else{
		$('#password').hide();
		$('#fake_pass').show();
	}
	$('#submitBtn').click(function(){
		$(this).button('loading');
	});
	$('#password').change(function(){
		$('#ischange').val(1);
	});
	
	// fix IE8下 password的placeholder不能正确显示提示

	// On focus of the fake password field
	$('#fake_pass').focus(function(){
	    $(this).hide(); //  hide the fake password input text
	    $('#password').show().focus(); // and show the real password input password
	});

	// On blur of the real pass
	$('#password').blur(function(){
	    if($(this).val() == ""){ // if the value is empty, 
	        $(this).hide(); // hide the real password field
	        $('#fake_pass').show(); // show the fake password
	    }
	    // otherwise, a password has been entered,
	    // so do nothing (leave the real password showing)
	});
	
	$('#loginForm').validate({
		submitHandler: function(form){
			//ajax 登录
			//form.submit();
			var ctx = "${ctx}";
			var username = $('#username').val();
			var password = $('#password').val();
			if($('#ischange').val()=='1'){
				var pwd = CryptoJS.MD5(md5_salt+password);
				password = pwd.toString();
			}
			var captcha = $('#captcha').val();
			var remember = $('#remember').is(":checked");
			if(!remember){
				$.cookie('ibabyuser',null);
			}
			var url = '${ctx}/system/login';
 			$.post(url,{username:username,password:password,captcha:captcha},function(data){
				//console.log(data);
				if(data.success){
					//登录成功是否记住密码
					if(remember){
						$.cookie('ibabyuser',username+ '@@' + password,{expires:360});
					}
					//登录成功跳转
					window.location.href = ctx + data.successUrl;
				}else{
					$('div.control-group').removeClass('success');
					if(data.type == 'username'){
						$('#sysMsg').html("<label id='username-error' class='error' for='username'><i class='fa fa-exclamation-circle'></i> " + data.message + "</label>").show();
					}else if(data.type == 'password'){
						$('#sysMsg').html("<label id='password-error' class='error' for='password'><i class='fa fa-exclamation-circle'></i> " + data.message + "</label>").show();
					}else if(data.type == 'captcha'){
						$('#sysMsg').html("<label id='captcha-error' class='error' for='captcha'><i class='fa fa-exclamation-circle'></i> " + data.message + "</label>").show();
					}else{
						$('#sysMsg').html("<label id='username-error' class='error' for='username'><i class='fa fa-exclamation-circle'></i> " + data.message + "</label>").show();
					}
					$('#submitBtn').button('reset');
					//是否显示验证码
					if(data.isCaptchaRequired){
						$('.captcha-group').show();
						refreshCaptcha();
					}
				}
			});
		},
		rules:{
			username:"required",
			password:{
				required:true,
			},
			captcha:{
				required:true,
			},
		},
		messages:{
			username:{required:"<i class='fa fa-exclamation-circle'></i> 请输入邮箱/用户名/已验证手机",},
			password:{required:"<i class='fa fa-exclamation-circle'></i> 请输入密码"},
			captcha:{required:"<i class='fa fa-exclamation-circle'></i> 请输入验证码"}
		},
//		errorElement: "span",
		//指定错误信息位置
//		errorContainer: $('#msg'),
		errorLabelContainer: $('#msg'),
//		wrapper: "li",
		success:function(element){
	        //$('#msg').hide();
	    },
	    highlight: function (element, errorClass, validClass) { 
	    	$('#sysMsg').hide();
            $(element).parents("div.control-group").addClass('error').removeClass('success'); 
        },
        unhighlight: function (element, errorClass, validClass) { 
            $(element).parents(".error").removeClass('error').addClass('success'); 
    	},
    	invalidHandler: function(even, validator){
    		$('#sysMsg').hide();
    		$('#submitBtn').button('reset');
    	}
//		errorPlacement: function(error, element) {
//			console.log(element);
//			error.appendTo($('#message').empty());
			//error.appendTo($('#message').empty()); //将错误信息添加当前元素的父结点后面 
//			if(error.text() == ''){
//				$('#message').html("ok");
//			} else {
//				$('#message').html(error.text());
//			}
//		}
	});
	
});
</script>
<style>
.panel-login .panel-body {
  padding: 35px 45px;
}
.mb30 {
  margin-bottom: 0px;
}
#sysMsg,#msg{
	display:none;
}
.captcha-group{
	display:none;
}
.remember input{
	margin:0
}
<c:if test="${isCaptchaRequired }">
.captcha-group{
	display:block;
}
</c:if>
body.login {
  background-color: #5CB85C;
}
#fake_pass{
	color:#999999;
}
</style>
</head>
<body class="login">
	<div class="panel panel-login">
    	<div class="panel-body">
        	<div class="logo text-center">

            </div>
            <div class="mb30" style="clear: left;"></div>
            <div class="alert alert-error" id="sysMsg"></div>
            <div class="alert alert-error" id="msg"></div>
            <form method="post" id="loginForm" class="form-horizontal" onsubmit="return false;">
            	<div class="control-group">
                    <div class="input-prepend">
                      <span class="add-on"><i class="fa fa-user"></i></span>
                      <input name="username" class="input-text" id="username" type="text" placeholder="用户名">
                    </div>
                </div>
            	<div class="control-group">
                    <div class="input-prepend">
                      <span class="add-on"><i class="fa fa-lock"></i></span>
                      <input type="text" class="input-text"  name="fake_pass" id="fake_pass" value="密码" style="display:none"/>
                      <input name="password" class="input-text" id="password" type="password" >
                    </div>
                </div>
                <div class="control-group captcha-group">
                    <div class="input-prepend">
                      <span class="add-on"><i class="fa fa-qrcode"></i></span>
                      <input name="captcha" class="input-text captcha" id="captcha" type="text" placeholder="验证码">
                      <img src="${ctx }/captcha.svl" onclick="this.src='${ctx }/captcha.svl?d='+new Date()*1" width="100" height="35" alt="点击更换验证码" title="点击更换验证码" class="captcha-img">
                    </div>
                </div>
                <div class="clearfix">
                	<div class="pull-left">
                    	<label for="remember" class="remember"><input type="checkbox" name="remember" id="remember">记住密码</label>
                    </div>
                    <div class="pull-right" style="display:none"><a href="${ctx }/anon/findPwd">忘记密码</a></div>
                </div>
                <div class="control-group mt20 text-center">
                	<input type="hidden" name="ischange" id="ischange" value="0">
                	<input type="submit" class="btn btn-success btn-block" id="submitBtn" data-loading-text="正在登录..." value="登　录">
                </div>
            </form>
        </div>
    </div>
</body>
</html>