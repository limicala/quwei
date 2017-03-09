<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.jfinal.plugin.activerecord.Record"%>
<%@ page import="com.jfinal.plugin.activerecord.Page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="<%=request.getContextPath()%>/frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<%=request.getContextPath()%>/frame/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
	
    <title>趣味问答系统-答题记录管理</title>
	
    <style>
        body{background-color: #f5f5f5;}
        img{width:100%;max-height:200px;}
        @media (min-width: 950px) {img{height:200px;}}
        table td {vertical-align: middle !important;}
		.tb-responsive {width: 100%;max-height: 600px;overflow-y:auto;-ms-overflow-style: -ms-autohiding-scrollbar;}
		select{width: 100%;}
		.tip{min-width: 100px;max-width: 150px;word-break:break-all;}
	</style>
</head>

<body>
	<input id="url" class="hidden" value="<%=request.getContextPath()%>"/>
	
	<input id="param" class="hidden" value="<%=request.getQueryString()%>"/>

    <!-- 主要内容 -->
    <div class="container">
        <!--顶端图片-->
        <div class="jumbotron text-center" style="padding-top: 5px;">
            <img src="<%=request.getContextPath()%>/resources/images/main.jpg" class="img-rounded">
        </div>

        <!--菜单导航-->
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
                            <li><a href="userManageView"><span class="icon-user"></span> 管理员管理</a></li>
                            <li><a href="stuManageView"><span class="icon-user"></span> 学生管理</a></li>
                            <li><a href="questionManageView"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li class="active"><a><span class="icon-check"></span> 答题记录管理</a></li>
                            <li><a href="configView"><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#" onclick="loginout()"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!--内容-->
        <div class="container text-center">
			<!--操作按钮-->
			<ul class="inline"><!--操作-->
				<li>
					<div class="input-prepend input-append" style="padding-top: 7px;">
						<div class="btn-group">
							<select id="condi">
								<!-- <option value="none">条件</option> -->
								<option value="stuNum" ${condi eq "stuNum" ? "selected" : "" }>学号</option>
								<option value="name" ${condi eq "name" ? "selected" : "" }>姓名</option>
								<option value="college" ${condi eq "college" ? "selected" : "" }>学院</option>
							</select>
						</div>
						<input type="text" placeholder="关键字" id="condiValue" value="${condiValue }">
					</div>
				</li>
				<li><button type="button" class="btn" onclick="queryRecord()"><span class="icon-search"></span> 查  找 </button></li>
				<li><button type="button" class="btn" onclick="location.href='historyManageView?hpn=${page1.pageNumber}'"><span class="icon-refresh"></span> 刷  新 </button></li>
				<li><button type="button" class="btn" onclick="deleteHistory()"><span class="icon-trash"></span> 删除所选 </button></li>
				<li><button type="button" class="btn" data-toggle="modal" data-target="#downloadModal"><span class="icon-download-alt"></span> 导出记录 </button></li>
				<li><button type="button" class="btn" onclick="emptyData()"><span class="icon-off"></span> 清空数据 </button></li>
			</ul>
			<div class="container tb-responsive">
				<table class="table table-condensed">
					<thead  style="white-space:nowrap; position: relative;z-index: 1;background:#ddd;">
						<tr>
							<th style="width: 50px;"> 全 选
								<input type="checkbox" id="recordCB" value="${h.hid }">
							</th>
							<th style="padding-left: 33px;min-width: 110px;">学号</th>
							<th style="min-width: 75px;">姓名</th>
							<th style="min-width: 200px;width: 300px;">学院</th>
							<th style="width: 150px;">分数</th>
							<th style="padding-left: 25px;">答题时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page1.list }" var="h">
							<tr>
								<td style="padding-left: 25px;"><input type="checkbox" name="recordCB" value="${h.hid }"></td><!--选择-->
								<td style="padding-left: 33px;min-width: 110px;">${h.hstuNum }</td><!--学号-->
								<td>${h.hname }</td><!--姓名-->
								<td>${h.hcollege }</td><!--学院-->
								<td>${h.hscore }</td><!--分数-->
								<td style="padding-left: 25px;min-width: 110px;">${fn:substring(h.htime, 0, 16) }</td><!--答题时间-->
							</tr>
							<tr style="max-height: 1px;background:#ddd;"><!--间隔--><td colspan="6" style="max-height: 1px;"></td></tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!--分页-->
            <%@include file="../common/page1.jsp" %>
        </div>
    </div>
	
    <!-- 部件 -->
	<!--导出模态框-->
    <div class="modal hide fade" id="downloadModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title text-center text-info" id="downloadContent">选 择 导 出 条 件</h4>
                </div>
                <div class="modal-body text-center">
					<div style="margin-bottom:15px;">
						<label class="radio inline " >
							<input type="radio" name="dlCB" value="default" checked> 默认
						</label>
						<label class="radio inline " >
							<input type="radio" name="dlCB" value="score"> 按分数从高到低
						</label>
						<label class="radio inline">
							<input  type="radio" name="dlCB" value="college"> 按学院分组
						</label>
					</div>
					<small style="margin-top:20px;" class="text-error">提示：默认为按答题时间先后顺序排列导出</small>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="downloadOk" onclick="downloadHistory()">确定</button>&nbsp;&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>
    
    <!--清空模态框-->
    <div class="modal hide fade" id="deleteModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title text-center text-info">确 定 清 空 所 有 答 题 记 录 吗 ？</h4>
                </div>
                <div id="confirm_password" class="modal-body text-center">
                    <ul class="inline">
                        <li><h5>请输入密码</h5></li>
                        <li><input type="password" id="admin_password"/></li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="deleteOk">确定</button>&nbsp;&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>
    

    <!--公用选择模态框-->
    <div class="modal hide fade" id="chooseModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
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
    
    <!--公用提示模态框-->
    <div class="modal hide fade" id="tipModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-body text-center">
                	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 id="tipContent"></h4>
                </div>
            </div>
        </div>
    </div>

    <script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/resources/js/admin/historyManage.js" type="text/javascript"></script>
    <script>
        $("#recordCB").click(function(){
            if(this.checked){   
                $("input[name='recordCB'][type=checkbox]").prop("checked", true);  
            }else{   
        		$("input[name='recordCB'][type=checkbox]").prop("checked", false);
            }   
        });

		$("#downloadModal").on('hidden', function () {
			$(":radio[name='dlCB'][value='default']").prop("checked", "checked");
        	$("#tipModal").modal('hide');
        })
        
        $("#deleteModal").on('hidden', function () {
        	$("#admin_password").val();
        	$("#tipModal").modal('hide');
        })
    </script>
</body>
</html>