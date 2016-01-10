<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>404|页面未找到</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<style type="text/css">
.error-container {
  width: 500px;
  margin: 50px auto 0px;
}
.error-container .error-code {
  float: left;
  width: 100%;
  font-size: 135px;
  line-height: 130px;
  text-align: center;
  color: #333;
  font-weight: 300;
}
.error-container .error-text {
  float: left;
  width: 100%;
  margin-top: 10px;
  font-size: 26px;
  line-height: 24px;
  text-transform: uppercase;
  color: #666;
  text-align: center;
  font-weight: 400;
}
.error-container .error-subtext {
  float: left;
  width: 100%;
  margin: 30px 0px 10px;
  font-size: 13px;
  line-height: 20px;
  color: #AAA;
  text-align: center;
  font-weight: 400;
}
.error-container .error-actions {
  	float: left;
	margin-left: 35px;
	width: 100%;
	margin-top: 10px;
}
</style>
</head>
<body>
<div class="error-container">
      <div class="error-code">404</div>
      <div class="error-text">很抱歉，您要访问的页面不存在</div>
      <div class="error-subtext">温馨提示：请检查您访问的网址是否正确或者联系管理员请求帮助.</div>
      <div class="error-actions">                                
          <div class="row">
              <div class="span2">
                  <button class="btn btn-info btn-block" onclick="document.location.href = 'admin';">返回首页</button>
            </div>
            <div class="span2">
                <button class="btn btn-primary btn-block" onclick="history.back();">返回上一页</button>
            </div>
        </div>                                
    </div>
</div>
</body>
</html>