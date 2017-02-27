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
function update_answertime(){
	var cid = $('#cid').val();
	var answer_time = $('#answer_time').val();   
	var old_time = $('#old_time').val();
	if(answer_time == old_time){
		showWrongTip("请修改时间间隔再提交");
		return;
	}
	$.ajax({  
        type: "post",  
        url: $("#url").val()+"/admin/update_config",  
        data: "configOS.canswertime=" + answer_time + "&configOS.cid="+cid,  
       /*  dataType: 'html',  
        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
        success: function(result) {  
            //location.reload();  
          /*   $("#editModal").modal('hide'); */
          	if(result){
          		showRightTip("修改成功");
          	}
          	else{
          		showWrongTip("修改失败");
          	}
        }  
    }); 
}

function update_interval(){
	var cid = $('#cid').val();
	var interval = $('#interval').val();   
	var old_interval = $('#old_interval').val();
	if(interval == old_interval){
		showWrongTip("请修改时间间隔再提交");
		return;
	}
	$.ajax({  
        type: "post",  
        url: $("#url").val()+"/admin/update_config",  
        data: "configOS.cdayinterval=" + interval + "&configOS.cid="+cid,  
       /*  dataType: 'html',  
        contentType: "application/x-www-form-urlencoded; charset=utf-8",   */
        success: function(result) {  
            //location.reload();  
          /*   $("#editModal").modal('hide'); */
          	if(result){
          		showRightTip("修改成功");
          	}
          	else{
          		showWrongTip("修改失败");
          	}
        }  
    }); 
}

function update_score(){
	var cid = $('#cid').val();
	var judge_num = $('#judge_num').val();   
	var judge_score = $('#judge_score').val();   
	var single_num = $('#single_num').val();   
	var single_score = $('#single_score').val();   
	var multi_num = $('#multi_num').val();   
	var multi_score = $('#multi_score').val();   
	var startword = $('#startword').val();
	var endword = $('#endword').val();
	//alert(startword);
	
	total_score = judge_num * judge_score + single_num * single_score + multi_num * multi_score;
	//alert(total_score);
	if(total_score != 100){
		showWrongTip("总分应为100分，你当前设置的总分为"+total_score);	
		return;
	}
	$.ajax({
		url:$("#url").val()+"/admin/update_config",  
		dataType:"json",
		data:{
			"configOS.cid":cid,
			"configOS.cjudge_num":judge_num,
			"configOS.cjudge_score":judge_score,
			"configOS.csingle_num":single_num,
			"configOS.csingle_score":single_score,
			"configOS.cmulti_num":multi_num,
			"configOS.cmulti_score":multi_score,
			"configOS.cstartword":startword,
			"configOS.cendword":endword,
		},
		success:function(result){
		
			if(result){
          		showRightTip("修改成功");
          	}
          	else{
          		showWrongTip("修改失败");
          	}
		}//success
	});//ajax
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