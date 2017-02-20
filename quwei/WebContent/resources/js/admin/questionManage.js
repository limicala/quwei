//显示错误提示信息
function showWrongTip(msg){
	$("#tipContent").text(msg);
	$("#tipContent").addClass("text-error");
	$("#tipContent").removeClass("text-success");
	$("#tipModal").modal('show');
}

//显示正确正确信息
function showRightTip(msg){
	$("#tipContent").text(msg);
	$("#tipContent").removeClass("text-error");
	$("#tipContent").addClass("text-success");
	$("#tipModal").modal('show');
}

//显示选择信息
function showChoose(msg){
	$("#chooseContent").text(msg);
	$("#chooseModal").modal('show');
}

//清除单选模态框内容
function singleClean(){
	$("#singleContent").val("");
    $("#singleA").val("");
    $("#singleB").val("");
    $("#singleC").val("");
    $("#singleD").val("");
    $("#singleTip").val("");
    $("#singleId").val("");
    $("#tipModal").modal('hide');
}

//关键字查询题目信息
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

//添加单条单项选择题目
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
	var answer = $("input[name='singleOptionsRadios']:checked").val().trim();
	if (answer == ""){
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

//设置答题限定状态
function setState(ob){
	var id = ob.id;
	var state =  $("#"+id).find("option:selected").val();
	var ostate = $("#o"+id+"State").val();

	if (state != ostate){
		$.ajax({  
	        type: "post",
	        url: $("#url").val()+"/admin/setQuestionState",
	        data: "id=" + id + "&state=" + state,
	        success: function(success) {
	        	if (state == 0){
	        		$("#"+id).removeClass("text-error");
	        	}else{
	        		$("#"+id).addClass("text-error");
	        	}
	        	$("#o"+id+"State").val(state);
	        },
			error: function(resp){
	        	showWrongTip(resp.msg);
			}
	    }); 
	}
}

//编辑单选题
function edit(ob){
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
    $("input[name='singleOptionsRadios'][value="+answer+"]").attr("CHECKED", true);
    $("#singleModal").modal('show');  
}

function update(){
	var old_account = $('#oldN').val();  
    var account = $('#editN').val();  
    var password = $('#editP').val();  
    
}

