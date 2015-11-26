<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>菜单新增</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<link href="${ctx}/resource/thirdparty/ztree3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resource/thirdparty/ztree3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${assets}/resource/thirdparty/jquery-validation-1.13.1/jquery.validate.min.js"></script>
<style type="text/css">
	label.error{
		color:red;
		text-align: left;
	}
</style>
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
			url:{
				required:true,
			},
			listorder:{
				digits:true,
				min:0,
			}
		},
		//设置错误信息
		messages:{
			name:{
				required:"请填写菜单名称",
			},
			url:{
				required:"请填写菜单URL",
			},
			listorder:{
				digits:"请输入正整数",
			}
		},
		//指定错误信息位置 
		errorPlacement: function(error, element) {
			error.appendTo(element.parent().next().children(".help-inline").empty()); //将错误信息添加当前元素的父结点后面 
		}
	});
	
	//组织结构树
	var setting = {
		view: {
			dblClickExpand: false,
			selectedMulti:false
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
		
		if (treeNode.id == $("input[name='id']").val()) {
			alert('请不要选择本菜单项作为“上级菜单”');
			return false;
		}
		return treeNode;
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
	var orgTree = "";
	$("#menuBtn").click(function(){
		if(orgTree!=""){
			showMenu();
		}else{
			var url = "${ctx}/system/menu/jsonTreeData.do";
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
<!-- main content -->
<!-- 页头 -->
<div class="pageheader">
    <div class="media pull-left">
        <div class="pageicon pull-left">
            <i class="fa fa-home"></i>
        </div>
        <div class="media-body">
            <ul class="breadcrumb">
                <li><a href="${ctx }/system/admin.do"><i class="fa fa-home"></i></a><span class="divider">/</span></li>
                <li><a href="${ctx }/system/menu/list.do">菜单管理</a><span class="divider">/</span></li>
            </ul>
            <h4>菜单信息</h4>
        </div>
    </div>
    <div class="pull-right menu-right">
        <%-- <a href="" class="btn">刷新</a>
        <a href="javascript:void(0)" class="btn btn-primary" id="edit"><i class="fa fa-edit"></i>修改信息</a> --%>
    </div>
</div>
<!-- /页头 -->
<!-- 主内容区 -->
<div class="contentpanel">
	<div class="row">
		<div class="widget box">
			<div class="widget-header"> <h4><i class="fa fa-reorder"></i>菜单信息</h4> </div>
            <div class="widget-content">
	            <c:if test="${message!=null}">
					<div class="alert alert-${messageType }">
		            	${message}
					</div>
				</c:if>
            	<form action="${ctx }/system/menu/save.do" class="form-horizontal row-border" method="post" id="myform">
            		<div class="control-group">
                        <label class="control-label" for="parentName"><span class="txt-impt">*</span>上级菜单：</label>
                        <div class="controls">
							<input type="text" name="parentName" value="${pMenuName }" id="parentName" readonly="readonly"/>
							<a id="menuBtn" href="javascript:void(0);" >选择</a>
							<input type="hidden" name="parentId" value="${pMenuId }" id="parentId"/>
                          	<span class="help-inline">2-10个字符</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="name"><span class="txt-impt">*</span>菜单名称：</label>
                        <div class="controls">
                          <input type="text" name="name" value="${menu.name }"/>
                          <span class="help-inline">2-20个字符</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="url"><span class="txt-impt">*</span>菜单url：</label>
                        <div class="controls">
                         	<input type="text" name="url" value="${menu.url }"/>
                         	<span class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group form-actions">
                        <div class="span7">
                        	<input type="submit" class="btn btn-primary btn-medium" value="${op }">
                        	<input type="button" class="btn btn-default btn-medium" value="返回" onclick="javascript:backToList('${ctx }/system/menu/list.do', '', '');" />
                        </div>
                    </div>
                    <input type="hidden" name="id" value="${menu.id }"/>
					<input type="hidden" name="parentIds" value="${menu.parentIds }"/>
            	</form>
            </div>
		</div>
	</div>
<!-- /主内容区 -->
</div>
<!-- /main content -->
<div id="menuContent" class="menuContent" style="display:none; position: absolute;border:1px solid #ddd;background:#fff">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
</body>
</html>