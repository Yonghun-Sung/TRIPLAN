
let urlParams = new URL(location.href).searchParams;
let join = urlParams.get('join');
let errCode = urlParams.get('errCode');
if (join == "1") {
    alert('회원가입이 완료되었습니다.');
}
if (errCode == "1") {
    alert('이메일 또는 비밀번호를 확인해주세요.');
} else if (errCode == "2") {
    alert('로그인 후 이용하실 수 있습니다.');
}


$('#findpw-btn').click(function () {
    let email = $('#email').val();
    $.ajax({
        type: 'POST',
        url: '/triplan/checkId',
        data: {'email':email},
    })
    .done(function (response) {
        if (response == 0) {
            alert('가입되지 않은 이메일입니다.');
        } else {
            $('#findpw-form').submit();
            alert('이메일이 전송되었습니다.');
            $('#findpw-modal').modal('hide');
        }
    })
    .fail(function (e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});





