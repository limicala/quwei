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
	<!-- <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/css/bootstrap-select.css"> -->
	<link href="<%=request.getContextPath()%>/frame/select2/css/select2.min.css" rel="stylesheet" />

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
                            <li><a href="#contact"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
        
        <!-- 用户信息 -->
       
            
        <div class="container text-center">
            
            <div class="row">
            	<p>答题间隔时间（分钟）：</p>
            	<input hidden="true" id="cid" value="${configOS.cid }" />
            	<div class="input-append" style="padding-top: 7px;">
            		
	            	<input id="interval" value="${configOS.cinterval }" class="span2" type="number" placeholder="答题间隔时间（分钟）"/>
	            	<button class="btn" onclick="update_interval()"><span class="icon-pencil"></span>  确 认</button>
	            </div>
            </div>
            <div class="row">

            	<table id="table" class="table table-striped text-center" style="max-width: 400px;margin-left: auto;margin-right: auto;">
            		<thead>         
                        <th>题型</th>
                        <th>题目数量</th>
                        <th>单题分值</th>
                    </thead>
                    <tr>
            			<td>判断题</td>
            			<td>
							<select id="judge_num" class="js-example-basic-single">
							</select>  
            			</td>
            			<td>
	            			<select id="judge_score" class="js-example-basic-single">  
							</select>
            			</td>
						
            		</tr>
            		<tr>
            			<td>单选题</td>
            			<td>
            				<select id="single_num" class="js-example-basic-single">
							</select>  
            			</td>
            			<td>
            				 <select id="single_score" class="js-example-basic-single"> 
							</select> 
            			</td>
            		</tr>
            		<tr>
            			<td>多选题</td>
            			<td>
            				<select id="multi_num" class="js-example-basic-single">
							</select>  
            			</td>
            			<td>
            				<select id="multi_score" class="js-example-basic-single">
							</select>   
            			</td>
            		</tr>
            		
            	</table>
            	<button class="btn" onclick="update_score()"><span class="icon-pencil"></span>  确 认</button>  
            </div>
        </div>


    </div>
	<!--操作提示模态框-->
    <div class="modal hide fade" id="tipModal" tabindex="0" role="dialog" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-body text-center">
                	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                	<a id="message"></a>
                    
                </div>
            </div>
        </div>
    </div>
    
    
    <script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/select2/js/select2.min.js"></script>
    <script>
	    $(document).ready(function() {
	    	$(".js-example-basic-single").select2({width:'90%'});
	    
	    	initScore('#judge_num', ${configOS.cjudge_num },${judge_ness });
	    	
	    	initScore('#judge_score', ${configOS.cjudge_score },0);
	    	initScore('#single_num', ${configOS.csingle_num },${single_ness });
	    	initScore('#single_score', ${configOS.csingle_score },0);
	    	initScore('#multi_num', ${configOS.cmulti_num },${multi_ness });
	    	initScore('#multi_score', ${configOS.cmulti_score },0);
	    });
	    //初始化下拉框，curr_num为当前配置的题数,ness_num为该题型的必答题数
	    //ness_num需要在Question这个model类添加方法，还未添加
	   	function initScore(id, curr_num, ness_num){
	   		for(var i = 0; i <= 10; i++){
	   			if(i < ness_num){//不可选
	   				$(id).append("<option value='"+i+"' disabled='disabled'>"+i+"</option>");
	   			}else{
	   				if(i != curr_num){//可选
	   					$(id).append("<option value='"+i+"'>"+i+"</option>");
	   				}else{//设置默认
	   					$(id).append("<option value='"+i+"' selected = 'selected' >"+i+"</option>");
	   				}
	   			}
	   			
	   		}
	   	}
    
    	function update_interval(){
    		var cid = $('#cid').val();
    		var interval = $('#interval').val(); 
    		if(same_interval(interval)){
    			alert("请修改间隔时间再提交");
    		}else{
    			$.ajax({  
        	        type: "post",  
        	        url: "<%=request.getContextPath()%>/admin/update_interval",  
        	        data: "configOS.cinterval=" + interval + "&configOS.cid="+cid,  
        	       /*  dataType: 'html',  
        	        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
        	        success: function(result) {  
        	            //location.reload();  
        	          /*   $("#editModal").modal('hide'); */
        	            $("#message").text("修改成功");
        	            $("#tipModal").modal();
        	        }  
        	    }); 
    		}
    		
    	}
    	
    	function update_score(){
    		var cid = $('#cid').val();
    		var judge_num = $('#judge_num').val();   
    		var judge_score = $('#judge_score').val();   
    		var single_num = $('#single_num').val();   
    		var single_score = $('#single_score').val();   
    		var multi_num = $('#multi_num').val();   
    		var multi_score = $('#multi_score').val();   
    		//总分
    		var total_score = judge_num*judge_score+single_num*single_score+multi_num*multi_score;
    		//判断是否有更改
    		if(same_score(judge_num, judge_score, single_num, single_score, multi_num, multi_score)){
    			alert("请修改信息");
    		}else if(total_score != 100){//判断修改后的总分是否为100
    			alert("当前设置总分是"+total_score+"分！要求总分是100分");
 	            
    		}else{
    			$.ajax({
        			url:"<%=request.getContextPath()%>/admin/update_score",  
        			dataType:"json",
        			data:{
        				"configOS.cid":cid,
        				"configOS.cjudge_num":judge_num,
        				"configOS.cjudge_score":judge_score,
        				"configOS.csingle_num":single_num,
        				"configOS.csingle_score":single_score,
        				"configOS.cmulti_num":multi_num,
        				"configOS.cmulti_score":multi_score,
        			},
        			success:function(response){
        			
        				 $("#message").text("修改成功");
         	            $("#tipModal").modal();

        			}//success
        		});//ajax
    		}
    		
    	}
    	
    	/*下列两个方法都是通过写js方法存储旧的信息，在判断是否修改的时候再调用这些方法*/
    	//时间间隔是否有改变
    	function same_interval(interval){
    		var ointerval = ${configOS.cinterval};
    		return interval==ointerval;
    	}
    	
    	//分数信息是否有改变
    	function same_score(jn,js,sn,ss,mn,ms){
    		var ojn = ${configOS.cjudge_num};
	    	var ojs = ${configOS.cjudge_score};
	    	var osn = ${configOS.csingle_num};
	    	var oss = ${configOS.csingle_score};
	    	var omn = ${configOS.cmulti_num};
	    	var oms = ${configOS.cmulti_score};
	    	
	    	return ojn==jn&&ojs==js&&osn==sn&&oss==ss&&omn==mn&&oms==ms;
    	}
    	
    	 /*hidden.bs.modal	此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。*/
        $('#tipModal').on('hidden.bs.modal', function () {
        	location.reload();
		})
    </script>
</body>
</html>