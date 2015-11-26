<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>操作提示</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<style type="text/css">
body{font:12px/1.7 "\5b8b\4f53",Tahoma;}
html,body,div,p,a,h3{margin:0;padding:0;}
.tips_wrap{ background:#F7FBFE;border:1px solid #DEEDF6;width:90%;padding:50px;margin:50px auto 0;}
.tips_inner{zoom:1;}
.tips_inner:after{visibility:hidden;display:block;font-size:0;content:" ";clear:both;height:0;}
.tips_inner .tips_img{width:80px;float:left;}
.tips_info{float:left;line-height:35px;width:650px}
.tips_info h3{font-weight:bold;color:#1A90C1;font-size:16px;}
.tips_info p{font-size:14px;color:#999;}
.tips_info p.message_error{font-weight:bold;color:#F00;font-size:16px; line-height:22px}
.tips_info p.message_success{font-weight:bold;color:#1a90c1;font-size:16px; line-height:22px}
.tips_info p.return{font-size:12px}
.tips_info .time{color:#f00; font-size:14px; font-weight:bold}
.tips_info p a{color:#1A90C1;text-decoration:none;}
</style>
</head>
		
<body>
<script type="text/javascript">
 function delayURL(url) {
    var delay = document.getElementById("time").innerHTML;
    //alert(delay);
    if(delay > 0){
	    delay--;
	    document.getElementById("time").innerHTML = delay;
	} else {
		art.dialog.close();
	}
	setTimeout("delayURL(\'" + url + "\')", 1000);
 }
</script>
<div class="tips_wrap">
    <div class="tips_inner">
        <div class="tips_img">
            <img src=""/>
        </div>
        <div class="tips_info">
            <p class="message_success">${message }</p>
            <p class="return">系统在  <span class="time" id="time">4</span>  秒后自动关闭，如果不想等待，<a href="javascript:void(0)" onclick="closeDialog()">点击这里关闭</a></p>
        </div>
    </div>
</div>
<script type="text/javascript">
    delayURL("${ctx }${nextUrl }");
</script>
</body>
</html>