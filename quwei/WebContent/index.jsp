<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="frame/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<style>
	body{
	  padding-top: 15px;
	  padding-bottom: 20px;
	  background-color: #f5f5f5;
	}
	a:hover{ cursor:pointer; }
	@media (min-width: 950px) {
	  img{
	    width: 725px;
	    height:455px;
	  }
	}
	</style>
</head>
<body>
  <div class="container">

    <div class="container text-center">
      <img src="resources/images/index.jpg" class="">
    </div>
    <div class="container ">

      <legend class="text-center"><h3>趣味问答</h3></legend>

      <div class="text-center">
        昵 称 <input type="text"  class="input-medium search-query" placeholder="学号是最美美的昵称哦">
      </div>
      <div class="text-center" style="padding-top: 15px;">
        <button type="submit" class="btn btn-success ">开始答题</button>
      </div>
    </div>

  <div class="text-right" style="padding-top: 35px;">
    <a href="admin"><span class="icon-user"></span>管理员登录</a>
    <p>
      <small class="text-left">@肇庆学院-福慧图书馆-技术部</small>
    </p>
  </div>

  </div>
  	<script src="frame/jquery/js/jquery.js" type="text/javascript"></script>
	<script src="frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
  </body>
</html>