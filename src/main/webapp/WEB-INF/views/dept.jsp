<%--
  Created by IntelliJ IDEA.
  User: Fesine
  Date: 2018/1/12
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门管理</title>
    <jsp:include page="../../common/backend_common.jsp"/>
    <jsp:include page="../../common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        用户管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护部门与用户关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            部门列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 dept-add"></i>
            </a>
        </div>
        <div id="deptList">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                用户列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 user-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length"
                                        aria-controls="dynamic-table" class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table"
                           class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                姓名
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                所属部门
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                邮箱
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                电话
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="userList"></tbody>
                    </table>
                    <div class="row" id="userPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-dept-form" style="display: none;">
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer"
               role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级部门</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择部门"
                            style="width: 200px;"></select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">名称</label></td>
                <td><input type="text" name="name" id="deptName" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptSeq">顺序</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="1"
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptRemark">备注</label></td>
                <td><textarea name="remark" id="deptRemark"
                              class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer"
               role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所在部门</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="选择部门"
                            style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">名称</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">邮箱</label></td>
                <td><input type="text" name="email" id="userMail" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userTelephone">电话</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">状态</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="选择状态"
                            style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">备注</label></td>
                <td><textarea name="remark" id="userRemark"
                              class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<%--使用mustache渲染树--%>
