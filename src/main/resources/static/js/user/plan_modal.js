$(function () {
    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년'
    });
    $(function(){
        $('.datepicker').datepicker();
    });

    $('#start_dt').datepicker({
        onClose: function(selectedDate) {
            $('#end_dt').datepicker("option", "minDate", selectedDate);
        }
    });

    $('#create-plan-modal').modal('show');

    $('#create-plan-modal').on('hidden.bs.modal', function () {
        history.back();
    });

    $('#title').keyup(function () {
        if ($(this).val().length > 20) {
            $(this).val($(this).val().substring(0, 20));
            alert('제목은 20자까지 입력 가능합니다.');
        }
    });

    $('#create-plan-btn').click(function () {
        if ($('#title').val() == '' || $('#start_dt').val() == '' || $('#end_dt').val() == '') {
            alert('제목/기간을 입력해주세요.');
            return;
        }

        let title = $('#title').val();
        let start_dt = $('#start_dt').val();
        let end_dt = $('#end_dt').val();
        let area_code = $('#areacode option:selected').val();
        let loc_x, loc_y, zoom;
        if (area_code == null || area_code == "") {
            loc_x = '37.56701114710962';
            loc_y = '126.9973611831669';
            zoom = 6;
        } else {
            loc_x = $('#areacode option:selected').data('loc_x');
            loc_y = $('#areacode option:selected').data('loc_y');
            zoom = $('#areacode option:selected').data('zoom');
        }

        let planInfo = {
            "title" : title,
            "start_dt" : start_dt,
            "end_dt" : end_dt,
            "area_code" : area_code,
            "loc_x" : loc_x,
            "loc_y" : loc_y,
            "zoom" : zoom
        };
        localStorage.setItem("planInfo", JSON.stringify(planInfo));

        if (area_code == null || area_code == "") {
            window.location.href="/triplan/planinsertform";
        } else {
            window.location.href="/triplan/planinsertform?areaCode=" + area_code;
        }
    });

});
