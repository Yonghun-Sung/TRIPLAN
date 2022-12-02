//삭제 체크박스 전체선택 및 전체 해제
$('#all').click(function () {
    if($("input:checkbox[id='all']").prop("checked")) {
        $("input[name=cb1]").prop('checked', true);
    } else {
        $("input[name=cb1]").prop('checked', false);
    }
});



//여행제목 수정 버튼
$('.inputTravelName_idx_a').click(function() {
    let title = $(this).prev().val();
    let code = $(this).data('code');
    let urlpathname = $(location).attr('pathname');

    $.ajax({
            method: "PUT",
            url: "/triplan/mypage/update",
            data: {
                "code": code,
                "title": title
            }
        }).done(function(response){
            console.log(response);
            console.log("success");
            alert("여행 제목이 변경되었습니다.");
            window.location.href = urlpathname;
        })
        .fail(function (e) {
            console.log("err");
            console.log(e.status);
            console.log(e.responseText);
        });
});
//나의 일정 삭제
$('.threeplan-btn.deleteSavedBtn').click(function() {
    let code = $(this).data('code');
    /*let url = $(location).attr('href');*/
    let urlpathname = $(location).attr('pathname');
    alert(urlpathname);
    $.ajax({
        method: "POST",
        url: "/triplan/mypage/delete",
        data: {
            "code": code,
            "urlpathname" : urlpathname
        }
    }).done(function(response){
        console.log("delete!")
        alert("여행 일정이 삭제되었습니다.");
        window.location.href = urlpathname;
    }).fail(function(e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});

//좋아요한 일정 체크한 거 삭제
$('.btn.btn-primary.like-delete-btn').click(function() {
   let checkBoxArr = [];
      /*$("input:checkbox[name='cb1']:checked").each(function() {*/
    $('.form-check-input.like-cb:checked').each(function() {
    alert($(this).data('code'));
    /*checkBoxArr.push(1);*/
    checkBoxArr.push($(this).data('code'));
    alert(checkBoxArr);
    })
    $.ajax({
        method: "POST",
        url: "/triplan/mypage/like/delete",
        data: {
            "checkBoxArr" : checkBoxArr
        }
    }).done(function(response) {
        console.log("like delete!")
        alert("좋아요한 일정이 삭제되었습니다.");
        window.location.href = "/triplan/mypage/like";
    }).fail(function(e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});


//댓글 체크한 거 삭제
$('.btn.btn-primary.reply-delete-btn').click(function() {
   let checkBoxArr = [];
      /*$("input:checkbox[name='cb1']:checked").each(function() {*/
    $('.form-check-input.reply-cb:checked').each(function() {
    alert($(this).data('code')); //reply.code
    /*checkBoxArr.push(1);*/
    checkBoxArr.push($(this).data('code'));
    alert(checkBoxArr);
    })
    $.ajax({
        method: "POST",
        url: "/triplan/mypage/reply/delete",
        data: {
            "checkBoxArr" : checkBoxArr
        }
    }).done(function(response) {
        console.log("reply delete!")
        alert("댓글이 삭제되었습니다.");
        window.location.href = "/triplan/mypage/reply";
    }).fail(function(e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});



let chk1 = true;
let chk2 = true;
let chk3 = true;

// 현재 비밀번호 일치하는지 확인 요청
$('#dbUserPw').on('keyup', function(){
    $('#currentPwMsg').css("color", "#0064d6");
    let dbPw = $('#db-userpw').val(); // 기존 db의 pw
    let currentPw = $('.form-control.current-pw').val(); //입력받은 현재 비밀번호
    /*alert("현재입력받은 비번: " + currentPw);
    alert("디비 비번: "+ dbPw);*/
    if(currentPw === dbPw) {
        $('#currentPwMsg').html('');
        chk1 = true;
    } else {
        $('#currentPwMsg').html('<b>현재 비밀번호를 정확히 입력해주세요.</b>');
        chk1 = false;
    }
/*    error : function(error) {
    console.log(error)
    }*/
});
//새로운 비번
$('#pwNew').on('keyup', function() {
    /*$('#newPwMsg').css('color', '#e01b1b');*/
    $('#newPwMsg').css("color", '#e01b1b');
	//비밀번호 공백 확인
	let pwNew = $("#pwNew").val();
	if($("#pwNew").val() === ""){
		$('#newPwMsg').html('<b>비밀번호는 필수 정보입니다.</b>');
		chk2 = false;
	}
	//비밀번호 유효성검사
	/*else if(!/(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,20}/.test(pwNew)){*//*/(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}/
	/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/*/
	else if(!/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/.test(pwNew)){
		$('#newPwMsg').html('<b>비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</b>');
		chk2 = false;
	} else {
		$('#newPwMsg').html('');
		chk2 = true;
	}
}); //end of new password


//비밀번호 확인
$('#newPwCheck').on('keyup', function() {
    let newPwCheck = $("#newPwCheck").val();
    let pwNew2 = $("#pwNew").val();
    $('#newPwCheckMsg').css("color", '#e01b1b');

	if(newPwCheck === "") {
		$('#newPwCheckMsg').html('<b>비밀번호 확인은 필수 정보입니다.</b>');
		chk3 = false;
	} else if(pwNew2 != newPwCheck) {
		$('#newPwCheckMsg').html('<b>비밀번호가 일치하지 않습니다.</b>');
		chk3 = false;
	} else {
		$('#newPwCheckMsg').html('');
		chk3 = true;
    }
});//end of passwordCheck

//비밀번호 변경 요청처리하기
$('.basic-btn2.userupdate-btn').click(function(){

	if(chk1 == false) {
		alert('현재 비밀번호가 틀렸습니다.');
	} else if(chk2 == false){
		alert('2번 틀림');
	} else if(chk3 == false){
		alert('3번 틀림');

	} else if(chk1 && chk2 && chk3) {
		const nickname = $('#nickname').val();
		const pw = $("#pwNew").val();
		$.ajax({
			method: "PUT",
			url: "/triplan/myprofile/update",
			data:{
			    "nickname": nickname,
                "pw": pw
                }
            }).done(function(response) {
                alert("회원정보가 변경되었습니다.");
                window.location.href = "/triplan/main";
            }).fail(function(e){
                alert('입력정보를 다시 확인하세요.');
            });
    }
    });

//회원탈퇴모달창-탈퇴버튼 클릭하면 id는 세션에서 받을수있어서 제외, name, nickname(마찬가지), code(마찬가지) 보내주기
$('#modal-drop-btn').click(function() {
   /* let id = $('#id')*/
    let name = $('#name').val();
    let nickname = $('#nickname').val();
    alert(name);
    alert(nickname);
    /*let user_code = $(this).data('userprofile.user_code');*/
    let user_code = $(this).data('code');
    alert(user_code);
    let userpw = $('#db-pw').val(); // 기존 db의 pw
    let pwconfirm = $('.form-control.password-confirm').val(); //입력받은 pw;

    if(pwconfirm == "") {
        alert('탈퇴를 원하시면 비밀번호를 입력해주세요.');
    } else if(userpw != pwconfirm ){
        alert('비밀번호가 일치하지 않습니다.')
        /*$('#exampleModal').on('hidden.bs.modal');*/
    } else {
        $.ajax({
            method: "POST",
            url: "/triplan/myprofile/drop",
            data: JSON.stringify({
                /*"id" : id,*/
                "name" : name,
                /*"nickname": nickname,*/
                "user_code": user_code
                }),
                contentType: "application/json"
            }).done(function(response){
                console.log(response);
                console.log("success");
                alert("탈퇴 되었습니다.");
                window.location.href = "/triplan/main";
            })
            .fail(function (e) {
                console.log("err");
                console.log(e.status);
                console.log(e.responseText);
            });
    }

});


// 동행자 검색 모달
$('.btn.btn-primary.mate-search').click(function() {
    let mateEmail = $('.form-control.mate-email').val(); //입력된 이메일
    $.ajax({
            method: "GET",
            url: "/triplan/mypage/searchMate",
            data: {
                "mateEmail": mateEmail //입력된 이메일 controller로 보내주고
            }
        }).done(function(response){
            console.log(response);
            if(response != ""){
                $('div#user_id').text(response.id);
                $('div#user_nickname').text(response.nickname);
                $('div#user_code').text(response.user_code);
                $('button#mate-addBtn').css('display', 'block');
            } else {
                $('div#user_id').text("검색 결과가 없습니다.");
                $('div#user_nickname').text('');
            }
        }).fail(function (e) {
            console.log("err");
            console.log(e.status);
            console.log(e.responseText);
        });
});

// 동행자 추가
$('.btn.btn-secondary.mate-add').click(function() {
    /*let id = $('.mate-id').val();
    let nickname = $('.mate-nickname').val();//입력된 이메일
    alert(id);
    alert(nickname);*/
    /*지금 필요한건 동행자user_code, plan_code*/
    alert("추가버튼 js시작")
    let plan_code = $('.threeplan-btn.deleteSavedBtn').data('code');
    let user_code = $('.mate-user_code').text();
    alert(plan_code);
    alert(user_code);
    $.ajax({
        method: "POST",
        url: "/triplan/mypage/addMate",
        data: JSON.stringify({
            "code" : plan_code,
            "user_code": user_code
            }),
            contentType: "application/json"
        }).done(function(response){
            console.log(response);
            alert("동행자 일정에 추가되었습니다.");
            window.location.href = "/triplan/main";
        }).fail(function (e) {
            console.log("err");
            console.log(e.status);
            console.log(e.responseText);
        });
});

/*$('.threeplan-btn').click(function() {
    $('#matemodal').modal('show');
});*/
