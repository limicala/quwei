//******************显示错误提示信息**************************
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

//**************************显示选择信息**************************
function showChoose(msg){
	$("#chooseContent").text(msg);
	$("#chooseModal").modal('show');
}

//************************** 清 除 单 选 模 态 框 内 容 **************************
function singleClean(){
	$("#singleId").val("");
	$("#singleContent").val("");
    $("#singleA").val("");
    $("#singleB").val("");
    $("#singleC").val("");
    $("#singleD").val("");
    $("#singleTip").val("");
    $("input[name='singleOptionsRadios']").attr("CHECKED", null);
}

//************************** 清 除 多 选 模 态 框 内 容 **************************
function multiClean(){
	$("#multiId").val("");
	$("#multiContent").val("");
    $("#multiA").val("");
    $("#multiB").val("");
    $("#multiC").val("");
    $("#multiD").val("");
    $("#multiTip").val("");
    $("input[name='multiOptionsRadios'][type=checkbox]").each(function() {
    	$(this).removeAttr("CHECKED");
	});
}

//************************** 清 除 判 断 模 态 框 内 容 **************************
function judgeClean(){
	$("#judgeId").val("");
	$("#judgeContent").val("");
    $("#judgeTip").val("");
    $("input[name='judgeOptionsRadios']").attr("CHECKED", null);
}

//**************************关键字查询题目信息**************************
function query(ob){
	var condi = ob.id+"condi";
	var condiValue = $("#"+condi).val();
	if (condiValue.trim() == ""){
		showWrongTip("请输入题目关键字");
		return;
	}
	//获取tab位置码
	var ct = "";
	if (ob.id == "s"){
		ct = "1";
	}else if (ob.id == "m"){
		ct = "2";
	}else{
		ct = "3";
	}
	location.href = "questionManageView?ct="+ct+"&"+condi+"="+condiValue;
}

//************************** 添 加 单 条 单 项 选 择 题 目 **************************
function addSingle(){
	var id = $("#singleId").val();
	var content = $("#singleContent").val().trim();
	if (content == ""){
		showWrongTip("题目内容不能为空");
		return;
	}
	var a = $("#singleA").val().trim();
	var b = $("#singleB").val().trim();
	var c = $("#singleC").val().trim();
	var d = $("#singleD").val().trim();
	if ( a == "" || b == "" || c == "" || d == "" ){
		showWrongTip("选项内容不能为空");
		return;
	}
	var answer = $("input[name='singleOptionsRadios']:CHECKED").val();
	if (answer == undefined){
		showWrongTip("题目答案不能为空");
		return;
	}
	var explain = $("#singleTip").val().trim();
	$.ajax({
        type: "post",  
        url: $("#url").val()+"/admin/updateQuestion",
        data: "qtype=2&id=" + id + "&content=" + content + "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d + "&answer=" + answer + "&explain=" + explain,
        success: function(resp) {
        	if (resp.type == "add"){//添加类型
        		if (resp.success){
            		showRightTip(resp.msg);
            		singleClean();
            	}else{
            		showWrongtTip(resp.msg);
            	}
        	}else{//编辑类型
        		if (resp.success){
            		showRightTip(resp.msg);
            		$("#"+id+"Content").text(content);
            		$("#"+id+"A").text(a);
            		$("#"+id+"B").text(b);
            		$("#"+id+"C").text(c);
            		$("#"+id+"D").text(d);
            		$("#"+id+"Explain").text(explain);
            		$("#"+id+"Answer").text(answer);
            	}else{
            		showWrongtTip(resp.msg);
            	}
        	}
        },
		error: function(resp){
			showWrongtTip(resp.msg);
		}
    });
}

