<%--
  Created by IntelliJ IDEA.
  User: Fesine
  Date: 2018/1/14
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <link rel="stylesheet" href="../../ztree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="../../assets/css/bootstrap-duallistbox.min.css" type="text/css">
    <script type="text/javascript" src="../../ztree/jquery.ztree.all.min.js"></script>
    <script type="text/javascript"
            src="../../assets/js/jquery.bootstrap-duallistbox.min.js"></script>
    <style type="text/css">
        .bootstrap-duallistbox-container .moveall, .bootstrap-duallistbox-container .removeall {
            width: 50%;
        }

        .bootstrap-duallistbox-container .move, .bootstrap-duallistbox-container .remove {
            width: 49%;
        }
    </style>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        角色管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护角色与用户, 角色与权限关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            角色列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 role-add"></i>
            </a>
        </div>
        <div id="roleList"></div>
    </div>
    <div class="col-sm-9">
        <div class="tabbable" id="roleTab">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" href="#roleAclTab">
                        角色与权限
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#roleUserTab">
                        角色与用户
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="roleAclTab" class="tab-pane fade in active">
                    <ul id="roleAclTree" class="ztree"></ul>
                    <button class="btn btn-info saveRoleAcl" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>
                </div>

                <div id="roleUserTab" class="tab-pane fade">
                    <div class="row">
                        <div class="box1 col-md-6">待选用户列表</div>
                        <div class="box1 col-md-6">已选用户列表</div>
                    </div>
                    <select multiple="multiple" size="10" name="roleUserList" id="roleUserList">
                    </select>
                    <div class="hr hr-16 hr-dotted"></div>
                    <button class="btn btn-info saveRoleUser" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-role-form" style="display: none;">
    <form id="roleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer"
               role="grid">
            <tr>
                <td><label for="roleName">名称</label></td>
                <td>
                    <input type="text" name="name" id="roleName" value=""
                           class="text ui-widget-content ui-corner-all">
                    <input type="hidden" name="id" id="roleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="roleType">角色</label></td>
                <td>
                    <select id="roleType" name="type" data-placeholder="角色"
                            style="width: 150px;">
                        <option value="1">管理员</option>
                        <option value="2">其他</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="roleStatus">状态</label></td>
                <td>
                    <select id="roleStatus" name="status" data-placeholder="状态"
                            style="width: 150px;">
                        <option value="1">可用</option>
                        <option value="0">冻结</option>
                    </select>
                </td>
            </tr>
            <td><label for="roleRemark">备注</label></td>
            <td><textarea name="remark" id="roleRemark" class="text ui-widget-content ui-corner-all"
                          rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script id="roleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#roleList}}
        <li class="dd-item dd2-item role-name" id="role_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green role-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red role-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/roleList}}
</ol>


</script>

<script id="selectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}" selected="selected">{{username}}</option>
{{/userList}}


</script>

<script id="unSelectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}">{{username}}</option>
{{/userList}}


</script>

