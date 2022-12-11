let urlParams = new URL(location.href).searchParams;
let errCode = urlParams.get('errCode');
if (errCode == "1") {
    alert('이메일 또는 비밀번호를 확인해주세요.');
}

// --미처리 신고
$('#report-modal').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget);                // button에 바인딩 된 함수 가져오기
    var reportedUser = button.data('reporteduser');     // 피신고자, 신고자, 신고내용, 신고사유, 접수일 가져오기
    var reportUser = button.data("reportuser");
    var content = button.data('content');
    var reason = button.data('reason');
    var dt = button.data('dt');

    $(this).find('#modal_reported').text(reportedUser); // 모달에 데이터 값 전달
    $(this).find('#modal_report').text(reportUser);
    $(this).find('#modal_dt').text(dt);
    $(this).find('#modal_reason').text(reason);
    $(this).find('#modal_content').text(content);

    var code = button.data('code');                     // 접수코드 가져오기
    $('#report-reg-code').val(code);                    // 접수코드 전달

    $('.report-result-btn').click(function() {
        let result_code = $(this).val();                // 신고처리코드 가져오기(버튼 value)
        $('#report-result-code').val(result_code);      // 신고처리코드 전달

        $('#report-proc-form').submit();
    });
});

// --처리내역
$('#report-proc-modal').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget);                // button에 바인딩 된 함수 가져오기
    var reportedUser = button.data('reporteduser');     // 피신고자, 신고자, 신고내용, 신고사유, 접수일 가져오기
    var reportUser = button.data("reportuser");
    var content = button.data('content');
    var reason = button.data('reason');
    var dt = button.data('dt');

    $(this).find('#modal_reported').text(reportedUser); // 모달에 데이터 값 전달
    $(this).find('#modal_report').text(reportUser);
    $(this).find('#modal_dt').text(dt);
    $(this).find('#modal_reason').text(reason);
    $(this).find('#modal_content').text(content);

    let reg_code = button.data('code');                 // 접수코드 가져오기

    $('.report-withdraw-btn').click(function(){
        let result_code = $(this).val();                // 신고처리코드 가져오기

        $.ajax({
            type: "put",
            url: "/triplan/processedReport",
            data: JSON.stringify({
                "reg_code": reg_code,
                "result_code": result_code
            }),
            contentType: "application/json"
        })
        .done(function(response){
            console.log("put success!");
            window.location.href = "/triplan/admin/reportProc";
        });
    });
});

let chk1 = false;
let chk2 = false;
let chk3 = false;

// 비밀번호 변경 - 현재 비밀번호 일치하는지 확인
$('#admindbpw').on('keyup', function(event){
    $('#currentAdminPw').css("color", "#0064d6");
    let DBpw = $('#adminDBpw').val();
    let inputPw = $('#admindbpw').val();

    $.ajax({
        type: "post",
        url: "/triplan/adminInfo",
        data: JSON.stringify({
            "pw" : inputPw
        }),
        contentType: "application/json"
    })
    .done(function(data) {
        if (data == "1"){
            $('#currentAdminPw').html('<b>현재 비밀번호를 정확히 입력해주세요.</b>');
        }
        else {
            $('#currentAdminPw').html('');
            chk1 = true;
        }
    });
});

// 비밀번호 변경 - 새 비밀번호
$('#pwNew').on('keyup', function(){
    $('#pwNewMsg').css("color", '#e01b1b');
    let pwNew = $('#pwNew').val();


    if (pwNew == '') {
        $('#pwNewMsg').html('<b>비밀번호는 필수 정보입니다.</b>');
    }
    else if(!/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/.test(pwNew)){
        $('#pwNewMsg').html('<b>비밀번호는 8~20자 영문, 숫자, 특수문자를 사용하세요.</b>');
    }
    else {
        $('#pwNewMsg').html('');
        chk2 = true;
    }

});

// 비밀번호 변경 - 새 비밀번호 확인
$('#pwNewCheck').on('keyup', function(){
    $('#pwNewCheckMsg').css("color", '#e01b1b');
    let pwNew = $('#pwNew').val();
    let pwNewCheck = $('#pwNewCheck').val();

    if (pwNewCheck == ''){
        $('#pwNewCheckMsg').html('<b>비밀번호 확인은 필수 정보입니다.</b>');
    }
    else if(pwNew != pwNewCheck){
        $('#pwNewCheckMsg').html('<b>비밀번호가 일치하지 않습니다.</b>');
    }
    else {
        $('#pwNewCheckMsg').html('');
        chk3 = true;
    }

});

// 비밀번호 변경 - 요청
$('#adminupdate-btn').click(function(event){
    if(chk1 == false) {
    	alert('현재 비밀번호가 일치하지 않습니다.');
    }
    else if(chk2 == false || chk3 == false){
    	alert('새 비밀번호가 일치하지 않습니다.');
    }
    else {
        let id = $('#adminid').val();
        let pwNewCheck = $('#pwNewCheck').val();
        $.ajax({
            method: "PUT",
            url: "/triplan/adminUpdate",
            data: JSON.stringify({
                "id": id,
                "pw": pwNewCheck
            }),
            contentType: "application/json"
        })
        .done(function(response){
            if(response == "success") {
                alert('비밀번호가 변경되었습니다.');
                window.location.href = "/triplan/adminLogout";
            }
        })
        .fail(function(e){
            alert('입력정보를 다시 확인하세요.');
        });
    }
});



