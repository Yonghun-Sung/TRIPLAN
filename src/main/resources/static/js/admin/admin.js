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

//    let result_code;
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

    var reg_code = button.data('code');                     // 접수코드 가져오기

    $('.report-withdraw-btn').click(function(){
        var result_code = $(this).val();

        $.ajax({
            method: "put",
            url: "/triplan/processedReport",
            data: JSON.stringify({
                "reg_code": reg_code,
                "result_code": result_code
            }),
            contentType: "application/json"
        })
        .done(function(response){
            console.log("put success!");
            window.location.href = "/triplan/reportProc";
        });
    });
});