//************************** 添 加 单 条 多 项 选 择 题 目**************************
function addMulti(){
	var id = $("#multiId").val();
	var content = $("#multiContent").val().trim();
	if (content == ""){
		showWrongTip("题目内容不能为空");
		return;
	}
	var a = $("#multiA").val().trim();
	var b = $("#multiB").val().trim();
	var c = $("#multiC").val().trim();
	var d = $("#multiD").val().trim();
	if ( a == "" || b == "" || c == "" || d == "" ){
		showWrongTip("选项内容不能为空");
		return;
	}
	var answer = "";
	$("input[name='multiOptionsRadios'][type=checkbox]:CHECKED").each(function() {
		answer = answer + $(this).val();
	});
	if (answer == ""){
		showWrongTip("题目答案不能为空");
		return;
	}
	var explain = $("#multiTip").val().trim();
	$.ajax({
        type: "post",
        url: $("#url").val()+"/admin/updateQuestion",
        data: "qtype=3&id=" + id + "&content=" + content + "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d + "&answer=" + answer + "&explain=" + explain,
        success: function(resp) {
        	if (resp.type == "add"){//添加类型
        		if (resp.success){
            		showRightTip(resp.msg);
            		multiClean();
            	}else{
            		showWrongtTip(resp.msg);
            	}
        	}else{//编辑类型
        		if (resp.success){
            		showRightTip(resp.msg);
            		$("#"+id+"Content").text(content);
            		$("#"+id+"A").text(a);
            		$("#"+id+"B").text(b);
            		$("#"+id+"C").text(c);
            		$("#"+id+"D").text(d);
            		$("#"+id+"Explain").text(explain);
            		$("#"+id+"Answer").text(answer);
            	}else{
            		showWrongtTip(resp.msg);
            	}
        	}
        },
		error: function(resp){
			showWrongtTip(resp.msg);
		}
    });
}

//************************** 添 加 单 条 判 断 题 目 **************************
function addJudge(){
	var id = $("#judgeId").val();
	var content = $("#judgeContent").val().trim();
	if (content == ""){
		showWrongTip("题目内容不能为空");
		return;
	}

	var answer = $("input[name='judgeOptionsRadios']:CHECKED").val();
	if (answer == undefined){
		showWrongTip("题目答案不能为空");
		return;
	}
	var explain = $("#judgeTip").val().trim();
	$.ajax({
        type: "post",  
        url: $("#url").val()+"/admin/updateQuestion",
        data: "qtype=1&id=" + id + "&content=" + content + "&answer=" + answer + "&explain=" + explain,
        success: function(resp) {
        	if (resp.type == "add"){//添加类型
        		if (resp.success){
            		showRightTip(resp.msg);
            		judgeClean();
            	}else{
            		showWrongtTip(resp.msg);
            	}
        	}else{//编辑类型
        		if (resp.success){
            		showRightTip(resp.msg);
            		$("#"+id+"Content").text(content);
            		$("#"+id+"Explain").text(explain);
            		if (answer == "1"){
            			$("#"+id+"Icon").text("√");
            			$("#"+id+"Icon").removeClass();
            			$("#"+id+"Icon").addClass("text-success");
            		}else{
            			$("#"+id+"Icon").text("×");
            			$("#"+id+"Icon").removeClass();
            			$("#"+id+"Icon").addClass("text-error");
            		}
            		$("#"+id+"Answer").val(answer);
            	}else{
            		showWrongtTip(resp.msg);
            	}
        	}
        },
		error: function(resp){
			showWrongtTip(resp.msg);
		}
    });
}

//**************************设置答题限定状态**************************
function setState(ob){
	var id = ob.id;
	var state =  $("#"+id).find("option:selected").val();
	var ostate = $("#o"+id).val();
	
	var qid = id.substring(0, id.length - 5);

	if (state != ostate){
		$.ajax({  
	        type: "post",
	        url: $("#url").val()+"/admin/setQuestionState",
	        data: "id=" + qid + "&state=" + state,
	        success: function(success) {
	        	if (state == 0){
	        		$("#"+id).removeClass("text-error");
	        	}else{
	        		$("#"+id).addClass("text-error");
	        	}
	        	$("#o"+id).val(state);
	        },
			error: function(resp){
	        	showWrongTip(resp.msg);
			}
	    }); 
	}
}

//************************** 编 辑 单 选 题 **************************
function editSingle(ob){
	var id = ob.id;
	var content = $("#"+id+"Content").text();
	var a = $("#"+id+"A").text();
	var b = $("#"+id+"B").text();
	var c = $("#"+id+"C").text();
	var d = $("#"+id+"D").text();
	var explain = $("#"+id+"Explain").text();
	var answer = $("#"+id+"Answer").text();
	$("#singleId").val(id);
	$("#singleContent").val(content);
    $("#singleA").val(a);
    $("#singleB").val(b);
    $("#singleC").val(c);
    $("#singleD").val(d);
    $("#singleTip").val(explain);

    $(":radio[name='singleOptionsRadios'][value='" + answer + "']").prop("checked", "checked");

    $("#singleModal").modal('show');
}

