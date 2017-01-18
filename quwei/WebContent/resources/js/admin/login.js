
function valid(){
	var id = document.getElementById("id").value;
	var pwd = document.getElementById("password").value;
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
			url:"admin/doLogin",
			dataType:"json",
			data:{"id":id,"password":pwd},
			success:function(response){
			
			if(response.success){
				location.href="admin/mainView";
				
			}else{
				$("div.tip-text").text(response.msg);
			}

			}//success
		});//ajax
	}

}

function showWrongMsg(msg){
	$("#msg").text(msg);
	$("#subBtn").addClass("disabled");
	$("#wrongTip").removeClass("hidden");
}
