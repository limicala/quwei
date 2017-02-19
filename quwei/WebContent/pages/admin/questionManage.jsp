<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.jfinal.plugin.activerecord.Record"%>
<%@page import="com.jfinal.plugin.activerecord.Page"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	
    <title>趣味问答系统-题库管理</title>
	
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


        .tb-responsive {
            width: 100%;
            max-height: 600px;
            overflow-y:auto;
            -ms-overflow-style: -ms-autohiding-scrollbar;
        }

        textarea{
            width: 90%;
            height: 90%;
        }

        select{
            width: 100%;
        }

        .content{
            max-width: 200px;
            word-break:break-all;
        }

        .itemContent{
            max-width: 150px;
            min-width:100px;
            word-break:break-all;
        }

        .tip{
            min-width: 100px;
            max-width: 150px;
            word-break:break-all;
        }

    </style>
</head>
<body>
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
                            <li><a href="userManageView"><span class="icon-user"></span> 用户管理</a></li>
                            <li class="active"><a><span class="icon-list-alt"></span> 题库信息管理</a></li>
                            <li><a href="#contact"><span class="icon-check"></span> 答题记录管理</a></li>
                            <li><a href="configView"><span class="icon-wrench"></span> 系统配置</a></li>
                            <li><a href="#contact"><span class="icon-off"></span> 退出系统</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <!--内容-->
        <div class="container text-center">
            <!-- tab按钮 -->
            <ul class="nav nav-tabs">
                <li ${ct eq "1" ? "class='active'" : "" } ><a href="#single" data-toggle="tab">单项选择题</a></li>
                <li ${ct eq "2" ? "class='active'" : "" } ><a href="#mutil" data-toggle="tab">多项选择题</a></li>
                <li ${ct eq "3" ? "class='active'" : "" } ><a href="#judge" data-toggle="tab">判断题</a></li>
            </ul>
            <!-- 对应tab按钮内容 -->
            <div class="tab-content" style="overflow-x: hidden;">

                <!--单项选择题-->
                <div class="tab-pane fade ${ct eq '1' ? 'active in' : '' }" id="single">
                    <!--操作按钮-->
                    <ul class="inline"><!--操作-->
                        <li>
                            <div class="input-append" style="padding-top: 10px;">
                                <input class="span2" type="text" placeholder="题目关键字">
                                <button class="btn" type="button"><span class="icon-search"></span> 查 找 </button>
                            </div>
                        </li>
                        <li><button class="btn" type="button" onclick="location.href='questionManageView?ct=1&spn=${page.pageNumber}'"><span class="icon-refresh"></span> 刷 新 </button></li>
                        <li><button type="submit" class="btn" data-toggle="modal" data-target="#singleModal"><span class="icon-plus"></span> 添 加 </button></li>
                        <li><button type="reset" class="btn"><span class="icon-trash"></span> 删除所选 </button></li>
                        <li><button type="reset" class="btn"  data-toggle="modal" data-target="#uploadModal"><span class="icon-plus-sign"></span> 批量导入 </button></li>
                    </ul>
                    <!--题库内容（表格显示）-->
                    <div class="container tb-responsive">
                        <table class="table table-striped text-center table-bordered table-condensed table-responsive" style="min-width: 600px;" data-toggle="table">
                            <thead style="white-space:nowrap; position: relative;z-index: 1;/*绝对定位 */background:#cccccc;">
                            <tr>
                                <th style="width: 45px;"> 全 选
                                    <input type="checkbox" value="">
                                </th>
                                <th >题 目 内 容</th>
                                <th colspan="2">选 项</th>
                                <th style="width: 50px;">答案</th>
                                <th style="width: 100px;">注解</th>
                                <th style="width: 15px;">回答次数（次）</th>
                                <th style="width: 15px;">正确率（%）</th>
                                <th>&nbsp;&nbsp;&nbsp;编 辑</th>
                            </tr>
                            </thead>
                            <tbody>

                            <!-- ******************************************默认显示样式****************************************** -->

							<c:forEach items="${page.list }" var="s">
								<tr>
	                                <td rowspan="4" style="padding-left: 25px;"><input type="checkbox" value=""></td><!--选择-->
	                                <td rowspan="4" class="content"><!--题目内容-->
	                                    ${s.qcontent}
	                                </td>
	                                <td style="max-width: 7px;"><strong>A</strong></td><!--选项标记-->
	                                <td class="itemContent">${s.qa }</td><!--选项内容-->
	                                <td rowspan="4" style="width: 50px;">${s.qanswer }</td><!--答案-->
	                                <td rowspan="4" class="tip">${s.explain }</td><!--题目注释-->
	                                <td rowspan="4">${s.qall_times }</td><!--回答次数-->
	                                <td rowspan="4">${s.qtrue_times }</td><!--正确率-->
	                                <td rowspan="4" style="width: 150px;"><!--题目操作->编辑->删除-->
	                                    <a class="btn btn-link">编辑</a><a class="btn btn-link">删除</a>
	                                </td>
	                            </tr>
	                            <tr><!--B选项-->
	                                <td style="max-width: 7px;"><strong>B</strong></td>
	                                <td class="itemContent">${s.qb }</td>
	                            </tr>
	                            <tr><!--C选项-->
	                                <td style="max-width: 7px;"><strong>C</strong></td>
	                                <td class="itemContent">${s.qc }</td>
	                            </tr>
	                            <tr><!--D选项-->
	                                <td style="max-width: 7px;"><strong>D</strong></td>
	                                <td class="itemContent">${s.qd }</td>
	                            </tr>
							</c:forEach>

                            </tbody>
                        </table>
                    </div>
                    <!--分页-->
                    <%@include file="../common/page.jsp" %>

                    <!--modal添加单个题目模态框-->
                    <!--
                        Button trigger modal 添加触发按钮，即“添加按钮”
                        为触发按钮添加属性： data-toggle="modal" data-target="#singleModal"  对应id
                        data-backdrop="false" 点击窗口外不会关闭窗口
                    -->
                    <!-- 单项选择题添加Modal -->
                    <div class="modal hide fade" id="singleModal" tabindex="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="true">
                        <div class="modal-dialog" role="document" >
                            <div class="modal-content">
                                <div class="modal-header red">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">新 增 单 项 选 择 题 目</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" style="width: 100%;" id="singlePlus">
                                        <table style="width: 90%;">
                                            <tr>
                                                <td colspan="1"><strong  style="white-space: nowrap">题目:</strong></td>
                                                <td colspan="3"><textarea id="singleContent" style="width: 100%;max-width:100%;height: 100px;"></textarea></td>
                                            </tr>

                                            <tr>
                                                <td colspan="1"><strong>A. </strong></td>
                                                <td colspan="3"><input id="singleA" type="text" style="width: 100%;" name="inputA" placeholder="A选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>B. </strong></td>
                                                <td colspan="3"><input id="singleB" type="text" style="width: 100%;" name="inputA" placeholder="B选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>C. </strong></td>
                                                <td colspan="3"><input id="singleC" type="text" style="width: 100%;" name="inputA" placeholder="C选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>D. </strong></td>
                                                <td colspan="3"><input id="singleD" type="text" style="width: 100%;" name="inputA" placeholder="D选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td><strong>答案:</strong></td>
                                                <td colspan="3" align="left" >
                                                    <label class="radio inline " style="max-width: 50px;">
                                                        <input id="singleAnswer" type="radio" name="singleOptionsRadios"value="1" checked> A
                                                    </label>
                                                    <label class="radio inline">
                                                        <input type="radio" name="singleOptionsRadios" value="2"> B
                                                    </label>
                                                    <label class="radio inline">
                                                        <input  type="radio" name="singleOptionsRadios" value="3"> C
                                                    </label>
                                                    <label class="radio inline">
                                                        <input  type="radio" name="singleOptionsRadios" value="4"> D
                                                    </label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>注释:</strong></td>
                                                <td colspan="3"><input id="singleTip" type="text" name="tip" placeholder="题目注释" style="width: 100%;" ></td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" onclick="">提交</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!--多项选择题-->
                <div class="tab-pane fade ${ct eq '2' ? 'active in' : '' }" id="mutil">
                    <!--操作按钮-->
                    <ul class="inline"><!--操作-->
                        <li>
                            <div class="input-append" style="padding-top: 10px;">
                                <input class="span2" type="text" placeholder="题目关键字">
                                <button class="btn" type="button"><span class="icon-search"></span> 查 找 </button>
                            </div>
                        </li>
                        <li><button class="btn" type="button" onclick="location.href='questionManageView?ct=2&mpn=${page1.pageNumber}'"><span class="icon-refresh"></span> 刷 新 </button></li>
                        <li><button type="submit" class="btn" data-toggle="modal" data-target="#mutilModal"><span class="icon-plus"></span> 添 加 </button></li>
                        <li><button type="reset" class="btn"><span class="icon-trash"></span> 删除所选 </button></li>
                        <li><button type="reset" class="btn"  data-toggle="modal" data-target="#uploadModal"><span class="icon-plus-sign"></span> 批量导入 </button></li>
                    </ul>
                    <!--题库内容（表格显示）-->
                    <div class="container tb-responsive">
                        <table class="table table-striped text-center table-bordered table-condensed table-responsive" style="min-width: 600px;" data-toggle="table">
                            <thead style="white-space:nowrap; position: relative;z-index: 1;/*绝对定位 */background:#cccccc;">
                            <tr>
                                <th style="width: 45px;"> 全 选
                                    <input type="checkbox" value="">
                                </th>
                                <th >题 目 内 容</th>
                                <th colspan="2">选 项</th>
                                <th style="width: 50px;">答案</th>
                                <th style="width: 100px;">注解</th>
                                <th style="width: 15px;">回答次数（次）</th>
                                <th style="width: 15px;">正确率（%）</th>
                                <th>&nbsp;&nbsp;&nbsp;编 辑</th>
                            </tr>
                            </thead>
                            <tbody>

                            <!-- ******************************************默认显示样式****************************************** -->
							<c:forEach items="${page1.list }" var="m">
								<tr>
	                                <td rowspan="4" style="padding-left: 25px;"><input type="checkbox" value=""></td><!--选择-->
	                                <td rowspan="4" class="content"><!--题目内容-->
	                                    ${m.qcontent}
	                                </td>
	                                <td style="max-width: 7px;"><strong>A</strong></td><!--选项标记-->
	                                <td class="itemContent">${m.qa }</td><!--选项内容-->
	                                <td rowspan="4" style="width: 50px;">${m.qanswer }</td><!--答案-->
	                                <td rowspan="4" class="tip">${m.explain }</td><!--题目注释-->
	                                <td rowspan="4">${m.qall_times }</td><!--回答次数-->
	                                <td rowspan="4">${m.qtrue_times }</td><!--正确率-->
	                                <td rowspan="4" style="width: 150px;"><!--题目操作->编辑->删除-->
	                                    <a class="btn btn-link">编辑</a><a class="btn btn-link">删除</a>
	                                </td>
	                            </tr>
	                            <tr><!--B选项-->
	                                <td style="max-width: 7px;"><strong>B</strong></td>
	                                <td class="itemContent">${m.qb }</td>
	                            </tr>
	                            <tr><!--C选项-->
	                                <td style="max-width: 7px;"><strong>C</strong></td>
	                                <td class="itemContent">${m.qc }</td>
	                            </tr>
	                            <tr><!--D选项-->
	                                <td style="max-width: 7px;"><strong>D</strong></td>
	                                <td class="itemContent">${m.qd }</td>
	                            </tr>
							</c:forEach>
                            
                            </tbody>
                        </table>
                    </div>
                    <!--分页-->
                    <%@include file="../common/page1.jsp" %>
                    
                    <!--modal添加单个题目模态框-->
                    <!--
                        Button trigger modal 添加触发按钮，即“添加按钮”
                        为触发按钮添加属性： data-toggle="modal" data-target="#singleModal"  对应id
                        data-backdrop="false" 点击窗口外不会关闭窗口
                    -->
                    <!-- 多项选择题添加Modal -->
                    <div class="modal hide fade" id="mutilModal" tabindex="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="true">
                        <div class="modal-dialog" role="document" >
                            <div class="modal-content">
                                <div class="modal-header red">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">新 增 多 项 选 择 题 目</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" style="width: 100%;" id="mutilPlus">
                                        <table style="width: 90%;">
                                            <tr>
                                                <td colspan="1"><strong  style="white-space: nowrap">题目:</strong></td>
                                                <td colspan="3"><textarea id="mutilContent" style="width: 100%;max-width:100%;height: 100px;"></textarea></td>
                                            </tr>

                                            <tr>
                                                <td colspan="1"><strong>A. </strong></td>
                                                <td colspan="3"><input id="mutilA" type="text" style="width: 100%;" name="inputA" placeholder="A选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>B. </strong></td>
                                                <td colspan="3"><input id="mutilB" type="text" style="width: 100%;" name="inputA" placeholder="B选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>C. </strong></td>
                                                <td colspan="3"><input id="mutilC" type="text" style="width: 100%;" name="inputA" placeholder="C选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>D. </strong></td>
                                                <td colspan="3"><input id="mutilD" type="text" style="width: 100%;" name="inputA" placeholder="D选项内容"></td>
                                            </tr>
                                            <tr>
                                                <td><strong>答案:</strong></td>
                                                <td colspan="3" align="left" >
                                                    <label class="checkbox inline " style="max-width: 50px;">
                                                        <input id="mutilAnswer" type="checkbox" name="singleOptionsRadios"value="1" checked> A
                                                    </label>
                                                    <label class="checkbox inline">
                                                        <input type="checkbox" name="singleOptionsRadios" value="2" checked> B
                                                    </label>
                                                    <label class="checkbox inline">
                                                        <input  type="checkbox" name="singleOptionsRadios" value="3" checked> C
                                                    </label>
                                                    <label class="checkbox inline">
                                                        <input  type="checkbox" name="singleOptionsRadios" value="4" checked> D
                                                    </label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>注释:</strong></td>
                                                <td colspan="3"><input id="mutilTip" type="text" name="tip" placeholder="题目注释" style="width: 100%;" ></td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" onclick="">提交</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!--判断题-->
                <div class="tab-pane fade ${ct eq '3' ? 'active in' : ''}" id="judge">
                    <!--操作按钮-->
                    <ul class="inline"><!--操作-->
                        <li>
                            <div class="input-append" style="padding-top: 10px;">
                                <input class="span2" type="text" placeholder="题目关键字">
                                <button class="btn" type="button"><span class="icon-search"></span> 查 找 </button>
                            </div>
                        </li>
                        <li><button class="btn" type="button" onclick="location.href='questionManageView?ct=3&jpn=${page2.pageNumber}'"><span class="icon-refresh"></span> 刷 新 </button></li>
                        <li><button type="submit" class="btn" data-toggle="modal" data-target="#judgeModal"><span class="icon-plus"></span> 添 加 </button></li>
                        <li><button type="reset" class="btn"><span class="icon-trash"></span> 删除所选 </button></li>
                        <li><button type="reset" class="btn"  data-toggle="modal" data-target="#uploadModal"><span class="icon-plus-sign"></span> 批量导入 </button></li>
                    </ul>
                    <!--题库内容（表格显示）-->
                    <div class="container tb-responsive">
                        <table class="table table-striped text-center table-bordered table-condensed table-responsive" style="min-width: 600px;" data-toggle="table">
                            <thead style="white-space:nowrap; position: relative;z-index: 1;/*绝对定位 */background:#cccccc;">
                            <tr>
                                <th style="width: 45px;"> 全 选
                                    <input type="checkbox" value="">
                                </th>
                                <th style="width: 200px;">题 目 内 容</th>
                                <th style="width: 50px;">答案</th>
                                <th style="width: 100px;">注解</th>
                                <th style="width: 15px;">回答次数（次）</th>
                                <th style="width: 15px;">正确率（%）</th>
                                <th>&nbsp;&nbsp;&nbsp;编 辑</th>
                            </tr>
                            </thead>
                            <tbody>

                            <!-- ******************************************默认显示样式****************************************** -->
							<c:forEach items="${page2.list }" var="j">
								<tr>
	                                <td style="padding-left: 25px;"><input type="checkbox" value=""></td><!--选择-->
	                                
	                                <td class="content"><!--题目内容-->${j.qcontent }</td>
	                                <c:choose>
									 	<c:when test="${j.qanswer eq 1 }">
									 		<td style="width: 50px;"><span class="icon-ok"></span></td><!--答案-->
									 	</c:when>
									 	<c:otherwise>
									 		<td style="width: 50px;"><span class="icon-remove"></span></td><!--答案-->
									 	</c:otherwise>
									</c:choose>
	                                
	                                <td class="tip" style="width: 210px;">${j.qexplain }</td><!--题目注释-->
	                                <td >${j.qall_times }</td><!--答题次数-->
	                                <td >${j.qtrue_times }</td><!--正确率-->
	                                <td style="width: 100px;"><!--题目操作->编辑->删除-->
	                                    <a class="btn btn-link">编辑</a><a class="btn btn-link">删除</a>
	                                </td>
	                            </tr>
							</c:forEach>
                            
                            </tbody>
                        </table>
                    </div>
                    <!--分页-->
                    <%@include file="../common/page2.jsp" %>
                    
                    <!--modal添加单个判断题目模态框-->
                    <!--
                        Button trigger modal 添加触发按钮，即“添加按钮”
                        为触发按钮添加属性： data-toggle="modal" data-target="#singleModal"  对应id
                        data-backdrop="false" 点击窗口外不会关闭窗口
                    -->
                    <!-- 判断题添加Modal -->
                    <div class="modal hide fade" id="judgeModal" tabindex="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="true">
                        <div class="modal-dialog" role="document" >
                            <div class="modal-content">
                                <div class="modal-header red">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">新 增 判 断 题 目</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" style="width: 100%;" id="judgePlus">
                                        <table style="width: 90%;">
                                            <tr>
                                                <td colspan="1"><strong  style="white-space: nowrap">题目:</strong></td>
                                                <td colspan="3"><textarea id="judgeContent" style="width: 100%;max-width:100%;height: 100px;"></textarea></td>
                                            </tr>

                                            <tr>
                                                <td><strong>答案:</strong></td>
                                                <td colspan="3" align="left" >
                                                    <label class="radio inline " style="max-width: 50px;">
                                                        <input id="judgeAnswer" type="radio" name="singleOptionsRadios"value="1" checked> <span class="icon-ok success"></span>
                                                    </label>
                                                    &nbsp;&nbsp;
                                                    <label class="radio inline">
                                                        <input type="radio" name="singleOptionsRadios" value="2" class="error"> <span class="icon-remove"></span>
                                                    </label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="1"><strong>注释:</strong></td>
                                                <td colspan="3"><input id="judgeTip" type="text" name="tip" placeholder="题目注释" style="width: 100%;" ></td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" onclick="">提交</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <!-- 部件 -->
    <!--公用提示模态框-->
    <div class="modal hide fade" id="tipModal" tabindex="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">提 示</h4>
                </div>
                <div class="modal-body">
                    <h6 id="tipContent">提示内容</h6>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">确定</button>
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
                    <h4 class="modal-title">提 示</h4>
                </div>
                <div class="modal-body">
                    <h6 id="chooseContent">提示内容</h6>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="">确定</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>

    <!--公用批量导入模态框-->
    <div class="modal hide fade" id="uploadModal" tabindex="0" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="true">
        <div class="modal-dialog" role="document" >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择批量导入Excel文件</h4>
                </div>
                <div class="modal-body text-center">
                    <form action="" method="post" enctype="multipart/form-data" style="padding-top: 10px;">
                        <input id="chooseFile" type="file" style="display:none" accept="application/vnd.ms-excel">
                        <div class="input-append">
                            <input id="showUrl"  type="text" readonly>
                            <a class="btn btn-primary" onclick="$('input[id=chooseFile]').click();">浏览文件</a>
                        </div>
                    </form>
                    <h6 style="color: #ff150e">**  提示：Excel表格表头必须包含列表头:“内容”、“A”、“B”、“C”、“D”、“注释”、“答案”.  **</h6>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" onclick="">确定上传</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"  aria-hidden="true">取消</button>
                </div>
            </div>
        </div>
    </div>


    <script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script>
        $("#singleModal").on('hidden', function () {
            /*拟态框隐藏事件，用于初始化输入框，因为拟态框隐藏不会再次初始化，会保留之前输入的数据           单项*/
            $("#singleContent").val("");
            $("#singleA").val("");
            $("#singleB").val("");
            $("#singleC").val("");
            $("#singleD").val("");
            $("#singleTip").val("");
        })

        $("#mutilModal").on('hidden', function () {
            /*拟态框隐藏事件，用于初始化输入框，因为拟态框隐藏不会再次初始化，会保留之前输入的数据           多项*/
            $("#mutilContent").val("");
            $("#mutilA").val("");
            $("#mutilB").val("");
            $("#mutilC").val("");
            $("#mutilD").val("");
            $("#mutilTip").val("");
        })

        $("#judgeModal").on('hidden', function () {
            /*拟态框隐藏事件，用于初始化输入框，因为拟态框隐藏不会再次初始化，会保留之前输入的数据           判断*/
            $("#judgeContent").val("");
            $("#judgeTip").val("");
        })

        $("#uploadModal").on('hidden', function () {
            /*拟态框隐藏事件，用于初始化输入框，因为拟态框隐藏不会再次初始化，会保留之前输入的数据*/
            $("#chooseFile").val("");
            $("#showUrl").val("");
        })

        $('input[id=chooseFile]').change(function() {
            $('#showUrl').val($(this).val());
        });
    </script>
</body>
</html>