<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@include file="./include/head.jsp" %>

	<title>登录趣味问答系统</title>

    <style type="text/css">
      body {
        padding-top: 50px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
        box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

      #wrongTip{
        width:120px;
        margin: auto auto;
        z-index: 666;
      }

    </style>

  </head>
  <body>
  	<input id="url" class="hidden" value="<%=request.getContextPath()%>"/>
    <div class="alert alert-waring fade in text-center hidden" role="alert" id="wrongTip">
      <button type="button" class="close" id="cloBtn">&times;</button>
      <span class="icon-warning-sign"></span>
      <strong id="msg">账号或密码错误</strong>
    </div>
    <div class="container" style="z-index: 0;">
      <form class="form-signin" >
        <h2 class="form-signin-heading text-center">趣味问答系统</h2>
        <input type="text" name="id" id="id" class="input-block-level" placeholder="用户名">
        <input type="password" name="password" id="password" class="input-block-level" placeholder="密码">
        <div>
        	<img id="verify_img" alt="验证码" src="<%=request.getContextPath()%>/admin/img" onclick="javascript:this.src=this.src+'?time='+Math.random()" style="width:40%;height:36px;padding-bottom: 15px;">
        	<input type="text" id="verify_code" placeholder="验证码" style="width:50%">
        </div>
        <div class="text-center">
         	<button class="btn btn-large btn-success" id="subBtn" type="submit">登录</button>
        	
        </div>
        <div class="text-right " style="padding-top:15px;">
        	<small class="text-right">@肇庆学院-福慧图书馆-技术部</small>
        </div>
      </form>
      
    </div>
    <%@include file="./include/foot.jsp" %>
	<script src="<%=request.getContextPath()%>/resources/js/admin/login.js" type="text/javascript"></script>
	<script>
		
		$('#cloBtn').bind('click', function () {
			$("#subBtn").removeClass("disabled");
			$("#wrongTip").addClass("hidden");
			})
	</script>
  </body>
</html>