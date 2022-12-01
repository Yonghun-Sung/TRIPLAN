
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

for (let i = 1; i <= $('#planDetail-reply-box > div.comment').length; i++) {
    let reply_writer_id = $('#planDetail-reply-box > div.comment:nth-child(' + i + ') h5.reply-nickname').data('id');
    if (now_user_id == reply_writer_id) {
        $('#planDetail-reply-box > div.comment:nth-child(' + i + ') .my-reply').css('display', 'inline-block');
        $('#planDetail-reply-box > div.comment:nth-child(' + i + ') .other-reply').css('display', 'none');
    } else {
        $('#planDetail-reply-box > div.comment:nth-child(' + i + ') .other-reply').css('display', 'inline-block');
        $('#planDetail-reply-box > div.comment:nth-child(' + i + ') .my-reply').css('display', 'none');
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

