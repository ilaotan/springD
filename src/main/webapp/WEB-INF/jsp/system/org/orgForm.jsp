<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>组织机构新增</title>
<link href="${ctx}/resource/thirdparty/ztree3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resource/common/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/resource/thirdparty/jquery-validation-1.13.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/resource/thirdparty/ztree3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
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
	
	//组织结构树
	var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
					pIdKey: "parentId"
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};

		function beforeClick(treeId, treeNode) {
//			var check = (treeNode && !treeNode.isParent);
//			if (!check) alert("只能选择城市...");
//			return check;
		}
		
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes(),
			v = "",
			id = "";
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				id += nodes[i].id + ",";
				v += nodes[i].name + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (id.length > 0 ) id = id.substring(0, id.length-1);
			$("#parentName").val(v);
			$("#parentId").val(id);
			hideMenu();
		}

		function showMenu() {
			var cityObj = $("#parentName");
			var cityOffset = $("#parentName").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		//ajax 载入tree数据
//		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		var orgTree = "";
		$("#menuBtn").click(function(){
			if(orgTree!=""){
				showMenu();
			}else{
				var url = "${ctx}/org/jsonTreeData.do";
				$.post(url,{},function(data){
					orgTree = data;
					$.fn.zTree.init($("#treeDemo"), setting, orgTree);
					showMenu();
				});
			}
		});
});
</script>
</head>
<body>
<div>
<a href="add.do">新增信息</a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="javascript:void(0)" id="ajaxTest">ajax异常测试</a>
</div>
<div class="msg">${message }</div>
<div>
<form action="${ctx }/org/save.do" method="post" id="myform">
<p>
<label>上级组织：</label>
<input type="text" name="parentName" value="${pOrgName }" id="parentName"/>
<a id="menuBtn" href="#" >选择</a>
<input type="hidden" name="parentId" value="${pOrgId }" id="parentId"/>
</p>
<p>
<label>组织名称：</label>
<input type="text" name="name" value="${org.name }"/>
</p>

<p>
<label>类型：</label>
<input type="radio" name="type" value="0"/>幼儿园
<input type="radio" name="type" value="1"/>管理
</p>

<p>
<label>状态：</label>
<input type="radio" name="status" value="0"/>禁用
<input type="radio" name="status" value="1"/>正常
</p>

<p>
<label>备注：</label>
<textarea rows="5" cols="40" name="remark" required>${org.remark }</textarea>
</p>
<p>
<input type="submit" value="提交"/>
</p>
<input type="hidden" name="id" value="${org.id }"/>
</form>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;border:1px solid #ddd;background:#fff">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
</body>
</html>