<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="frame/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

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
    <div class="alert alert-info fade in text-center" id="wrongTip">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <strong>账号或密码错误</strong>
    </div>
    <div class="container" style="z-index: 0;">
      <form class="form-signin">
        <h2 class="form-signin-heading text-center">趣味问答系统</h2>
        <input type="text" class="input-block-level" placeholder="用户名">
        <input type="password" class="input-block-level" placeholder="密码">
        <div class="text-center">
          <button class="btn btn-large btn-success" type="submit">登录</button>
        </div>
      </form>
    </div>
    <script src="frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

  </body>
</html>