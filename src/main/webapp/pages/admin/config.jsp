<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="./include/head.jsp" %>
	<title>趣味问答系统-系统配置</title>
	
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

        <%@include file="./include/nav.jsp" %>


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
	            		
		            	<input id="interval" value="${configOS.cdayinterval }" class="span2" type="number" placeholder="一天答题次数"/>
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
            	<button class="btn" onclick="update_score()" style="margin-bottom: 20px;"><span class="icon-pencil"></span>  确  认</button>
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
    
    <%@include file="./include/foot.jsp" %>
    <script src="<%=request.getContextPath()%>/resources/js/admin/config.js" type="text/javascript"></script>
    <script>
    
   		
    	 /*hidden.bs.modal	此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。*/
        /* $('#tipModal').on('hidden.bs.modal', function () {
        	location.reload();
		}) */
    </script>
</body>
</html>