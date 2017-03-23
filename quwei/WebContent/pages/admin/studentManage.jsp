<%@page import="java.util.List"%>
<%@page import="com.jfinal.plugin.activerecord.Record"%>
<%@page import="com.jfinal.plugin.activerecord.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=11">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="<%=request.getContextPath()%>/frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="<%=request.getContextPath()%>/frame/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<title>趣味问答系统-学生管理</title>
	
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
        
        .content{
            /* max-width: 200px; */
            word-break:break-all;
        }
        
        .tb-responsive {
            width: 100%;
            max-height: 600px;
            overflow-y:auto;
            -ms-overflow-style: -ms-autohiding-scrollbar;
        }
    </style>
</head>
<body>
	<input id="url" class="hidden" value="<%=request.getContextPath()%>"/>
	<input id="param" class="hidden" value="<%=request.getQueryString()%>"/>
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
                            <li class="active"><a><span class="icon-user"></span> 学生管理</a></li>
                            <li><a href="questionManageView"><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li><a href="historyManageView"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li><a href="configView"><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#" onclick="loginout()"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
        
        <!-- 用户信息 -->
        <div class="container text-center">
            <ul class="inline"><!--操作-->
                <li>
					<div class="input-prepend input-append" style="padding-top: 7px;">
						<div class="btn-group">
							<select id="search_type" style="width:auto">
                    		</select>
						</div>
						<input type="text" id="condit" value="${condit }" placeholder="关键字">
					</div>
				</li>
				<li><button class="btn" type="button" id="s" onclick="query(this);"><span class="icon-search"></span> 查 找 </button></li>
                <li><button type="button" class="btn" onclick="location.href='stuManageView'"><span class="icon-refresh"></span> 刷 新 </button></li>
                <li><button type="button" class="btn" data-toggle="modal" data-target="#editModal"><span class="icon-plus"></span> 添 加 </button></li>
                
                
            </ul>
			<ul class="inline"><!--操作-->
				<li><button type="button" class="btn" data-toggle="modal" data-target="#uploadModal"><span class="icon-plus-sign"></span> 批量导入 </button></li>
				<li><button type="button" class="btn" id="singleDels" onclick="deleteStudents(this)"><span class="icon-trash"></span> 删除所选 </button></li>
                
                <li><button  type="button" class="btn"  onclick="emptyStudent(this)"><span class="icon-trash"></span> 清空数据 </button></li>
			</ul>
            <div class="container tb-responsive">
                <table id="table" class="table table-condensed tb-responsive" style="max-width:700px;margin-left:auto;margin-right:auto;">
                 <%--    <caption class="text-left">
                    	<h4>
                    		<strong>学生信息</strong>
                    		<button style="margin-bottom: 5px;" class="btn" data-toggle="modal" data-target="#editModal"><span class="icon-plus"></span> 添 加 </button>
                    	</h4>
                    </caption> --%>
                    <thead style="white-space:nowrap; position: relative;z-index: 1;/*绝对定位 */background:#cccccc;">
	                    <tr>
	                    	<th style="width: 50px;"> 
                                	全 选<input type="checkbox" id="select_all"/>
                            </th>
	                        <th style="padding-left: 33px;min-width: 110px;">学号</th>
	                        <th style="min-width: 75px;">姓名</th>
	                        <th style="min-width: 100px;width: 200px;">学院</th>
	                        <th style="min-width:50px;padding-left: 9px;">&nbsp;&nbsp;&nbsp;编辑</th>
	                    </tr>
                    </thead>
                   
                
                    <tbody>
                    	<%Integer table_id = 1; %>
                    	<c:forEach items="${page.list }" var="s">
                    	
                    		<tr>
                    			
                    			<td style="padding-left: 25px;"><input  type="checkbox" name="stu_list" value="${s.sid }"/></td><!--选择-->
		                        <td style="padding-left: 33px;min-width: 110px;">${s.sid }</td>
		                        <td >${s.sname }</td>
		                       <%--  <td>${s.sprofession }</td> --%>
		                        <td >${s.scollege }</td>
		                        <td >
		                            <button class="btn btn-link" id="<%=table_id %>" data-toggle="modal" data-target="#editModal" onclick="editStudent(this)">编辑</button>
		                            <button class="btn btn-link" id="<%=table_id %>" data-toggle="modal" data-target="#judgeModal" onclick="deleteStudent(this)">删除</button>
		                        </td>
		                        <%table_id++; %>
		                    </tr>
                    
	                    </c:forEach>
					</tbody>
				
                    	
		                
               
                </table>
            </div>
			<%@include file="../common/page.jsp" %>
        </div>

    </div>
    
    <!--公用批量导入模态框-->
    <div class="modal hide fade" id="uploadModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择批量导入Excel文件</h4>
                </div>
                <div class="modal-body text-center">
                    <form id="uploadForm" action=uploadStudents method="post" enctype="multipart/form-data" style="padding-top: 10px;" target="frameFile">
                        <input id="chooseFile" type="file" name="doc" style="display:none" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                        
                        <div class="input-append">
                            <input id="showUrl"  type="text" readonly>
                            <a class="btn btn-primary" onclick="$('input[id=chooseFile]').click();">浏览文件</a>
                        </div>
                    </form>
                    <h6 style="color: #ff150e">提示：点击下方链接下载Excel表格模板，正确填充信息然后上传，否则可能导致上传失败.</h6>
                	<a id="dlTemplate" onclick="doDownloadTemplate()" style="cursor:pointer;">下载模板</a>
                </div>
                <div class="modal-footer text-left">
                    <button type="button" class="btn btn-success" onclick="doUpload()">确定上传</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>
    
    <!--编辑用户信息模态框-->
    
    <div class="modal hide fade" id="editModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header red">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <input hidden="hidden" id="old_sid"/>
                    <input hidden="hidden" id="table_id"/>
                    <h4 class="modal-title">编 辑 用 户 信 息</h4>
                </div>
                <div class="modal-body text-center">
                	
                    <ul class="inline">
                        <li><h5>学号 </h5></li>
                        <li><input type="text" onchange="check_exsit()" id="sid"/></li>
                    </ul>
                    <ul class="inline">
                        <li><h5>姓名</h5></li>
                        <li><input type="text" id="sname"/></li>
                    </ul>
                    <ul class="inline">
                        <li><h5>学院</h5></li>
                        <li>
                        	<input type="text" style="display:none"/><input type="password" style="display:none"/>
                        	<input type="text"  id="scollege"/>
                        </li>
                    </ul>
                </div>
                <div class="modal-footer">
                	<ul class="inline">
                		<li><p id="check_tip"></p></li>
                		<li><button type="button" id="update_button" class="btn btn-primary" onclick="addStudent()">确定</button></li>
                		<li><button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button></li>
                	</ul>
                	
                    
                    
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
                <div style="display:none;" id="confirm_password" class="modal-body text-center">
                	
                    <ul class="inline">
                        <li><h5>请输入密码</h5></li>
                        <li><input type="password" id="admin_password"/></li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="chooseOk">确定</button>&nbsp;&nbsp;
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
                </div>
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
    
    
    <script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/resources/js/admin/studentManage.js" type="text/javascript"></script>
    
    <script>
    	$(document).ready(function() {
    		initSearch("#search_type",${search_type})
    	});
    
    	
	    function initSearch(id,st){
	    	var search = new Array('','学号','姓名','学院');
	   		for(var i = 1; i <= 3; i++){
   				if(i != st){//可选
   					$(id).append("<option value='"+i+"'>"+search[i]+"</option>");
   				}else{//设置默认
   					$(id).append("<option value='"+i+"' selected = 'selected' >"+search[i]+"</option>");
   				}
	   			
	   		}
	   	}
	    
	    $("#chooseModal").on('hidden', function () {
            /*拟态框隐藏事件，用于初始化输入框，因为拟态框隐藏不会再次初始化，会保留之前输入的数据           单项*/
	    	 $("#confirm_password").hide();
        })
	    
	    $("#editModal").on('hidden', function () {
            /*拟态框隐藏事件，用于初始化输入框，因为拟态框隐藏不会再次初始化，会保留之前输入的数据           单项*/
        	clean();
        	$("#tipModal").modal('hide');
        })
		$("#select_all").click(function(){   
            if(this.checked){   
            	$("input[name='stu_list'][type=checkbox]").prop("checked", true);  
            }else{   
            	$("input[name='stu_list'][type=checkbox]").prop("checked", false);
            }   
        });
	    
	    $('input[id=chooseFile]').change(function() {
            $('#showUrl').val($(this).val());
        });
	    
	  //“1”存储成功 “0”存储失败 “2”上传模板出错 “3”数据填充出错，数据丢失 "4"没数据
		function afterUpload(respMsg){
			if (respMsg == "0"){
				$("#uploadModal").modal('hide');
				showWrongTip("上传失败");
			}else if (respMsg == "1"){
				$("#uploadModal").modal('hide');
				showRightTip("上传成功，刷新页面后即可查看上传内容");
			}else if (respMsg == "2"){
				$("#uploadModal").modal('hide');
				showWrongTip("上传失败,上传模板错误");
			}else if (respMsg == "3"){
				$("#uploadModal").modal('hide');
				showWrongTip("上传文件含不完整数据，上传未完成");
			}else if (respMsg == "4"){
				$("#uploadModal").modal('hide');
				showWrongTip("上传失败，上传文件不含有效数据");
			}
		}
	    
	  //**************************关键字查询题目信息**************************
	    function query(ob){
	    	var search_type = $("#search_type").val();
	    	var condiValue = $("#condit").val();
	    	if (condiValue.trim() == ""){
	    		showWrongTip("请输入关键字");
	    		return;
	    	}
	    	location.href = "stuManageView?search_type="+search_type+"&condit"+"="+condiValue;
	    }	
    
    </script>
    <iframe id='frameFile' name='frameFile' style='display: none;'></iframe>
</body>
</html>