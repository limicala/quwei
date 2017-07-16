<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                    <li id="userManageView"><a href="userManageView"><span class="icon-user"></span> 管理员管理</a></li>
                    <li id="stuManageView"><a href="stuManageView"><span class="icon-user"></span> 学生管理</a></li>
                    <li id="questionManageView"><a href="questionManageView"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                    <li id="historyManageView"><a href="historyManageView"><span class="icon-check"></span> 答题记录管理</a></li>
                    <li id="configView"><a href="configView"><span class="icon-wrench"></span> 系统配置</a></li>
                    <li><a href="#" onclick="loginout()"><span class="icon-off"></span> 退出系统</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>