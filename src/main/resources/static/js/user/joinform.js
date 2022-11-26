
let idcheck = 0;

// id, name, nickname 공백 입력 제거
$('#id').focusout(function () {
   $('#id').val($('#id').val().replaceAll(" ", ""));
});
$('#name').focusout(function () {
    $('#name').val($('#name').val().replaceAll(" ", ""));
});
$('#nickname').focusout(function () {
    $('#nickname').val($('#nickname').val().replaceAll(" ", ""));
});

// 아이디 중복확인
$('#id-overlap-btn').click(function () {
    idcheck = 0;
    let id = $('#id').val().replaceAll(" ", "");
    $('#id').val(id);

    $('#idErrMsg').css('display', 'none');
    $('#idErrMsg').css('color', '#e01b1b');

    // 이메일 형식 체크
    if (!isEmailFormat(id)) {
        $('#idErrMsg').html('이메일 형식이 아닙니다.');
        $('#idErrMsg').css('display', 'block');
        $('#id').focus();
    } else {
        $.ajax({
            method: "POST",
            url: "/triplan/checkJoinId",
            data: {"id":id}
        })
        .done(function (response) {
            // 중복확인 (response[0])
            if (response[0] != 0)
                $('#idErrMsg').html('이미 사용중인 아이디입니다.');
            // 탈퇴회원 확인 (response[1])
            else if (response[1] != 0)
                $('#idErrMsg').html('탈퇴한 아이디입니다.');
            else {
                $("#idErrMsg").html("사용가능한 아이디입니다.");
                $("#idErrMsg").css("color", "#0064d6");
                idcheck++;
            }
            $("#idErrMsg").css("display", "block");
            $("#id").focus();
        })
        .fail(function (e) {
            console.log(e.status);
            console.log(e.responseText);
        });
    }
});

// ID 값 변경 확인
$('#id').change(function () {
    idcheck = 0;
});

// <회원가입>
$('#joininsert-btn').click(function () {
    $("#idErrMsg").css("display", "none");
    $("#pwErrMsg").css("display", "none");
    $("#pwCheckErrMsg").css("display", "none");
    $("#nameErrMsg").css("display", "none");
    $("#joinErrMsg").css("display", "none");

    // 항목입력 체크
    // 아이디 중복검사 체크
    if (idcheck == 0) {
        $("#idErrMsg").html("중복검사를 진행해주세요.");
        $("#idErrMsg").css("color", "#e01b1b");
        $("#idErrMsg").css("display", "block");
        $("#id").focus();
        return false;
    }

    // 비밀번호 형식 체크
    let pw = $('#pw').val();
    if (!(pw.length >=8 && pw.length <= 20 && isPwFormat(pw))) {
        $("#pwErrMsg").css("display", "block");
        $("#pw").focus();
        return false;
    }

    // 비밀번호 == 비밀번호확인 체크
    if ($("#pwCheck").val() != $("#pw").val()) {
        $("#pwCheckErrMsg").css("display", "block");
        $("#pwCheck").focus();
        return false;
    }

    // 이름 형식 체크 (특수문자/공백/완성형 한글)
    var replaceChar = /[~!@\#$%^&*\()\-=+_'\;<>0-9\/.\`:\"\\,\[\]?|{}]/gi;
    var replaceNotFullKorean = /[ㄱ-ㅎㅏ-ㅣ]/gi;
    let name = $("#name").val();
    if (name.length > 0) {
        if (name.match(replaceChar) || name.match(replaceNotFullKorean) || name.length < 2) {
            $("#nameErrMsg").css("display", "block");
            return false;
        }
    }

    // 항목 입력 체크
   if ($("#id").val().replaceAll(' ', '') == ""
        || $("#name").val().replaceAll(' ', '') == ""
        || $("#nickname").val().replaceAll(' ', '') == ""
        || $("#pw").val().replaceAll(' ', '') == ""
        || $("#pwCheck").val().replaceAll(' ', '') == ""
        || !($("input:checkbox[id='agree-terms']").is(':checked')) ) {
        $("#joinErrMsg").css("display", "block");
        return false;
   }

   $("#joinform").submit();
});


// 이메일 형식인지 체크
function isEmailFormat(id)
{
    let format = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if (format.test(id))
        return true;
    return false;
}

// 비밀번호 체크 (8~20자, 영어/숫자/특수문자 하나 이상 포함)
function isPwFormat(pw)
{
    //let format = new RegExp("^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$");
    let format = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;
    if (format.test(pw))
        return true;
    return false;
}