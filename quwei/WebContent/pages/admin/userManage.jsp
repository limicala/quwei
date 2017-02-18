<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="<%=request.getContextPath()%>/frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="<%=request.getContextPath()%>/frame/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<title>趣味问答系统-用户管理</title>
	
	<style>
        body{
            background-color: #f5f5f5;
        }
        img{
            width:100%;
            max-height:200px;
        }
        @media (min-width: 950px) {
            img{
                height:200px;
            }
        }
		
        table td {
            vertical-align: middle !important;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 图片 -->
        <div class="jumbotron text-center" style="padding-top: 5px;">
            <img src="<%=request.getContextPath()%>/resources/images/main.jpg" class="img-rounded">
        </div>
        <!-- 导航栏 -->
        <div class="navbar " style="padding-top: 4px;">
            <div class="navbar navbar-inner">
                <div class="container">
                    <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="brand" href="mainView"><span class="icon-home" style="margin-top: 5px;"></span><strong> 首 页 </strong></a>

                    <div class="nav-collapse collapse text-center">
                        <ul class="nav" style="padding-top:1px;">
                            <li class="active"><a><span class="icon-user"></span> 用户管理</a></li>
                            <li><a href="questionManageView"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li><a href="#contact"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li><a href="#contact"><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#contact"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
        <!-- 用户信息 -->
        <div class="container text-center">
            <ul class="inline">
                <li>
                    查询 &rsaquo;&rsaquo;
                    <div class="input-append" style="padding-top: 7px;">
                        <input class="span2" type="text" placeholder="账号">
                        <button class="btn" type="button"><span class="icon-search"></span> 查 找 </button>
                    </div>
                </li>
                <li>
                    <button class="btn" type="button"><span class="icon-refresh"></span> 刷 新 </button>
                </li>
                <li>
                    <form class="form-inline">
                        添加新用户 &rsaquo;&rsaquo;
                        <input type="text" class="input-small" placeholder="新账号">
                        <input type="password" class="input-small" placeholder="密码">
                        <button type="submit" class="btn"><span class="icon-arrow-up"></span> 提 交 </button>
                        <button type="reset" class="btn"><span class="icon-minus"></span> 重 置 </button>
                    </form>
                </li>
            </ul>

            <div class="container">
                <table class="table table-striped text-center" style="max-width: 500px;margin-left: auto;margin-right: auto;">
                    <caption class="text-left"><h4><strong>管理员信息</strong></h4></caption>
                    <thead>
                    <tr>
                        <th>账号</th>
                        <th>密码</th>
                        <th>&nbsp;&nbsp;&nbsp;编辑</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>admin</td><td>admin</td>
                        <td>
                            <button class="btn btn-link" data-toggle="modal" data-target="#editModal">编辑</button><button class="btn btn-link">删除</button>
                        </td>
                    </tr>

                    <tr>
                        <td>jishubu</td><td>jishubu</td>
                        <td>
                            <button class="btn btn-link" data-toggle="modal" data-target="#editModal">编辑</button><button class="btn btn-link">删除</button>
                        </td>
                    </tr>



                    <tr>
                        <td>quwei</td><td>quwei</td>
                        <td>
                            <button class="btn btn-link" data-toggle="modal" data-target="#editModal">编辑</button><button class="btn btn-link">删除</button>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>

            <div class="pagination">
                <ul>
                    <li class="active"><a>&lsaquo;&lsaquo;</a></li>
                    <li class="active"><a>1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">&rsaquo;&rsaquo;</a></li>
                </ul>
            </div>

        </div>

    </div>

    <!--编辑用户信息模态框-->
    <div class="modal hide fade" id="editModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">编 辑 用 户 信 息</h4>
                </div>
                <div class="modal-body text-center">
                    <ul class="inline">
                        <li><h5>用户名 </h5></li>
                        <li><input name="editUserName" id="editN"/></li>
                    </ul>
                    <ul class="inline">
                        <li><h5>密&nbsp;&nbsp;&nbsp;&nbsp;码 </h5></li>
                        <li><input name="editPassword" id="editP"/></li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="">确定</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>

    <script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script>
        $("#editModal").on('hidden', function () {
            /*拟态框隐藏事件，用于初始化输入框，因为拟态框隐藏不会再次初始化，会保留之前输入的数据           判断*/
            $("#editN").val("");
            $("#editP").val("");
        })
    </script>
</body>
</html>