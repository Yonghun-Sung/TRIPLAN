/*function checkAll() {
var checks = document.getElementById("all")

if(document.getElementById("all").checked==true) {
for(var i=0;i<)
}
}*/

/*function checkAll() {
    if($("#all").is('checked')) {
        $("input[name=cb1]").prop("checked", true);
    } else {
        $("input[name=cb1]").prop("checked", false);
    }
}*/
/*

$(document).ready(function() {
$('.')

})*/
//삭제 체크박스 전체선택 및 전체 해제
$('#all').click(function () {
    if($("input:checkbox[id='all']").prop("checked")) {
        $("input[name=cb1]").prop('checked', true);
    } else {
        $("input[name=cb1]").prop('checked', false);
    }
});




//전체

/*function deleteReport(){
        var cnt = $("input[name='reportChkBxRow']:checked").length;
        var arr = new Array();
        $("input[name='reportChkBxRow']:checked").each(function() {
            arr.push($(this).attr('id'));
        });
        if(cnt == 0){
            alert("선택된 글이 없습니다.");
        }
        else{
            $.ajax = {
                type: "POST"
                url: "OOOO.do"
                data: "RPRT_ODR=" + arr + "&CNT=" + cnt,
                dataType:"json",
                success: function(jdata){
                    if(jdata != 1) {
                        alert("삭제 오류");
                    }
                    else{
                        alert("삭제 성공");
                    }
                },
                error: function(){alert("서버통신 오류");}
            };
        }
    }


$()*/


// 나의 일정에서 삭제버튼 눌렀을때(아직 완료된 일정만 반영해놓음)
/*
$("#deleteSavedBtn_0").click(function() {
    var id = $("#deleteSavedBtn_0").val();
    $.ajax({
        method: "DELETE",
        url: "/mypage/plan/delete"+id,
        dataType: 'json',
        ContentType: "application/json"
    }).done(function(){
        alert('일정이 삭제되었습니다.');
        window.location.href = "/mypage";
    });
});
*/
//
/*//여행제목 수정 js
$("#inputTravelName_idx_a").click(function(){
    var code = $("#title-edit-code").val();//hidden으로 숨겨져있던 일정 code
    var title = $("inputTravelName_idx_0").val();//일정 title폼에 입력된 제목

    $.ajax({
        method: "PUT",
        url: "mypage",
        data: JSON.stringify({
            "code": code,
            "title": title
        }),
        contentType: "application/json"
    }).done(function(){
        console.log("success");
        alert("여행 제목이 변경되었습니다.");
        window.location.href = "/mypage";
    });
});*/
/*
function titleEdit(){
    var code = $("#title-edit-code").val();//hidden으로 숨겨져있던 일정 code
    var title = $("#inputTravelName_idx_0").val();//일정 title폼에 입력된 제목


    title = $('#inputTravelName_idx_a').val();
    alert(title);

    $.ajax({
        method: "PUT",
        url: "/triplan/mypage",
        data: {
            "code": code,
            "title": title
        },
        contentType: "application/json"
    }).done(function(response){
        console.log("success");
        alert("여행 제목이 변경되었습니다.");
        window.location.href = "/";
    });
};
*/

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

//회원정보 수정(닉네임, 비밀번호)
$('#userupdate-btn').click(function() {
    let nickname = $('#nickname').val();
    alert(nickname);
    let pw = $('#pwNew').val();
    alert(pw);
    $.ajax({
        method: "PUT",
        url: "/triplan/myprofile/update",
        data: {
            "nickname": nickname,
            "pw": pw
        }
    }).done(function(response){
        console.log(response);
        console.log("success");
        alert("회원정보가 변경되었습니다.");
        window.location.href = "/triplan/myprofile";
    })
    .fail(function (e) {
        console.log("err");
        console.log(e.status);
        console.log(e.responseText);
    });
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

/*    $.ajax({
        method: "POST",
        url: "/triplan/myprofile/drop",
        data: {
            *//*"id" : id,*//*
            "name" : name,
            *//*"nickname": nickname,
            "user_code": user_code*//*
        }
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
    });*/
});


// 동행자 추가 모달
$('.btn.btn-primary.mate-search').click(function() {
    let mateEmail = $('.form-control.mate-email').val(); //입력된 이메일
    $.ajax({
            method: "GET",
            url: "/triplan/addMateForm",
            data: {
                "mateEmail": mateEmail
            }
        }).done(function(response){
            console.log(response);
            alert("동행자 이메일을 검색합니다");
            if(response != null){
                $('div#user_id').text(가져온user_id넣어줌));
                $('div#user_nickname').text(user_nickname넣어줌);
            } else {
                $('div#user_detail').text('검색결과가 없습니다.');
            }
/*window or 여기에 이제 리스트 추가이이런거..안될까/.*/

        }).fail(function (e) {
            console.log("err");
            console.log(e.status);
            console.log(e.responseText);
        });

});

/*$('.threeplan-btn').click(function() {
    $('#matemodal').modal('show');
});*/
