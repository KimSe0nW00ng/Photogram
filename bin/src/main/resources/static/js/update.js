// (1) 회원정보 수정
function update(userid,event) {
	event.preventDefault();// 폼태그 액션을 막기!
	
	//header.jsp에 j쿼리 쓸수 있도록 해놓음
	let data = $("#profileUpdate").serialize(); // update.jsp에 있는 폼!
	// 폼태그가 가지고 있는 인풋값을 끌어옴
	//key - value 전송할 때
	console.log(data);
	
	$.ajax({
		type:"put",
		url:`/api/user/${userid}`, //백틱 조심합시다
		data: data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json"
		
	}).done(res=>{ // HttpStatue 상태코드 200번대
		console.log("성공",res);
		location.href=`/user/${userid}`;
	}).fail(error=>{ // HttpStatue 상태코드 200번대 아닐 때
	if(error.data == null){
	alert(error.responseJSON.message);		
	}else{
		
	alert(JSON.stringify(error.responseJSON.data)); // object,object 를 JSON문자열로 변환하는 방법
	}
	});
}