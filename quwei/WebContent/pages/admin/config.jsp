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
        body{background-color: #f5f5f5;}
        img{width:100%;max-height:200px;}
        @media (min-width: 950px) {img{height:200px;}}
        table td {vertical-align: middle !important;}
    </style>
</head>

<body>
	<input id="url" class="hidden" value="<%=request.getContextPath()%>"/>
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
                            <li><a href="userManageView"><span class="icon-user"></span> 管理员管理</a></li>
                            <li><a href="stuManageView"><span class="icon-user"></span> 学生管理</a></li>
                            <li><a href="questionManageView"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li><a href="historyManageView"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li class="active"><a><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#" onclick="loginout()"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 用户信息 -->
       
            
        <div class="container text-center">
            <input hidden="true" id="cid" value="${configOS.cid }" />
            <input hidden="true" id="old_interval" value="${configOS.cinterval }" />
            <input hidden="true" id="old_time" value="${configOS.canswertime }" />
            <div class="row">
            	<div class="span3"></div>
            	<div class="span3">
            		<p>答题时间（分钟）：</p>
            	
	            	<div class="input-append" style="padding-top: 7px;">
	            		
		            	<input id="answer_time" value="${configOS.canswertime }" class="span2" type="number" placeholder="答题时间（分钟）"/>
		            	<button class="btn" onclick="update_answertime()"><span class="icon-pencil"></span>  确  认</button>
		            </div>
            	</div>
            	<div class="span3">
            		<p>一天答题次数 ：</p>
            	
	            	<div class="input-append" style="padding-top: 7px;">
	            		
		            	<input id="interval" value="${configOS.cdayinterval }" class="span2" type="number" placeholder="答题间隔时间（分钟）"/>
		            	<button class="btn" onclick="update_interval()"><span class="icon-pencil"></span>  确  认</button>
		            </div>
            	</div>
            	<div class="span3"></div>
            </div>
            <div class="row">
            	
            </div>
            
            <div class="row">
            	
            	<table id="table" class="table table-striped text-center" style="max-width: 500px;margin-left: auto;margin-right: auto;">
            		<caption class="text-left">
	                    	<h4>
	                    		<strong>试题设置</strong>
	                    		
	                    	</h4>
	                </caption>
            		<thead>         
                        <th style="width:60px">题型</th>
                        <th>题数</th>
                        <th>单题分值</th>
                    </thead>
                    <tr>
            			<td>判断题</td>
            			<td><input id="judge_num" value="${configOS.cjudge_num }" class="span2" type="number" placeholder="题数"></td>
            			<td><input id="judge_score" value="${configOS.cjudge_score }" class="span2" type="number" placeholder="分值"></td>
            		</tr>
            		<tr>
            			<td>单选题</td>
            			<td><input id="single_num" value="${configOS.csingle_num }" class="span2" type="number" placeholder="题数"></td>
            			<td><input id="single_score" value="${configOS.csingle_score }" class="span2" type="number" placeholder="分值"></td>
            		</tr>
            		<tr>
            			<td>多选题</td>
            			<td><input id="multi_num" value="${configOS.cmulti_num }" class="span2" type="number" placeholder="题数"></td>
            			<td><input id="multi_score" value="${configOS.cmulti_score }" class="span2" type="number" placeholder="分值"></td>
            		</tr>
            		
            		<tr>
            			<td colspan="3">
            				<div class="form-group">
							    <h5>开始语</h5>
							    <textarea id="startword" class="form-control" style="width:90%;height:60px;">${configOS.cstartword }</textarea>
							  </div>
            			</td>
            		</tr>
            		<tr>
            			<td colspan="3">
            				<div class="form-group">
							    <h5>结束语</h5>
							    <textarea id="endword" class="form-control" style="width:90%;height:60px;">${configOS.cendword }</textarea>
							  </div>
            			</td>
            		</tr>
            	</table>
            	<button class="btn" onclick="update_score()"><span class="icon-pencil"></span>  确  认</button>
            </div>
        </div>


    </div>
	<!--公用提示模态框-->
    <div class="modal hide fade" id="tipModal" tabindex="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-body text-center">
                	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 id="tipContent"></h4>
                </div>
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
    
    <script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/resources/js/admin/config.js" type="text/javascript"></script>
    <script>
    
   		
    	 /*hidden.bs.modal	此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。*/
        /* $('#tipModal').on('hidden.bs.modal', function () {
        	location.reload();
		}) */
    </script>
</body>
</html>