function valid(){
	var id = document.getElementById("id").value;
	var pwd = document.getElementById("password").value;
	if ( id.trim() == "" ){
		$("#msg").text("请输入账号");
		$("#subBtn").addClass("disabled");
		$("#wrongTip").removeClass("hidden");
		return false;
	}else if( pwd.trim() == "" ){
		$("#msg").text("请输入密码");
		$("#subBtn").addClass("disabled");
		$("#wrongTip").removeClass("hidden");
		return false;
	}else{
		return true;
	}
}

