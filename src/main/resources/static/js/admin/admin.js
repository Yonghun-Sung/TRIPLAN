$(function(){
    $('#report-modal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget);
        var reportUser = button.data('reportUser');
        var reportedUser = button.data('reportedUser');
        var content = button.data('content');
        var state = button.data('state');
        var dt = button.data('dt');

        $(this).find('#modal_reported').text(reportedUser);
        $(this).find('#modal_report').text(reportUser);
        $(this).find('#modal_dt').text(dt);
        $(this).find('#modal_state').text(state);
        $(this).find('#modal_content').text(content);
    });
});


