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

//**************************查找信息**************************
function queryRecord(){
	var condi = $("#condi").val();//判断查询条件
	/*if (condi == "none"){
		showWrongTip("请选择查询条件");
		return;
	}*/
	var condiValue = $("#condiValue").val();//判断查询条件值
	if (condiValue.trim() == ""){
		showWrongTip("请输入查询关键字");
		return;
	}
	location.href = "historyManageView?condi="+condi+"&condiValue="+condiValue;
}

//******************删除所选记录**************************
function deleteHistory(){
	var delId = "";
	$("input[name='recordCB'][type=checkbox]").each(function() {
		if (this.checked){
			delId = delId + $(this).val() + "|";
		}
	});
	//判断是否选中题目
	if (delId == ""){
		showWrongTip("请 选 择 要 删 除 的 记 录");
		return;
	}
	$("#chooseOk").unbind("click");
	showChoose("确 定 删 除 所 选 记 录 吗 ？");
	$("#chooseOk").bind("click", function(){
		$.ajax({
			type: "post",
			url: $("#url").val()+"/admin/deleteHistory",
			data: "hid=" + delId,
			success: function(resp){
				$("#chooseModal").modal('hide');
				location.href = "historyManageView?" + $("#param").val();
			},
			error: function(resp){
				$("#chooseModal").modal('hide');
				showWrongTip("删除出错");
			}
		});
	});
}

//**********************下载记录Excel*********************
function downloadHistory(){
	var dlCondi = $("input[name='dlCB']:CHECKED").val();
	location.href = "downloadHistories?dlCondi=" + dlCondi;
}

//**************************注销**************************
function loginout(){
	$("#chooseOk").unbind("click");
	showChoose("确 定 要 注 销 管  理  员  账  号  吗 ？");
	$("#chooseOk").bind("click", function(){
		window.location.href=$("#url").val()+"/admin/loginout";
	});
}

/**
 * 清空数据
 */
function emptyData(){
	$("#deleteModal").modal('show');
	$("#deleteOk").unbind("click");
	
	$("#deleteOk").bind("click", function(){
		var password = $("#admin_password").val().trim();
		if(password == ''){
			showWrongTip("请输入密码");
			return;
		}
		$.ajax({
			type: "post",
			url: $("#url").val()+"/admin/deleteAllHistories",
			data: "password=" + password,
			success: function(resp){
				if(resp.success){
					$("#deleteModal").modal('hide');
					location.href = "historyManageView?" + $("#param").val();
				}
				else{
					showWrongTip(resp.msg);
				}
			},
			error: function(resp){
				showWrongTip("服务器出错");
			}
		});
	});
}