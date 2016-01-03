<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@taglib prefix="permfn" uri="http://github.com/chenz/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<title>权限分配</title>
<%@ include file="/WEB-INF/common/assets.jsp"%>
<link href="${ctx}/resource/thirdparty/ztree3.5.12/css/zTreeStyle/zTreeStyle.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resource/thirdparty/ztree3.5.12/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>

<script type="text/javascript">
//组织树
        $(function () {
            var setting = {
                check: {
                    enable: true ,
                    chkboxType: { "Y": "ps", "N": "ps"}
                },
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: onCheck
                }
            };

            var zNodes =[
                <c:forEach items="${menuList}" var="m">
	                { id:${m.id}, pId:${m.parentId}, name:"${m.name}", checked:${permfn:isIn(role.menuIds, m.id)}},
                </c:forEach>
            ];

            function onCheck(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree"),
                        nodes = zTree.getCheckedNodes(true),
                        id = "",
                        name = "";
                nodes.sort(function compare(a,b){return a.id-b.id;});
                for (var i=0, l=nodes.length; i<l; i++) {
                    id += nodes[i].id + ",";
                    name += nodes[i].name + ",";
                }
                if (id.length > 0 ) id = id.substring(0, id.length-1);
                if (name.length > 0 ) name = name.substring(0, name.length-1);
                $("#menuIds").val(id);
                $("#resourceName").val(name);
//                hideMenu();
            }

            function showMenu() {
                var cityObj = $("#resourceName");
                var cityOffset = $("#resourceName").offset();
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

            $.fn.zTree.init($("#tree"), setting, zNodes);
            $("#menuBtn").click(showMenu);
        });
</script>
</head>
<body>
<div>
<ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>
<form action="${ctx }/system/role/savePermission.do" method="post">
<input type="hidden" name="id" id="id" value="${role.id }"/>
<input type="hidden" name="menuIds" id="menuIds" value="${role.menuIds }"/>
<input type="submit" value="提交" />
</form>
</body>
</html>