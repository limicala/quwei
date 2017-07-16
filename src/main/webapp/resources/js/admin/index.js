//**************************显示选择信息**************************
function showChoose(msg){
	$("#chooseContent").text(msg);
	$("#chooseModal").modal('show');
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