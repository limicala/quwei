<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/quwei/frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="/quwei/frame/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
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
        <div class="jumbotron text-center" style="padding-top: 5px;">
            <img src="/quwei/resources/images/main.jpg" class="img-rounded">
        </div>

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
                            <li class="active"><a ><span class="icon-user"></span> 用户管理</a></li>
                            <li><a href="#about"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li><a href="#contact"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li><a href="#contact"><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#contact"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

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
                                <button class="btn btn-link">编辑</button><button class="btn btn-link">删除</button>
                            </td>
                        </tr>

                        <tr>
                            <td>jishubu</td><td>jishubu</td>
                            <td>
                                <button class="btn btn-link">编辑</button><button class="btn btn-link">删除</button>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                            	<input class="input-mini" type="text">
                            </td>
                            <td>
                            	<input class="input-mini" type="text">
                            </td>
                            <td>
                                <button class="btn btn-link">编辑</button><button class="btn btn-link">删除</button>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>quwei</td><td>quwei</td>
                            <td>
                                <button class="btn btn-link">编辑</button><button class="btn btn-link">删除</button>
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
	<script src="/quwei/frame/jquery/js/jquery.js" type="text/javascript"></script>
	<script src="/quwei/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>