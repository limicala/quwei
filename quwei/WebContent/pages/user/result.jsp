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
    
    <title>趣味问答-结果</title>

</head>
<body style="background-color: #f5f5f5">
	<div class="container">
	    <div class="text-center" style="padding-top: 5px;">
	        <h1>图书馆小常识测试</h1>
	    </div>
	    <div>
	        <ul class="inline">
	            <li>
	                <h5 class="text-success">恭喜您，×××，你的分数为:</h5>
	            </li>
	            <li><h3 class="text-error">99分</h3></li>
	        </ul>
	
	        <h5 class="text-info">快来瞧瞧自己的答题结果吧！！魅力体现在细节，千万别错过注释哦。</h5>
	    </div>
	    <!--内容-->
	    <div class="container">
	        <!-- 判断题 -->
	        <div class="container">
	            <h4 style="margin: 0px;padding: 0px;">一、判断题：</h4>
	            <!-- 第一题 -->
	            <div class="row">
	                <div class="span12 text-left">
	                    <p><h5>1.阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？阅览室的男孩很帅？</h5></p>
	                </div>
	
	            </div>
	
	            <!-- 结果注释 -->
	            <div class="row">
	                <div class="span4">
	                    <ul class="inline">
	                        <li><h6>结果：</h6></li>
	                        <li><h6 class="text-error">错误</h6></li>
	                    </ul>
	                </div>
	                <div class="span4">
	                    <h6 class="text-success">正确答案： √</h6>
	                </div>
	                <div class="span4 ">
	                    <h6 class="text-error">你的答案： ×</h6>
	                </div>
	            </div>
	            <h6 class="text-info">注释: 技术部男生必须帅</h6>
	
	        </div>
	
	        <!-- 单选题 -->
	        <div class="container">
	            <h4 style="margin-bottom: 0px;padding-bottom: 0px;">二、单项选择题：</h4>
	
	            <!-- 第一题 -->
	            <div class="row text-left" style="margin-top: 0px;margin-bottom:0px;padpadding-top: 0px;">
	                <div class="span">
	                    <h5>1. 阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？阅览室是否可以自带手提电脑？</h5>
	                </div>
	            </div>
	            <div class="row" style="margin-top: 0px;padding-top: 0px;">
	                <div class="span3 text-left">
	                    <h6> A. 当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以当然可以</h6>
	                </div>
	                <div class="span3 text-left">
	                    <h6>B. 你说呢</h6>
	                </div>
	                <div class="span3 text-left">
	                    <h6>C. 哦呵</h6>
	                </div>
	                <div class="span3 text-left">
	                    <h6>D. 绝对不行？</h6>
	                </div>
	            </div>
	            <!-- 结果注释 -->
	            <div class="row">
	                <div class="span4">
	                    <ul class="inline">
	                        <li><h5>结果：</h5></li>
	                        <li><h5 class="text-success">正确</h5></li>
	                    </ul>
	                </div>
	                <div class="span4">
	                    <h6 class="text-success">正确答案： B</h6>
	                </div>
	                <div class="span4 ">
	                    <h6 class="text-success">你的答案： B</h6>
	                </div>
	            </div>
	            <h6 class="text-info">注释: 不可以带哦哦</h6>
	
	        </div>
	
	        <!-- 多选题 -->
	        <div class="container">
	            <h4 style="margin-bottom: 0px;padding-bottom: 0px;">三、多项选择题：</h4>
	
	            <!-- 第一题 -->
	            <div class="row text-left">
	                <div class="span">
	                    <h5>1. 阅览室是否可以自带手提电脑？</h5>
	                </div>
	            </div>
	            <div class="row">
	                <div class="span3 text-left">
	                    <h6> A. 当然可以</h6>
	                </div>
	                <div class="span3 text-left">
	                    <h6> B. 你说呢</h6>
	                </div>
	                <div class="span3 text-left">
	                    <h6> C. 哦呵</h6>
	                </div>
	                <div class="span3 text-left">
	                    <h6> D. 绝对不行？</h6>
	                </div>
	            </div>
	
	            <!-- 结果注释 -->
	            <div class="row">
	                <div class="span4">
	                    <ul class="inline">
	                        <li><h6>结果：</h6></li>
	                        <li><h6 class="text-success">正确</h6></li>
	                    </ul>
	                </div>
	                <div class="span4">
	                    <h6 class="text-success">正确答案： BC</h6>
	                </div>
	                <div class="span4 ">
	                    <h6 class="text-success">你的答案： BC</h6>
	                </div>
	            </div>
	            <h6 class="text-info">注释: 不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦不可以带哦哦</h6>
	        </div>
	
	
	        <div class="text-center" style="padding-top: 15px; padding-bottom: 15px;">
	            <p>
	                <small class="text-center">@肇庆学院-陈熹图书馆-技术部</small>
	            </p>
	        </div>
	    </div>
	</div>
	
	<script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>
    