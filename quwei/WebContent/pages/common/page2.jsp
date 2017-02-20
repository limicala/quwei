<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="inline">
               <li style="float:left;">
                   <div>
                       <br>
                       <h6>共<a>${page2.totalRow }</a>条记录，当前显示第&nbsp;<a>${page2.pageNumber }&nbsp;</a>页</h6>
                   </div>
               </li>
               
               <li style="float:right;">
                   <div class="pagination"  ><!--分页-->
                       <ul>
                           <!-- 上一页 -->
                       	   <c:choose>
								<c:when test="${page2.totalPage eq 0 }">
									<li class="active"><a>&lsaquo;&lsaquo;</a></li>
								</c:when>
								<c:when test="${page2.pageNumber eq 1 }">
									<li class="active"><a>&lsaquo;&lsaquo;</a></li>
								</c:when>
								
								<c:otherwise>
									<li><a href="${url }?ct=3&jpn=${page2.pageNumber - 1 }&pageNumber=${page2.pageNumber - 1 }">&lsaquo;&lsaquo;</a></li>
								</c:otherwise>
							</c:choose>


						   <%--
							必备条件，页码列表的开始和结束位置    begin 和 end
							通过当前页计算得出begin和end
							1.总页数不足6页-->begin=1 end为最大页
							2.通过公式设置begin和end，begin=当前页-1，end=当前页+3
							3.如果begin<1,那么让begin=1,end=6
							4.如果end>tp,让begin=5, end=tp
							 --%>
							 <c:choose>
							 	<c:when test="${page2.totalPage <=6 }">
							 		<c:set var="begin" value="1"/>
							 		<c:set var="end" value="${page2.totalPage }"/>
							 	</c:when>
							 	<c:otherwise>
							 		<c:set var="begin" value="${page2.pageNumber - 2 }"/>
							 		<c:set var="end" value="${page2.pageNumber + 3 }"/>
							 		<c:if test="${begin < 1 }">
							 			<c:set var="begin" value="1"/>
							 			<c:set var="end" value="6"/>
							 		</c:if>
							 		<c:if test="${end > page2.totalPage }">
							 			<c:set var="begin" value="${page2.totalPage - 5 }"/>
							 			<c:set var="end" value="${page2.totalPage }"/>
							 		</c:if>
							 	</c:otherwise>
							 </c:choose>
							 
							 <c:forEach begin="${begin }" end="${end }" var="i">
							 	<c:choose>
							 		<c:when test="${i eq page2.pageNumber }">
							 			<li class="active"><a>${i }</a></li>
							 		</c:when>
							 		<c:otherwise>
							 			<li><a href="${url }?ct=3&jpn=${i }&pageNumber=${i}">${i }</a></li>
							 		</c:otherwise>
							 	</c:choose>
							 </c:forEach>
							
							<%-- 显示点点点 --%>
						    <c:if test="${end < page2.totalPage }">
						      <li><a>...</a></li>
						    </c:if> 

                           
                           <!-- 下一页 -->
                 
                           <c:choose>
								<c:when test="${page2.totalPage eq 0 }">
									<li class="active"><a href="#">&rsaquo;&rsaquo;</a></li>
								</c:when>
								<c:when test="${page2.pageNumber eq page2.totalPage }">
									<li class="active"><a href="#">&rsaquo;&rsaquo;</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${url }?ct=3&jpn=${page2.pageNumber+1 }&pageNumber=${page2.pageNumber+1 }">&rsaquo;&rsaquo;</a></li>
								</c:otherwise>
							</c:choose>
                       </ul>
                   </div>
               </li>
           </ul>
           