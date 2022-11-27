// --미신고 처리
$('#report-modal').on('show.bs.modal', function(event) {
    var button = $(event.relatedTarget);                // button에 바인딩된 함수 가져오기
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
    $('#report-reg-code').val(code);

    let result_code;
    $('.report-result-btn').click(function () {
        result_code = $(this).val();
        $('#report-result-code').val(result_code);      // 신고처리코드 가져오기(버튼 value)

        $('#report-proc-form').submit();
    });


});





