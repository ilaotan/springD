<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>系统管理平台</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<script type="text/javascript">
if(window.top !== window.self){ window.top.location = window.location;}
</script>
<style type="text/css">
body.login {
  /* background-color: #428bca; */
  background-color: #5CB85C;
}
.btn-primary {
  background-image: linear-gradient(to bottom,#5C885C,#5CB85C);
}
.navbar-inner {
  background-color: #5CB85C;
}
.header-right .nav {
  border-left: 1px solid #3cb371;
}
.leftpanel .nav > li.active > a {
  background-color: #5CB85C;
}
.navbar .nav > li:first-child {
  border-left: 1px solid #fff;
}
.dropdown-menu>li>a:hover,.dropdown-menu>li>a:focus,.dropdown-submenu:hover>a,.dropdown-submenu:focus>a {
    background-color: #5CB85C;
    background-image: -moz-linear-gradient(top,#5B5,#5CB85C);
    background-image: -webkit-gradient(linear,0 0,0 100%,from(#5B5),to(#5CB85C));
    background-image: -webkit-linear-gradient(top,#5B5,#5CB85C);
    background-image: -o-linear-gradient(top,#5B5,#5CB85C);
    background-image: linear-gradient(to bottom,#5B5,#5CB85C);
}
.leftpanel .nav > li > a b {
  border-top: 6px solid #5CB85C;
}
.leftpanel .nav > li > a i {
  color: #5CB85C;
}
.leftpanel .nav > li > a:hover, .leftpanel .nav > li > a:active,.leftpanel .nav > li > a:focus {
	border-left: 5px solid #6CD85C;
}
.navbar .brand {
  margin-left: 6px;
  color: #fff;
  line-height: 27px;
  height: 32px;
}
</style>
</head>

<body scroll="no" style="overflow-y:hidden">
<!-- header -->
<div id="header">
	<div class="navbar">
    	<div class="navbar-inner">
        	<div class="container">
                <div class="header-left">
                    <a class="brand">系统管理平台</a>
                    <div class="pull-right">
                        <a href="" class="menu-collapse"><i class="fa fa-bars"></i></a>
                    </div>
                </div>
            	<div class="header-right">
                	<ul class="nav pull-left"></ul>
                    <ul class="nav pull-right">
                    	<li class="">
                        </li>
                        <li class="account dropdown">
                        	<a data-toggle="dropdown" href="" class="label"><i class="fa fa-user"></i>${user.name}<i class="caret"></i></a>
                            <ul class="dropdown-menu pull-right">
                                <li><a href="${ctx }/system/admin/changePwd.do" target="win" class="glyphicons camera">修改密码<i></i></a></li>
                                <li class="divider"></li>
                                <li><a href="${ctx }/logout.do?type=system" class="glyphicons camera">退出账号<i></i></a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /header -->
<!-- main content -->
<div id="content">
    <div class="container">
    	<!-- left panel -->
    	<div class="leftpanel" id="leftpanel">
        	<div class="shortcut">
                <div class="media profile">
                	<a href="#" class="pull-left profile-thumb">
                		<img src="${assets }/resource/system/images/admin/avatar.png" class="img-circle">
                	</a>
                	<div class="media-body">
                		<h4 class="media-heading">系统管理平台</h4>
                		<small class="text-muted">欢迎您！</small>
                		<ul class="inline" style="display:none;">
                			<li><a href="" class="btn btn-small tip" data-toggle="tooltip" title="返回首页"><i class="icon-home"></i></a></li>
                		</ul>
                	</div>
                </div>
            </div>
          <ul class="menu nav nav-tabs nav-stacked" id="menu">
                <shiro:hasPermission name="system:permission:view">
                <li class="parent">
                	<a href="javascript:void(0)" target="win"><i class="fa fa-lock"></i><span>权限管理</span><b class="caret"></b></a>
               		<ul class="children">
               			<shiro:hasPermission name="system:menu:list">
                    	<li><a href="${ctx }/system/menu/list.do" target="win">菜单管理</a></li>
               			</shiro:hasPermission>
               			<shiro:hasPermission name="system:role:list">
                    	<li><a href="${ctx }/system/role/list.do" target="win">角色管理</a></li>
               			</shiro:hasPermission>
               			<shiro:hasPermission name="system:usersystem:list">
                    	<li><a href="${ctx }/system/usersystem/showList.do" target="win">系统账号管理</a></li>
               			</shiro:hasPermission>
                    </ul>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="system:userManager:view">
                <li class="parent"><a href="javascript:void(0)" class=""><i class="fa fa-child"></i><span>用户管理</span><b class="caret"></b></a>
                	<ul class="children">
                	<shiro:hasPermission name="system:user:showList">
                    	<li><a href="${ctx }/system/user/showList.do" target="win">用户列表</a></li>
                	</shiro:hasPermission>
                    </ul>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="system:dataManager:view">
                 <li class="parent">
                	<a href="javascript:void(0)" target="win"><i class="fa fa-wrench"></i><span>系统维护</span><b class="caret"></b></a>
               		<ul class="children">
               		<shiro:hasPermission name="system:druid:index">
                    	<li><a href="${ctx }/system/druid/index.html" target="win">数据库监控</a></li>
               		</shiro:hasPermission>
                    </ul>
                </li>
                </shiro:hasPermission>
            </ul>
        </div>
        <!-- /left panel -->
        <div class="main" id="main">
        	<!-- iframe区域 -->
			<iframe src="${ctx }/system/welcome.do" name="win" id="win" width="100%" height="100%" frameborder="0"></iframe>
            <!-- /iframe区域 -->
        </div>
    </div>
</div>
<!-- /main content -->
<script type="text/javascript">
window.onload =window.onresize= function(){winresize();}
function winresize()
{
	function $(s){return document.getElementById(s);}
	var D=document.documentElement||document.body,h=D.clientHeight-65,w=D.clientWidth-205;
	//alert(h);
	$("main").style.height=h+"px";
	$("leftpanel").style.height=h+"px";
	//$("content").style.width=w+"px";
	$("content").style.height=h+"px";
}

</script>
</body>
</html>