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


// selectbox
let days = $('.place-by-day > .place-info:last-child').data('daynum');
let placeNum = $('span#placeNum').text();
let loc_xs = [];
let loc_ys = [];
let names = [];
loc_xs[1] = new Array();
loc_ys[1] = new Array();
names[1] = new Array();
for (let i = 2; i <= days; i++) {
    $('#day-select-box').append(
        "<option value='" + i + "'>DAY" + i + "</option>"
    );
    loc_xs[i] = new Array();
    loc_ys[i] = new Array();
    names[i] = new Array();
}

days = parseInt($('span#placeNum').text());
let allLength = days + parseInt($('.place-by-day > .place-info:last-child').data('daynum'));
let day = 1;
let count = 0;

for (let i = 1; i <= allLength; i++) {
    let value = $('.place-by-day > .place-info:nth-child(' + i + ')').data('daynum');
    let loc_x = $('.place-by-day > .place-info:nth-child(' + i + ')').data('loc_x');
    let loc_y = $('.place-by-day > .place-info:nth-child(' + i + ')').data('loc_y');
    let name = $('.place-by-day > .place-info:nth-child(' + i + ') h4').text();
    if (value == null) {
        continue;
    } else if (value == day) {
        names[day][count] = name;
        loc_xs[day][count] = loc_x;
        loc_ys[day][count++] = loc_y;
    } else {
        day++;
        count = 0;
        names[day][count] = name;
        loc_xs[day][count] = loc_x;
        loc_ys[day][count++] = loc_y;
    }
}


// 지도 들어갈 공간 만들기
totalDays = $('.place-by-day > .place-info:last-child').data('daynum');
for (let i = 2; i <= totalDays; i++) {
    $('#map_wrap').append(
        "<div id='map_div" + i + "' class='map'></div>"
    );
}

for (let i = 1; i <= totalDays; i++) {
    if (i == 1)
        $('div#map_div' + i).css('display', 'block');
    else
        $('div#map_div' + i).css('display', 'none');
}
$('#day-select-box').change(function () {
    let value = $(this).val();
    for (let i = 1; i <= totalDays; i++) {
        if (i == value)
            $('div#map_div' + i).css('display', 'block');
        else
            $('div#map_div' + i).css('display', 'none');
    }
});

// 지도
var map;
var marker_s, marker_e;
var marker;
var label;
var positions = [];

//경로그림정보
var drawInfoArr = [];

let mapArr = [];
let mapId;

function initTmap(){
    let nowNum = $('#day-select-box').val();

    // 지도 띄우기
    for (let i = 1; i <= totalDays; i++) {

        let mid_x = 0, mid_y = 0;
        let _path = [];
        positions[i] = new Array();

        for (let j = 0; j < loc_xs[i].length; j++) {
            _path[j] = new Tmapv2.LatLng(loc_xs[i][j], loc_ys[i][j]);

            // 중심 좌표
            mid_x += Number(loc_xs[i][j]);
            mid_y += Number(loc_ys[i][j]);

            // 다중 마커
            positions[i][j] = { title: names[i][j],
                             lonlat: new Tmapv2.LatLng(loc_xs[i][j], loc_ys[i][j]) };
        }
        mid_x /= loc_xs[i].length;
        mid_y /= loc_ys[i].length;

        mapId = 'map_div' + i;

        mapArr[i] = new Tmapv2.Map(mapId, {
            center: new Tmapv2.LatLng(mid_x, mid_y),
            width : "107%",
            height : "300px",
            zoom : 11,
            zoomControl : false,
            scrollwheel : true
        });

        //선
        var polyline = new Tmapv2.Polyline({
            path: _path,
            strokeColor: "#dd00dd",	// 라인 색상
            strokeWeight: 6,	    // 라인 두께
            map: mapArr[i]          // 지도 객체
        });

        //다중 마커
        for (var k = 0; k < positions[i].length; k++){//for문을 통하여 배열 안에 있는 값을 마커 생성
            var lonlat = positions[i][k].lonlat;
            var title = positions[i][k].title;
            label="<span style='background-color: #46414E;color:white'>"+title+"</span>";
            //Marker 객체 생성.
            if (k == 0) {
                marker = new Tmapv2.Marker({
                    position : lonlat,  //Marker의 중심좌표 설정.
                    map : mapArr[i],          //Marker가 표시될 Map 설정.
                    icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png",
                    title : title,      //Marker 타이틀.
                    label : label       //Marker의 라벨.
                });
            } else if (k == positions[i].length - 1) {
                marker = new Tmapv2.Marker({
                    position : lonlat,  //Marker의 중심좌표 설정.
                    map : mapArr[i],          //Marker가 표시될 Map 설정.
                    icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png",
                    title : title,      //Marker 타이틀.
                    label : label       //Marker의 라벨.
                });
            } else {
                marker = new Tmapv2.Marker({
                    position : lonlat,  //Marker의 중심좌표 설정.
                    map : mapArr[i],          //Marker가 표시될 Map 설정.
                    icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_" + (k+1) + ".png",
                    title : title,      //Marker 타이틀.
                    label : label       //Marker의 라벨.
                });
            }
        }
    }
}
