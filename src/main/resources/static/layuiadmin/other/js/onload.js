//管理员加载DOM之前执行
function adminOnload(){
	$.ajax({
		url: '/admin/isAdminToken',
		type: 'get',
		dataType: 'json',
		beforeSend:function(request){
			request.setRequestHeader("adminToken",localStorage.getItem("adminToken"));
		},
		success: function(res){
			if(res.status == 2){
				location.href = '/views/admin/login.html';
			}
		}
	});
}
//老师加载DOM之前执行
function teacherOnload(){
	$.ajax({
		url: '/login/isTeacherToken',
		type: 'get',
		dataType: 'json',
		beforeSend:function(request){
			request.setRequestHeader("teacherToken",localStorage.getItem("teacherToken"));
		},
		success: function(res){
			if(res.status == 2){
				location.href = '/views/teacher/login.html';
			}
		}
	});
}
//学生加载之前执行
function studentOnload() {
	$.ajax({
		url: '/login/isStudentToken',
		type: 'get',
		dataType: 'json',
		beforeSend:function(request){
			request.setRequestHeader("studentToken",localStorage.getItem("studentToken"));
		},
		success: function(res){
			if(res.status == 2){
				location.href = '/views/student/login.html';
			}
		}
	});
}