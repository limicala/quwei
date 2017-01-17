<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/quwei/frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="/quwei/frame/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<title>趣味问答系统-管理员首页</title>
	
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
                    <a class="brand"><span class="icon-home" style="margin-top: 5px;"></span><strong> 首 页 </strong></a>

                    <div class="nav-collapse collapse text-center">
                        <ul class="nav" >
                            <li><a href="#"><span class="icon-user"></span> 用户管理</a></li>
                            <li><a href="#about"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li><a href="#contact"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li><a href="#contact"><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#contact"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

        
        <div class="container text-center" style="padding-top: 30px;">
            <h3 class="text-warning">欢迎使用</h3>
            <h1 style="font-size: 40px;">趣味问答系统</h1>
        </div>
	</div>
	<script src="/quwei/frame/jquery/js/jquery.js" type="text/javascript"></script>
	<script src="/quwei/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>