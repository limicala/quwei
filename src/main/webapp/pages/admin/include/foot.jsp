<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/frame/jquery/js/jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/frame/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/admin/config.js" type="text/javascript"></script>

<script>
    url = window.location.href;
    viewList = ["userManageView", "stuManageView", "questionManageView", "historyManageView", "configView"];

    for(var i = 0; i < viewList.length; i++){
        item = viewList[i];
        if(url.indexOf(item) > 0){
            $("#"+item).addClass("active");
        }
    }
</script>