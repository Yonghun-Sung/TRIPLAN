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
//체크박스 하나 선택될 때마다



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

//여행제목 수정 js
$("#title-edit-icon").click(function(){
    var code = $("#title-edit-code").val();//hidden으로 숨겨져있던 일정 code
    var title = $("inputTravelName_idx_0").val()//일정 title폼에 입력된 제목

    $.ajax({
        method: "PUT",
        url: "mypage",
        data: JSON.stringify({
            "code": code,
            "title": title
        }),
        contentType: "application/json"
    }).done(function(){
        alert("여행제목이 수정되었습니다.");
        window.location.href = "/mypage";
    })
})
