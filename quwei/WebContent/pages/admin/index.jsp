<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<title>趣味问答系统-管理员首页</title>
	
	<style>
        body{
            background-color: #f5f5f5;
        }
        .top{
            width:100%;
            max-height:200px;
        }
        @media (min-width: 950px) {
            .top{
                height:200px;
            }
        }

    </style>
</head>
<body>
	<input id="url" class="hidden" value="<%=request.getContextPath()%>"/>
	<div class="container">
        <div class="jumbotron text-center" style="padding-top: 5px;">
            <img class="top" src="<%=request.getContextPath()%>/resources/images/main.jpg" class="img-rounded">
        </div>

        <div class="navbar" style="padding-top: 4px;">
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
                            <li><a href="userManageView"><span class="icon-user"></span> 管理员管理</a></li>
                            <li><a href="stuManageView"><span class="icon-user"></span> 学生管理</a></li>
                            <li><a href="questionManageView"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li><a href="historyManageView"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li><a href="configView"><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#" onclick="loginout()"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
		<!--公用选择模态框-->
	    <div class="modal hide fade" id="chooseModal" tabindex="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="true">
	        <div class="modal-dialog" role="document" >
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                    <h4 class="modal-title text-center text-info" id="chooseContent">提 示</h4>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-primary" id="chooseOk">确定</button>&nbsp;&nbsp;
	                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
	                </div>
	            </div>
	        </div>
	    </div>
        
        <div class="container text-center" style="padding-top: 30px;">
            <h3 class="text-warning">欢迎使用</h3>
            <h1 style="font-size: 40px;">趣味问答系统</h1>
        </div>
        <div class="container text-center">
        	<button class="btn btn-info"  data-toggle="modal" data-target="#qrcodeModal"><span class="icon-qrcode"></span> 二 维 码</button>
        </div>
	</div>
	
	<!--公用批量导入模态框-->
    <div class="modal hide fade" id="qrcodeModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header text-center">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">二 维 码</h4>
                </div>
                <div class="modal-body text-center">
					<img data-src="holder.js/300x300" src="getQrcode" style="width: 300px; height: 300px;">
                </div>
                <div class="modal-footer text-center ">
                    <button type="button" class="btn btn-success" onclick="location.href = 'downloadQrcode'"> 下 载 </button>
                </div>
            </div>
        </div>
    </div>
    
	<script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/resources/js/admin/index.js" type="text/javascript"></script>
	
</body>
</html>