//여행제목 수정
$('.updateTitle-icon').click(function() {
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
        alert("여행 제목이 변경되었습니다.");
        window.location.href = urlpathname;
    }).fail(function (e) {
        console.log("err");
        console.log(e.status);
        console.log(e.responseText);
    });
});

//나의 일정 삭제
$('.threeplan-btn.deleteSavedBtn').click(function() {
    let code = $(this).data('code');
    let urlpathname = $(location).attr('pathname');

    $.ajax({
        method: "POST",
        url: "/triplan/mypage/delete",
        data: {
            "code": code,
            "urlpathname" : urlpathname
        }
    }).done(function(response){
        alert("여행 일정이 삭제되었습니다.");
        window.location.href = urlpathname; // 현재 삭제 시킨 일정이 있는 url(다가올 일정, 완료된 일정 중)로 리다이렉트
    }).fail(function(e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});

//삭제 체크박스 선택, 전체선택
$('#all').click(function () {
    if($("input:checkbox[id='all']").prop("checked")) {
        $("input[name=cb1]").prop('checked', true);
    } else {
        $("input[name=cb1]").prop('checked', false);
    }
});

//체크한 나의 댓글 삭제
$('.btn.btn-primary.reply-delete-btn').click(function() {
   let checkBoxArr = [];
    $('.form-check-input.reply-cb:checked').each(function() {
        checkBoxArr.push($(this).data('code')); // 체크된 댓글 code 배열에 넣기
    })

    $.ajax({
        method: "POST",
        url: "/triplan/mypage/reply/delete",
        data: {
            "checkBoxArr" : checkBoxArr // 배열 객체 controller로 전달
        }
    }).done(function(response) {
        alert("댓글이 삭제되었습니다.");
        window.location.href = "/triplan/mypage/reply";
    }).fail(function(e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});

//체크한 좋아요한 일정 삭제
$('.btn.btn-primary.like-delete-btn').click(function() {
   let checkBoxArr = [];
    $('.form-check-input.like-cb:checked').each(function() {
        checkBoxArr.push($(this).data('code')); // 체크된 일정 code 배열에 넣기
    })

    $.ajax({
        method: "POST",
        url: "/triplan/mypage/like/delete",
        data: {
            "checkBoxArr" : checkBoxArr // 배열 객체 controller로 전달
        }
    }).done(function(response) {
        alert("좋아요한 일정이 삭제되었습니다.");
        window.location.href = "/triplan/mypage/like";
    }).fail(function(e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});

// 비밀번호 변경 3단계 체크하기 위한 변수 ---------------
let chk1 = true;
let chk2 = true;
let chk3 = true;
// --------------------------------------------------

// 회원정보 수정 (비밀번호 변경 3단계)
$('.userupdate-btn').click(function(){

    // --- 현재 비밀번호 확인
    $('#currentPwMsg').css("color", '#e01b1b');
    let dbPw = $('#db-userpw').val(); // 기존 db의 pw
    let currentPw = $('.form-control.current-pw').val(); //입력받은 현재 비밀번호

    if(currentPw === dbPw) {
        $('#currentPwMsg').html('');
        chk1 = true;
    } else {
        $('#currentPwMsg').html('<b>현재 비밀번호를 정확히 입력해주세요.</b>');
        $("#dbUserPw").focus();
        chk1 = false;
        return false;
    }

    // --- 새 비밀번호
    $('#newPwMsg').css("color", '#e01b1b');
	let pwNew = $("#pwNew").val();

	if(pwNew === "" || pwNew.length == 0){ // 해당 입력폼 비었으면
		$('#newPwMsg').html('<b>비밀번호는 필수 정보입니다.</b>');
		$("#pwNew").focus();
		chk2 = false;
		return false;
	} else if(!/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/.test(pwNew)){ //비밀번호 유효성검사
		$('#newPwMsg').html('<b>비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</b>');
		$("#pwNew").focus();
		chk2 = false;
		return false;
	} else {
		$('#newPwMsg').html('');
		chk2 = true;
	}

	// --- 비밀번호 확인
    let newPwCheck = $("#newPwCheck").val();
    $('#newPwCheckMsg').css("color", '#e01b1b');

	if(newPwCheck === "" || newPwCheck.length == 0) { // 해당 입력폼 비었으면
		$('#newPwCheckMsg').html('<b>비밀번호 확인은 필수 정보입니다.</b>');
		$("#newPwCheck").focus();
		chk3 = false;
		return false;
	} else if(pwNew != newPwCheck) { // 새 비밀번호와 비밀번호 확인 일치하지 않으면
		$('#newPwCheckMsg').html('<b>비밀번호가 일치하지 않습니다.</b>');
		$("#newPwCheck").focus();
		chk3 = false;
		return false;
	} else {
		$('#newPwCheckMsg').html('');
		chk3 = true;
    }

   // --- 비밀번호 변경 요청 처리하기
    if(chk1 && chk2 && chk3) {
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

// 회원탈퇴 버튼 모달 - 엔터키 누르면 탈퇴버튼
$('#dropModal').on('keypress', 'input', function(e){
    let passwordLength = $('.form-control.password-confirm').val().length;
    if(e.key == "Enter"  && passwordLength != 0) { // 비밀번호 입력하고 엔터키 누른 경우
        e.preventDefault();
        $('#modal-drop-btn').trigger('click');
    }
});

//회원탈퇴 버튼 모달
$('#modal-drop-btn').click(function() {
    let name = $('#name').val();
    let nickname = $('#nickname').val();
    let user_code = $(this).data('code');
    let userpw = $('#db-pw').val(); // 기존 db의 pw
    let pwconfirm = $('.form-control.password-confirm').val(); //입력받은 pw;

    if(pwconfirm == "") {
        alert('탈퇴를 원하시면 비밀번호를 입력해주세요.');
    } else if(userpw != pwconfirm ){
        alert('비밀번호가 일치하지 않습니다.')
    } else {
        $.ajax({
            method: "POST",
            url: "/triplan/myprofile/drop",
            data: JSON.stringify({
                "name" : name,
                "user_code": user_code
                }),
                contentType: "application/json"
        }).done(function(response){
            console.log(response);
            alert("탈퇴 되었습니다.");
            window.location.href = "/triplan/main";
        }).fail(function (e) {
            console.log("err");
            console.log(e.status);
            console.log(e.responseText);
        });
    }
});

// 동행자 검색 모달 - 엔터키 누르면 검색버튼
$('#matemodal').on('keypress', 'input', function(e){
    let mateEmailLength = $('.form-control.mate-email').val().length;
    if(e.key == "Enter"  && mateEmailLength != 0) { // 이메일 입력하고 엔터키 누른 경우
        e.preventDefault();
        $('.btn.btn-primary.mate-search').trigger('click');
    }
});

//동행자 추가 모달에 해당 일정의 plan.code 알려주기
$('.threeplan-btn.mateAddBtn').click(function(){
    let pCode = $(this).data('code'); // plan.code
    $('button#mate-addBtn').data('code', pCode); // 모달의 추가버튼에 해당 일정code를 data-code로 넣기
});

// 동행자 검색 모달
$('.btn.btn-primary.mate-search').click(function() {
    let mateEmail = $('.form-control.mate-email').val(); //입력된 이메일
    $.ajax({
            method: "POST",
            url: "/triplan/mypage/searchMate",
            data: {
                "mateEmail": mateEmail
            }
    }).done(function(response){ // controller 통해 받은 response(동행자 회원)
        console.log(response);
        if(response != ""){
            $('div#user_id').text(response.id); // 받은 이메일
            $('div#user_nickname').text(response.nickname); // 검색된 닉네임
            $('div#user_code').text(response.user_code); // 검색된 user_code
            $('button#mate-addBtn').css('display', 'block'); // 추가버튼 보이기
        } else {
            $('div#user_id').text("검색 결과가 없습니다."); // 검색한 id db에 없어서 받아온게 없을 때
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
    let plan_code = $('button#mate-addBtn').data('code');
    let user_code = $('.mate-user_code').text();
    let urlPathName = $(location).attr('pathname');

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
            window.location.href = urlPathName;
        }).fail(function (e) {
            console.log("err");
            console.log(e.status);
            console.log(e.responseText);
        });
});