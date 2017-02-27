//**************************显示选择信息**************************
function showChoose(msg){
	$("#chooseContent").text(msg);
	$("#chooseModal").modal('show');
}

function edit(obj){
    		
	var id = $(obj).attr("id"); 
	//alert(id+table_id);
    //获取表格中的一行数据  
    var account = document.getElementById("table").rows[id].cells[0].innerText;  
    var password = document.getElementById("table").rows[id].cells[1].innerText;
    //向模态框中传值 
    $('#oldN').val(account);  
    $('#editN').val(account);  
    $('#oldP').val(password);  
    $('#editP').val(password);  
}

function delete_admin(){
    $.ajax({  
        type: "post",  
        url: $("#url").val()+"/admin/delete",  
        data: "id=" + $('#delete_id').val(),  
       /*  dataType: 'html',  
        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
        success: function(result) {  
            //location.reload();  
        	$("#judgeModal").modal('hide');
        	$("#message").text("删除成功");
        	$("#tipModal").modal();
        }  
    });  
}

function update(){
	var old_account = $('#oldN').val();  
    var account = $('#editN').val().trim();  
    var old_password = $('#oldP').val().trim();
    var password = $('#editP').val().trim();  
    if(account == '' || password == ''){
    	$("#check_tip").html("<span style='color:red'>请填写信息再提交</span>");
    }
    else if(old_account==account&& old_password== password){
    	if(old_account==''){//添加新用户
    		$("#check_tip").html("<span style='color:red'>请填写信息再提交</span>");
    	}else{
    		$("#check_tip").html("<span style='color:red'>请修改信息再提交</span>");
    	}
    	
    }else{
    	$.ajax({  
	        type: "post",  
	        url: $("#url").val()+"/admin/update",  
	        data: "old_account=" + old_account + "&account=" + account + "&password=" + password,  
	       /*  dataType: 'html',  
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
	        success: function(result) {  
	            //location.reload();  
	            $("#editModal").modal('hide');
	            if(old_account==''){//添加新用户
	            	$("#message").text("添加成功");
    	    	}else{
    	    		$("#message").text("修改成功");
    	    	}
	            
	            $("#tipModal").modal();
	        }  
	    });  
    }
    
}

function get_delete_id(obj){
	var id = $(obj).attr("id"); 
	//alert(id+table_id);
    //获取表格中的一行数据  
    var account = document.getElementById("table").rows[id].cells[0].innerText;
    $('#delete_id').val(account);  
}

function check_exsit(){
	var old_account = $('#oldN').val();
	var account = $('#editN').val();
	if(old_account != account){
		$.ajax({  
	        type: "post",  
	        url: $("#url").val()+"/admin/checkAid",  
	        data: "account=" + account,  
	       /*  dataType: 'html',  
	        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
	        success: function(result) {  
	            //location.reload();  
	           /*  $("#editModal").modal('hide');
	            $("#message").text("修改成功");
	            $("#tipModal").modal(); */
	            //console.log(result);
	            if(result){
	            	$("#check_tip").html("<span style='color:red'>用户名已存在</span>");
	            	//aaaa = "<span hidden=true style="color:red">用户名已存在</span>";
	            	$("#update_button").attr("disabled","disabled");
	            }else{
	            	$("#check_tip").html("<span style='color:green'>用户名可用</span>");
	            	$("#update_button").removeAttr("disabled");
	            }
	        }  
	    }); 
	}
	
}
function search(){
	account = $("#account").val();
	if (account.trim() == ""){
		$("#message").text("请输入账号");
		$("#tipModal").modal();
		return;
	}
	location.href = "userManageView?account="+account;
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