//************************** 编 辑 多 选 题 **************************
function editMulti(ob){
	var id = ob.id;
	var content = $("#"+id+"Content").text();
	var a = $("#"+id+"A").text();
	var b = $("#"+id+"B").text();
	var c = $("#"+id+"C").text();
	var d = $("#"+id+"D").text();
	var explain = $("#"+id+"Explain").text();
	var answer = $("#"+id+"Answer").text();
	$("#multiId").val(id);
	$("#multiContent").val(content);
    $("#multiA").val(a);
    $("#multiB").val(b);
    $("#multiC").val(c);
    $("#multiD").val(d);
    $("#multiTip").val(explain);
    $("input[name='multiOptionsRadios'][type=checkbox]").each(function() {
		if (answer.indexOf($(this).val()) != -1){
			$(this).attr("CHECKED", true);
		}
	});
    $("#multiModal").modal('show');
}

//************************** 编 辑 判 断 题  **************************
function editJudge(ob){
	var id = ob.id;
	var content = $("#"+id+"Content").text();
	var explain = $("#"+id+"Explain").text();
	var answer = $("#"+id+"Answer").val();
	$("#judgeId").val(id);
	$("#judgeContent").val(content);
    $("#judgeTip").val(explain);
    $(":radio[name='judgeOptionsRadios'][value='" + answer + "']").prop("checked", "checked");
    $("#judgeModal").modal('show');
}


//************************** 删 除 题 目  **************************
function deleteQuestion(ob){
	var delId = ob.id.substring(0, ob.id.length - 3);
	$("#chooseOk").unbind("click");
	showChoose("确 定 删 除 该 题 目 信 息 吗 ？");
	$("#chooseOk").bind("click", function(){
		$.ajax({
			type: "post",
			url: $("#url").val()+"/admin/deleteQuestion",
			data: "delType=s&id=" + delId,
			success: function(resp){
				if (resp.success){
					$("#chooseModal").modal('hide');
					location.href = "questionManageView?" + $("#param").val();
				}else{
					showWrongTip(resp.msg);
				}
			},
			error: function(resp){
				showWrongTip(resp.msg);
			}
		});
	});
}

//************************** 删 除 选 择 题 目  **************************
function deleteQuestions(ob){
	var id = ob.id.substring(0, ob.id.length - 4);
	var delId = "";
	//根据点击相应所属页面“删除所选”按钮获取相应题目的选中编号
	if (id == "single"){
		$("input[name='singleCB'][type=checkbox]").each(function() {
			if (this.checked){
				delId = delId + $(this).val() + "|";
			}
		});
	}else if(id == "multi"){
		$("input[name='multiCB'][type=checkbox]").each(function() {
			if (this.checked){
				delId = delId + $(this).val() + "|";
			}
		});
	}else if(id == "judge"){
		$("input[name='judgeCB'][type=checkbox]").each(function() {
			if (this.checked){
				delId = delId + $(this).val() + "|";
			}
		});
	}else{
		showWrongTip("错误");
	}
	//判断是否选中题目
	if (delId == ""){
		showWrongTip("请选择要删除的题目");
		return;
	}
	$("#chooseOk").unbind("click");
	showChoose("确 定 删 除 所 选 题 目 信 息 吗 ？");
	$("#chooseOk").bind("click", function(){
		$.ajax({
			type: "post",
			url: $("#url").val()+"/admin/deleteQuestion",
			data: "delType=m&id=" + delId,
			success: function(resp){
				$("#chooseModal").modal('hide');
				location.href = "questionManageView?" + $("#param").val();
			},
			error: function(resp){
				$("#chooseModal").modal('hide');
				showWrongTip("删除出错");
			}
		});
	});
}

/**
 * 模板类型
 */
var templateType = "";
//****************** 修 改 flag**************************
function changeUpLoadflag(ob){
	templateType = ob.id;
	$("#uploadType").val(ob.id);
}

/**
 * 下载上传模板
 * @returns
 */
function doDownloadTemplate(){
	if (templateType != "")
		window.location.href = "downloadTemplate?templateType=" + templateType;
}

/**
 * 执行上传文件
 * @returns
 */
function doUpload(){
	if ($("#showUrl").val() == ""){
		showWrongTip("请选择批量导入文件(Excel)");
	}else{
		$("#uploadForm").submit();
	}
}
/**
 * 注销
 * @returns
 */
function loginout(){
	$("#chooseOk").unbind("click");
	showChoose("确 定 要 注 销 管  理  员  账  号  吗 ？");
	$("#chooseOk").bind("click", function(){
		window.location.href=$("#url").val()+"/admin/loginout";
	});
}
