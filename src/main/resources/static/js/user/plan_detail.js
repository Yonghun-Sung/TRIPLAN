let urlParams = new URL(location.href).searchParams;
let focus = urlParams.get('focus');
let errCode = urlParams.get('errCode');
// 댓글 관련 업무 → 댓글 focus
if (focus == "re") {
    $('#replyTab').css('color', '#81bef0').css('font-weight', 'bold');
    $('#planTab').css('color', '#666666').css('font-weight', 'normal');

    $('div.comments').css('display', 'block');
    $('div.content').css('display', 'none');
}
if (errCode == "1") {
    alert('죄송합니다. 일시적인 오류가 발생했습니다. 다시 시도해주세요.');
}

$('#planTab').click(function () {
    $(this).css('color', '#81bef0').css('font-weight', 'bold');
    $('#replyTab').css('color', '#666666').css('font-weight', 'normal');

    $('div.content').css('display', 'block');
    $('div.comments').css('display', 'none');
});
$('#replyTab').click(function () {
    $(this).css('color', '#81bef0').css('font-weight', 'bold');
    $('#planTab').css('color', '#666666').css('font-weight', 'normal');

    $('div.comments').css('display', 'block');
    $('div.content').css('display', 'none');
});

let now_user_id = $('#now_user_id').val();
let plan_writer_id = $('span#nickname').data('id');
if (now_user_id == plan_writer_id) {
    $('#plan-update-btn').css('display', 'inline-block');
    $('#plan-delete-btn').css('display', 'inline-block');
} else {
    $('#plan-update-btn').css('display', 'none');
    $('#plan-delete-btn').css('display', 'none');
}

for (let i = 1; i <= $('#planDetail-reply-box > div.reply-outer').length; i++) {
    let reply_writer_id = $('#planDetail-reply-box > div.reply-outer:nth-child(' + i + ') h5.reply-nickname').data('id');

    if (now_user_id == reply_writer_id) {
        $('#planDetail-reply-box > div.reply-outer:nth-child(' + i + ') .my-reply').css('display', 'inline-block');
        $('#planDetail-reply-box > div.reply-outer:nth-child(' + i + ') .other-reply').css('display', 'none');
    } else {
        $('#planDetail-reply-box > div.reply-outer:nth-child(' + i + ') .other-reply').css('display', 'inline-block');
        $('#planDetail-reply-box > div.reply-outer:nth-child(' + i + ') .my-reply').css('display', 'none');
    }
}

//전체 장소 개수
let totalPlace = $('.place-info').length;
//전체 여행 일수
let totalDays = $('.place-by-day > .place-info:last-child').data('daynum');

let classDay = 1
for (let i = 1; i <= totalPlace; i++) {
    let className = 'day' + classDay;
    //alert($('.place-by-day > .place-info:nth-child(' + i + ')').data('daynum'));
    if (classDay == $('.place-by-day > .place-info:nth-child(' + i + ')').data('daynum')) {
        $('.place-by-day > .place-info:nth-child(' + i + ')').addClass(className);
    } else {
        classDay++;
        i--;
        continue;
    }
}

// Day별 경유지 개수 계산 (n-1 Day까지)
const placeArr = Array.from({length: totalDays+1}, () => 0);
for (let i = 1; i <= totalPlace; i++) {
    placeArr[$('.place-by-day > .place-info:nth-child(' + i + ')').data('daynum')]++;
}

for (let i = 2; i < totalDays; i++) {
    //placeArr[i] += placeArr[i - 1];
    placeArr[i] += placeArr[i - 1] + 1;
}


for (let i = 1; i < totalDays; i++) {
    let startDay = $('.place-by-day > .place-info:nth-child(' + (placeArr[i] + 1) + ')').data('startday');

    $('.place-by-day > .place-info:nth-child(' + placeArr[i] + ')').after(
          "<div class='day-info-box'>"
        +   "<div class='day'>DAY" + (i + 1) + "</div>"
        +   "<span class='date'>" + startDay + "</span>"
        + "</div>"
    );
}

//$('.place-by-day > .place-info:nth-child(1)').before(
startDay = $('.place-by-day > .place-info:first-child').data('startday');
$('.place-by-day').prepend(
    "<div class='day-info-box'>"
    +   "<div class='day'>DAY1</div>"
    +   "<span class='date'>" + startDay + "</span>"
    + "</div>"
);


// 댓글 등록
$('#insert-reply-btn').click(function () {
    let comment = $.trim($('#insert-reply-comment').val());
    if (comment == "") {
        $('#replyErrMsg').css('display', 'block');
        $('#insert-reply-comment').focus();
        return;
    }

    $('#replyErrMsg').css('display', 'none');
    $('#replyInsertForm').submit();
});

// 댓글 수정
$('a.reply-option').click(function () {
    let code = $(this).data('code');

    // 다른 댓글의 수정창은 없애기
    $('div.reply-update-form').css('display', 'none');
    $('div.comment').css('display', 'block');

    $('div#comment' + code).css('display', 'none');
    $('div#update' + code).css('display', 'block');
});
$('.update-reply-btn').click(function () {
    let code = $(this).val();
    let comment = $.trim($('div#update' + code + ' #update-reply-comment').val());
    if (comment == "") {
        $('#replyErrMsg' + code).css('display', 'block');
        $('div#update' + code + ' #update-reply-comment').focus();
        return;
    }
    $('#replyUpdateForm' + code).submit();
});