<script id="deptListTemplate" type="x-tmpl-mustache">
<ol class="dd-list">
    {{#deptList}}
        <li class="dd-item dd2-item dept-name" id="dept_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green dept-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red dept-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/deptList}}
</ol>


</script>
<%--用户模板--%>
<script id="userListTemplate" type="x-tmpl-mustache">
{{#userList}}
<tr role="row" class="user-name odd" data-id="{{id}}"><!--even -->
    <td><a href="#" class="user-edit" data-id="{{id}}">{{username}}</a></td>
    <td>{{showDeptName}}</td>
    <td>{{email}}</td>
    <td>{{telephone}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td> <!-- 此处套用函数对status做特殊处理 -->
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green user-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red user-acl" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/userList}}



</script>
<%--实现递归树展示--%>
<script type="application/javascript">
    // 使用jquery
    $(function () {
        //存储部门树列表
        var deptList;
        //缓存部门树
        var deptMap = {};
        //缓存用户信息
        var userMap = {};
        var optionStr = "";
        var lastClickDeptId = -1;
        //通过id获取元素
        var deptListTemplate = $('#deptListTemplate').html();
        //通过Mustache渲染
        Mustache.parse(deptListTemplate);
        var userListTemplate = $('#userListTemplate').html();
        Mustache.parse(userListTemplate);
        loadDeptTree();

        function loadDeptTree() {
            //ajax获取tree数据
            $.ajax({
                url: "/sys/dept/tree.json",
                success: function (result) {
                    if (result.ret) {
                        deptList = result.data;
                        var rendered = Mustache.render(deptListTemplate, {deptList: result.data});
                        //递归渲染
                        $("#deptList").html(rendered);
                        recursiveRenderDept(result.data);
                        bindDeptClick();
                    } else {
                        showMessage("加载部门列表", result.msg, false);
                    }
                }
            })
        }

        //实现递归渲染部门数据
        function recursiveRenderDept(deptList) {
            if (deptList && deptList.length > 0) {
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    if (dept.deptList.length > 0) {
                        var rendered = Mustache.render(deptListTemplate, {deptList: dept.deptList});
                        $("#dept_" + dept.id).append(rendered);
                        recursiveRenderDept(dept.deptList);
                    }
                })
            }

        }

        // 绑定部门点击事件
        function bindDeptClick() {
            $(".dept-delete").click(function (e) {
                e.preventDefault();
                //关闭冒泡事件
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                var deptName = $(this).attr("data-name");
                if (confirm("确定要删除部门【" + deptName + "】吗？")) {
                    $.ajax({
                        url:"/sys/dept/delete.json",
                        data:{
                            id: deptId
                        },
                        success:function (result) {
                            if(result.ret){
                                showMessage("删除部门[" + deptName +"]", "删除成功", true);
                                loadDeptTree();
                            }else {
                                showMessage("删除部门[" + deptName + "]", result.msg, false);
                            }
                        }
                    })
                }
            });
            $(".dept-name").click(function (e) {
                e.preventDefault();
                //关闭冒泡事件
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                handleDeptSelected(deptId);

            });

            $(".dept-edit").click(function (e) {
                e.preventDefault();
                //关闭冒泡事件
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                $("#dialog-dept-form").dialog({
                    model: true,
                    title: '编辑部门',
                    open: function (event, ui) {
                        //隐藏模态框自带的关闭按钮
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        //生成层级部门下拉列表
                        optionStr = "<option value='0'>-</option>";
                        recursiveRenderDeptSelect(deptList, 1);
                        //重置输入框
                        $("#deptForm")[0].reset();
                        $("#parentId").html(optionStr);
                        $("#deptId").val(deptId);
                        var targetDept = deptMap[deptId];
                        if (targetDept) {
                            $("#parentId").val(targetDept.parentId);
                            $("#deptName").val(targetDept.name);
                            $("#deptSeq").val(targetDept.seq);
                            $("#deptRemark").val(targetDept.remark);
                        }
                    },
                    buttons: {
                        "更新": function (e) {
                            //阻止默认方法
                            e.preventDefault();
                            //保存数据操作
                            updateDept(false, function (data) {
                                //关闭模态框
                                $("#dialog-dept-form").dialog("close");
                            }, function (data) {
                                //提示错误信息
                                showMessage("更新部门", data.msg, false);
                            })
                        },
                        "取消": function () {
                            $("#dialog-dept-form").dialog("close");
                        }
                    }
                });
            });
        }

        function handleDeptSelected(deptId) {
            if (lastClickDeptId != -1) {
                var lastDept = $("#dept_" + lastClickDeptId + " .dd2-content:first");
                lastDept.removeClass("btn-yellow");
                lastDept.removeClass("no-hover");
            }
            var currentDept = $("#dept_" + deptId + " .dd2-content:first");
            currentDept.addClass("btn-yellow");
            currentDept.addClass("no-hover");
            lastClickDeptId = deptId;
            loadUserList(deptId);
        }

        function loadUserList(deptId) {
            var pageSize = $("#pageSize").val();
            var url = "/sys/user/page.json?deptId=" + deptId;
            var pageNo = $("#userPage .pageNo").val() || 1;
            $.ajax({
                url: url,
                data: {
                    pageSize: pageSize,
                    pageNo: pageNo
                },
                success: function (result) {
                    renderUserListAndPage(result, url);
                }
            });
        }

        function renderUserListAndPage(result, url) {
            if (result.ret) {
                if (result.data.total > 0) {
                    var rendered = Mustache.render(userListTemplate, {
                        userList: result.data.data,
                        "showDeptName": function () {
                            return deptMap[this.deptId].name;
                        },
                        "showStatus": function () {
                            return this.status == 1 ? "有效" : (this.status == 0 ? "无效" : "删除")
                        },
                        "bold": function () {
                            return function(text, render)
                            {
                                var status = render(text);
                                if (status == "有效") {
                                    return "<span class='label label-sm label-success'>有效</spna>";
                                } else if (status == "无效") {
                                    return "<span class='label label-sm label-warning'>无效</spna>";
                                } else {
                                    return "<span class='label'>删除</spna>";
                                }
                            }
                        }
                    });
                    $("#userList").html(rendered);
                    bindUserClick();
                    //缓存用户信息
                    $.each(result.data.data, function (i, user) {
                        userMap[user.id] = user;
                    })
                } else {
                    $("#userList").html('');
                }
                var pageSize = $("#pageSize").val();
                var pageNo = $("#userPage .pageNo").val() || 1;
                renderPage(url, result.data.total, pageNo, pageSize, result.data.total > 0 ? result.data.total : 0, "userPage",renderUserListAndPage);
            }else {
                showMessage("获取部门下用户列表", result.msg, false);
            }

        }

        $(".user-add").click(function () {
            //id选择器
            $("#dialog-user-form").dialog({
                model: true,
                title: '新增用户',
                open: function (event, ui) {
                    //隐藏模态框自带的关闭按钮
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    //生成层级部门下拉列表
                    optionStr = "";
                    recursiveRenderDeptSelect(deptList, 1);
                    //重置输入框
                    $("#userForm")[0].reset();
                    $("#deptSelectId").html(optionStr);
                    $("#deptSelectId").val(lastClickDeptId);
                },
                buttons: {
                    "添加": function (e) {
                        //阻止默认方法
                        e.preventDefault();
                        //保存数据操作
                        updateUser(true, function (data) {
                            //关闭模态框
                            $("#dialog-user-form").dialog("close");
                            loadUserList(lastClickDeptId);
                        }, function (data) {
                            //提示错误信息
                            showMessage("新增用户", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-user-form").dialog("close");
                    }
                }
            });
        });

        function updateUser(isCreated, successCallback, failCallback) {
            $.ajax({
                url: isCreated ? "/sys/user/save.json" : "/sys/user/update.json",
                data: $("#userForm").serializeArray(),
                type: "POST",
                success: function (result) {
                    if (result.ret) {
                        loadDeptTree();
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

        function bindUserClick() {
            //用户权限角色查看
            $(".user-acl").click(function (e) {
                e.preventDefault();
                //关闭冒泡事件
                e.stopPropagation();
                var userId = $(this).attr("data-id");
                $.ajax({
                    url:"/sys/user/acls.json",
                    data:{
                        id: userId
                    },
                    success:function (result) {
                        if(result.ret){
                            console.log(result.data);
                        }else {
                            showMessage("查看用户权限", result.msg, false);
                        }
                    }
                });
            });

            $(".user-edit").click(function (e) {
                e.preventDefault();
                //关闭冒泡事件
                e.stopPropagation();
                var userId = $(this).attr("data-id");
                $("#dialog-user-form").dialog({
                    model: true,
                    title: '编辑用户',
                    open: function (event, ui) {
                        //隐藏模态框自带的关闭按钮
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        //生成层级部门下拉列表
                        optionStr = "";
                        recursiveRenderDeptSelect(deptList, 1);
                        //重置输入框
                        $("#userForm")[0].reset();
                        $("#deptSelectId").html(optionStr);
                        $("#userId").val(userId);
                        var targetUser = userMap[userId];
                        if (targetUser) {
                            $("#userId").val(targetUser.id);
                            $("#deptSelectId").val(targetUser.deptId);
                            $("#userName").val(targetUser.username);
                            $("#userMail").val(targetUser.email);
                            $("#userTelephone").val(targetUser.telephone);
                            $("#userStatus").val(targetUser.status);
                            $("#userRemark").val(targetUser.remark);
                        }
                    },
                    buttons: {
                        "更新": function (e) {
                            //阻止默认方法
                            e.preventDefault();
                            //保存数据操作
                            updateUser(false, function (data) {
                                //关闭模态框
                                $("#dialog-user-form").dialog("close");
                                loadUserList(lastClickDeptId);
                            }, function (data) {
                                //提示错误信息
                                showMessage("更新用户", data.msg, false);
                            })
                        },
                        "取消": function () {
                            $("#dialog-user-form").dialog("close");
                        }
                    }
                });
            });

        }

        $(".dept-add").click(function () {
            //id选择器
            $("#dialog-dept-form").dialog({
                model: true,
                title: '新增部门',
                open: function (event, ui) {
                    //隐藏模态框自带的关闭按钮
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    //生成层级部门下拉列表
                    optionStr = "<option value='0'>-</option>";
                    recursiveRenderDeptSelect(deptList, 1);
                    //重置输入框
                    $("#deptForm")[0].reset();
                    $("#parentId").html(optionStr);
                    $("#parentId").val(lastClickDeptId);
                },
                buttons: {
                    "添加": function (e) {
                        //阻止默认方法
                        e.preventDefault();
                        //保存数据操作
                        updateDept(true, function (data) {
                            //关闭模态框
                            $("#dialog-dept-form").dialog("close");
                        }, function (data) {
                            //提示错误信息
                            showMessage("新增部门", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-dept-form").dialog("close");
                    }
                }
            });
        });

        function recursiveRenderDeptSelect(deptList, level) {
            level = level | 0;
            if (deptList && deptList.length > 0) {
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    var blank = "";
                    if (level > 1) {
                        for (var j = 3; j <= level; j++) {
                            blank += "..";
                        }
                        blank += "ㄴ";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>", {
                        id: dept.id,
                        name: blank + dept.name
                    });
                    if (dept.deptList && dept.deptList.length > 0) {
                        recursiveRenderDeptSelect(dept.deptList, level + 1);
                    }
                });
            }
        }

        function updateDept(isCreated, successCallback, failCallback) {
            $.ajax({
                url: isCreated ? "/sys/dept/save.json" : "/sys/dept/update.json",
                data: $("#deptForm").serializeArray(),
                type: "POST",
                success: function (result) {
                    if (result.ret) {
                        loadDeptTree();
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
    });


</script>
</body>
</html>