<script type="application/javascript">
    //存储角色列表
    var roleList;
    //缓存角色
    var roleMap = {};
    var lastRoleId = -1;
    var selectFirstTab = true;
    var hasMultiSelect = false;
    //通过id获取元素
    var roleListTemplate = $('#roleListTemplate').html();
    //通过Mustache渲染
    Mustache.parse(roleListTemplate);
    loadRoleList();

    var selectedUsersTemplate = $("#selectedUsersTemplate").html();
    Mustache.parse(selectedUsersTemplate);
    var unSelectedUsersTemplate = $("#unSelectedUsersTemplate").html();
    Mustache.parse(unSelectedUsersTemplate);

    // zTree
    <!-- 树结构相关 开始 -->
    var zTreeObj = [];
    var modulePrefix = 'm_';
    var aclPrefix = 'a_';
    var nodeMap = {};

    var setting = {
        check: {
            enable: true,
            chkDisabledInherit: true,
            chkboxType: {"Y": "ps", "N": "ps"}, //auto check 父节点 子节点
            autoCheckTrigger: true
        },
        data: {
            simpleData: {
                enable: true,
                rootPId: 0
            }
        },
        callback: {
            onClick: onClickTreeNode
        }
    };

    function onClickTreeNode(e, treeId, treeNode) { // 绑定单击事件
        var zTree = $.fn.zTree.getZTreeObj("roleAclTree");
        zTree.expandNode(treeNode);
    }

    function loadRoleList() {
        $.ajax({
            url: "/sys/role/list.json",
            success: function (result) {
                if (result.ret) {
                    roleList = result.data;
                    var rendered = Mustache.render(roleListTemplate, {
                        roleList: result.data
                    });
                    //递归渲染
                    $("#roleList").html(rendered);
                    bindRoleClick();
                    $.each(result.data, function (i, role) {
                        roleMap[role.id] = role;
                    })
                } else {
                    showMessage("加载角色列表", result.msg, false);
                }
            }
        })

    }

    $(".role-add").click(function () {
        //id选择器
        $("#dialog-role-form").dialog({
            model: true,
            title: '新增角色模块',
            open: function (event, ui) {
                //隐藏模态框自带的关闭按钮
                $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                //重置输入框
                $("#roleForm")[0].reset();
            },
            buttons: {
                "添加": function (e) {
                    //阻止默认方法
                    e.preventDefault();
                    //保存数据操作
                    updateRole(true, function (data) {
                        //关闭模态框
                        $("#dialog-role-form").dialog("close");
                    }, function (data) {
                        //提示错误信息
                        showMessage("新增角色模块", data.msg, false);
                    })
                },
                "取消": function () {
                    $("#dialog-role-form").dialog("close");
                }
            }
        });
    });

    function getTreeSelectedId() {
        var treeObj = $.fn.zTree.getZTreeObj("roleAclTree");
        debugger
        var nodes = treeObj.getCheckedNodes(true);
        var v = "";
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].id.startsWith(aclPrefix)) {
                v += "," + nodes[i].dataId;
            }
        }
        return v.length > 0 ? v.substring(1) : v;
    }


    $(".saveRoleAcl").click(function (e) {
        e.preventDefault();
        if(lastRoleId == -1) {
            showMessage("保存角色与权限点的关系", "请在左侧选择需要操作的角色", false);
            return;
        }
        $.ajax({
            url:"/sys/role/changeAcls.json",
            data:{
                roleId:lastRoleId,
                aclIds: getTreeSelectedId()
            },
            type:"POST",
            success:function (result) {
                if(result.ret) {
                    showMessage("保存角色与权限点的关系","保存成功",false)
                }else {
                    showMessage("保存角色与权限点的关系", result.msg, false)
                }
            }
        })
    });
    function updateRole(isCreated, successCallback, failCallback) {
        $.ajax({
            url: isCreated ? "/sys/role/save.json" : "/sys/role/update.json",
            data: $("#roleForm").serializeArray(),
            type: "POST",
            success: function (result) {
                if (result.ret) {
                    loadRoleList();
                    if (successCallback) {
                        successCallback(result);
                    }
                } else {
                    if (failCallback) {
                        failCallback(result);
                    }
                }
            }
        })
    }

    $("#roleTab a[data-toggle='tab']").on("shown.bs.tab",function (e) {
        if(lastRoleId == -1){
            showMessage("加载角色与用户关系", "请先在左侧选择需要操作的角色", false);
            return;
        }
        if(e.target.getAttribute("href") == "#roleAclTab"){
            selectFirstTab = true;
            loadRoleAcl(lastRoleId)
        }else {
            selectFirstTab = false;
            loadRoleUser(lastRoleId)
        }
    });


    function loadRoleUser(selectRoleId) {
        console.log("loadRoleUser :"+ selectRoleId)
        $.ajax({
            url:"/sys/role/users.json",
            data:{
                roleId: selectRoleId
            },
            success:function (result) {
                if(result.ret){
                    var renderedSelected = Mustache.render(selectedUsersTemplate, {
                        userList: result.data.selected
                    });
                    var renderedUnSelected = Mustache.render(unSelectedUsersTemplate, {
                        userList: result.data.unselected
                    });
                    $("#roleUserList").html(renderedSelected+ renderedUnSelected);
                    //处理多选
                    if(!hasMultiSelect) {
                        $("select[name='roleUserList']").bootstrapDualListbox({
                            showFilterInputs:false,
                            moveOnSelect :false,
                            infoText:false
                        });
                        hasMultiSelect = true;
                    }else {
                        $("select[name='roleUserList']").bootstrapDualListbox('refresh',true);
                    }
                }else {
                    showMessage("获取用户列表", result.msg, false);
                }
            }
        })

    }

    $(".saveRoleUser").click(function (e) {
        e.preventDefault();
        if (lastRoleId == -1) {
            showMessage("保存角色与用户的关系", "请在左侧选择需要操作的角色", false);
            return;
        }
        $.ajax({
            url: "/sys/role/changeUsers.json",
            data: {
                roleId: lastRoleId,
                userIds: $("#roleUserList").val()? $("#roleUserList").val().join(","):''
            },
            type: "POST",
            success: function (result) {
                if (result.ret) {
                    showMessage("保存角色与用户的关系", "保存成功", false)
                } else {
                    showMessage("保存角色与用户的关系", result.msg, false)
                }
            }
        })
    })

    function bindRoleClick() {
        $(".role-delete").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            //id选择器
            var roleId = $(this).attr("data-id");
            var roleName = $(this).attr("data-name");
            if (confirm("确定要删除角色【" + roleName + "】吗？")) {
                $.ajax({
                    url: "/sys/role/delete.json",
                    data: {
                        id: roleId
                    },
                    success: function (result) {
                        if (result.ret) {
                            showMessage("删除角色[" + roleName + "]", "删除成功", true);
                            loadRoleList();
                        } else {
                            showMessage("删除角色[" + roleName + "]", result.msg, false);
                        }
                    }
                })
            }
        });
        $(".role-edit").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            //id选择器
            var roleId = $(this).attr("data-id");
            //id选择器
            $("#dialog-role-form").dialog({
                model: true,
                title: '编辑角色模块',
                open: function (event, ui) {
                    //隐藏模态框自带的关闭按钮
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    //重置输入框
                    $("#roleForm")[0].reset();
                    $("#roleId").val(roleId);
                    var targetRole = roleMap[roleId];
                    if (targetRole) {
                        $("#roleName").val(targetRole.name);
                        $("#roleType").val(targetRole.type);
                        $("#roleRemark").val(targetRole.remark);
                        $("#roleStatus").val(targetRole.status);
                    }
                },
                buttons: {
                    "更新": function (e) {
                        //阻止默认方法
                        e.preventDefault();
                        //保存数据操作
                        updateRole(false, function (data) {
                            //关闭模态框
                            $("#dialog-role-form").dialog("close");
                        }, function (data) {
                            //提示错误信息
                            showMessage("编辑角色模块", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-role-form").dialog("close");
                    }
                }
            });
        });

        $(".role-name").click(function (e) {
            e.preventDefault();
            //关闭冒泡事件
            e.stopPropagation();
            var roleId = $(this).attr("data-id");
            handleRoleSelected(roleId);
        });

    }

    function handleRoleSelected(roleId) {
        if (lastRoleId != -1) {
            var lastRole = $("#role_" + lastRoleId + " .dd2-content:first");
            lastRole.removeClass("btn-yellow");
            lastRole.removeClass("no-hover");
        }
        var currentRole = $("#role_" + roleId + " .dd2-content:first");
        currentRole.addClass("btn-yellow");
        currentRole.addClass("no-hover");
        lastRoleId = roleId;
        $(".roleTab a:first").trigger();
        if (selectFirstTab) {
            loadRoleAcl(roleId);
        }else {
            loadRoleUser(roleId);
        }
    }

    function loadRoleAcl(selectedRoleId) {
        if (selectedRoleId == -1) {
            return;
        }
        $.ajax({
            url: "/sys/role/roleTree.json",
            data: {
                roleId: selectedRoleId
            },
            type: "POST",
            success: function (result) {
                if (result.ret) {
                    renderRoleTree(result.data);
                } else {
                    //提示错误信息
                    showMessage("加载权限角色数据", result.msg, false);
                }
            }
        })
    }


    function renderRoleTree(aclModuleList) {
        zTreeObj = [];
        recursivePrepareTreeData(aclModuleList);
        for (var key in nodeMap) {
            zTreeObj.push(nodeMap[key]);
        }
        $.fn.zTree.init($("#roleAclTree"), setting, zTreeObj);

    }

    function recursivePrepareTreeData(aclModuleList) {
        //prepare map
        if (aclModuleList && aclModuleList.length > 0) {
            //each
            $(aclModuleList).each(function (i, aclModule) {
                var hasChecked = false;
                if (aclModule.aclList && aclModule.aclList.length > 0) {
                    //each aclList
                    $(aclModule.aclList).each(function (i, acl) {
                        zTreeObj.push({
                            id: aclPrefix + acl.id,
                            pId: modulePrefix + acl.aclModuleId,
                            name: acl.name + ((acl.type == 1) ? '(菜单)' : ''),
                            chkDisabled: !acl.hasAcl,
                            checked: acl.checked,
                            dataId: acl.id
                        });
                        if (acl.checked) {
                            hasChecked = true;
                        }
                    })
                }
                if ((aclModule.aclModuleList && aclModule.aclModuleList.length > 0)
                    || (aclModule.aclList && aclModule.aclList.length > 0)) {
                    nodeMap[modulePrefix + aclModule.id] = {
                        id: modulePrefix + aclModule.id,
                        pId: modulePrefix + aclModule.parentId,
                        name: aclModule.name,
                        open: hasChecked
                    };
                    var tempAclModule = nodeMap[modulePrefix + aclModule.id];
                    while (hasChecked && tempAclModule) {
                        if (tempAclModule) {
                            nodeMap[tempAclModule.id] = {
                                id: tempAclModule.id,
                                pId: tempAclModule.pId,
                                name: tempAclModule.name,
                                open: true
                            }
                        }
                        tempAclModule = nodeMap[tempAclModule.pId];
                    }
                }
                recursivePrepareTreeData(aclModule.aclModuleList)
            });
        }

    }
</script>

</body>
</html>
