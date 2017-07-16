$("form").submit(function(e){
    e.preventDefault();
    var id = document.getElementById("id").value;
	var pwd = document.getElementById("password").value;
	var verify_code = document.getElementById("verify_code").value;
	if ( id.trim() == "" ){
		showWrongMsg("请输入账号");
		flag = false;
	}else if( pwd.trim() == "" ){
		showWrongMsg("请输入密码");
		flag = false;
	}else{
		flag = true;
	}
	if (flag){
		$.ajax({
			url:$("#url").val()+"/admin/doLogin",
			dataType:"json",
			data:{"id":id,"password":pwd,"verify_code":verify_code},
			success:function(response){
				if(response.success){
					location.href=$("#url").val()+"/admin/mainView";
				}else{
					showWrongMsg(response.msg);
					$("#verify_img").attr("src",$("#url").val()+"/admin/img?time="+Math.random())
				}
			}
		});
	}
});

function showWrongMsg(msg){
	$("#msg").text(msg);
	$("#subBtn").addClass("disabled");
	$("#wrongTip").removeClass("hidden");
}
