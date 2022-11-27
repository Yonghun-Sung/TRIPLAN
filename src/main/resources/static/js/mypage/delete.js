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
            window.location.href = "/triplan/mypage/completedplans";
        })
        .fail(function (e) {
            console.log("err");
            console.log(e.status);
            console.log(e.responseText);
        });
});

$('.threeplan-btn.deleteSavedBtn').click(function() {
    let code = $(this).data('code');

    $.ajax({
        method: "POST",
        url: "/triplan/mypage/delete",
        data: {
            "code": code
        }
    }).done(function(response){
        console.log("delete!")
        alert("여행 일정이 삭제되었습니다.");
        window.location.href = "/triplan/mypage/completedplans";
    }).fail(function(e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});

$('.btn.btn-primary.like-delete-btn').click(function() {
    let checkBoxArr = [];
      $("input:checkbox[name='cb1']:checked").each(function() {
      checkBoxArr.push($(this).data('code'));     // 선택된 체크박스의 plan.code 배열에 push
      console.log(checkBoxArr);
    })
    $.ajax({
        method: "POST",
        url: "/triplan/mypage/like/delete",
        data: {
            "plan_code" : code
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