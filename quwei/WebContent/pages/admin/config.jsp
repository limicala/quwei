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
                            <li><a href="#contact"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li class="active"><a><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#contact"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
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
            		<p>答题间隔时间（分钟）：</p>
            	
	            	<div class="input-append" style="padding-top: 7px;">
	            		
		            	<input id="interval" value="${configOS.cinterval }" class="span2" type="number" placeholder="答题间隔时间（分钟）"/>
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
    
    
    <script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script>
    
   		function update_answertime(){
   			var cid = $('#cid').val();
    		var answer_time = $('#answer_time').val();   
    		var old_time = $('#old_time').val();
    		if(answer_time == old_time){
    			showWrongTip("请修改时间间隔再提交");
    			return;
    		}
    		$.ajax({  
    	        type: "post",  
    	        url: $("#url").val()+"/admin/update_config",  
    	        data: "configOS.canswertime=" + answer_time + "&configOS.cid="+cid,  
    	       /*  dataType: 'html',  
    	        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
    	        success: function(result) {  
    	            //location.reload();  
    	          /*   $("#editModal").modal('hide'); */
    	          	if(result){
    	          		showRightTip("修改成功");
    	          	}
    	          	else{
    	          		showWrongTip("修改失败");
    	          	}
    	        }  
    	    }); 
   		}
    
    	function update_interval(){
    		var cid = $('#cid').val();
    		var interval = $('#interval').val();   
    		var old_interval = $('#old_interval').val();
    		if(interval == old_interval){
    			showWrongTip("请修改时间间隔再提交");
    			return;
    		}
    		$.ajax({  
    	        type: "post",  
    	        url: $("#url").val()+"/admin/update_config",  
    	        data: "configOS.cinterval=" + interval + "&configOS.cid="+cid,  
    	       /*  dataType: 'html',  
    	        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
    	        success: function(result) {  
    	            //location.reload();  
    	          /*   $("#editModal").modal('hide'); */
    	          	if(result){
    	          		showRightTip("修改成功");
    	          	}
    	          	else{
    	          		showWrongTip("修改失败");
    	          	}
    	        }  
    	    }); 
    	}
    	
    	function update_score(){
    		var cid = $('#cid').val();
    		var judge_num = $('#judge_num').val();   
    		var judge_score = $('#judge_score').val();   
    		var single_num = $('#single_num').val();   
    		var single_score = $('#single_score').val();   
    		var multi_num = $('#multi_num').val();   
    		var multi_score = $('#multi_score').val();   
    		var startword = $('#startword').val();
    		var endword = $('#endword').val();
    		//alert(startword);
    		
    		total_score = judge_num * judge_score + single_num * single_score + multi_num * multi_score;
    		//alert(total_score);
    		if(total_score != 100){
    			showWrongTip("总分应为100分，你当前设置的总分为"+total_score);	
    			return;
    		}
    		$.ajax({
    			url:$("#url").val()+"/admin/update_config",  
    			dataType:"json",
    			data:{
    				"configOS.cid":cid,
    				"configOS.cjudge_num":judge_num,
    				"configOS.cjudge_score":judge_score,
    				"configOS.csingle_num":single_num,
    				"configOS.csingle_score":single_score,
    				"configOS.cmulti_num":multi_num,
    				"configOS.cmulti_score":multi_score,
    				"configOS.cstartword":startword,
    				"configOS.cendword":endword,
    			},
    			success:function(result){
    			
    				if(result){
    	          		showRightTip("修改成功");
    	          	}
    	          	else{
    	          		showWrongTip("修改失败");
    	          	}
    			}//success
    		});//ajax
    	}
    	function showWrongTip(msg){
    		$("#tipContent").text(msg);
    		$("#tipContent").addClass("text-error");
    		$("#tipContent").removeClass("text-success");
    		$("#tipModal").modal('show');
    	}
    	
    	//**************************显示正确正确信息**************************
    	function showRightTip(msg){
    		$("#tipContent").text(msg);
    		$("#tipContent").removeClass("text-error");
    		$("#tipContent").addClass("text-success");
    		$("#tipModal").modal('show');
    	}
    	 /*hidden.bs.modal	此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。*/
        /* $('#tipModal').on('hidden.bs.modal', function () {
        	location.reload();
		}) */
    </script>
</body>
</html>