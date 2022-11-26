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



