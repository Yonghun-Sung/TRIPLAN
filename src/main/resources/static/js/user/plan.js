$('#plan-exitbtn').click(function () {
    window.location.href='/triplan/main';
});
// 새로고침,나가기 confirm()
function refresh() {
    window.onbeforeunload = function (e) {
        return 0;
    };
}

// localStorage에 저장해놓음
let planInfo = JSON.parse(localStorage.getItem('planInfo'));
/*
    alert("제목: " + planInfo.title
            + "\n시작날짜: " + planInfo.start_dt
            + "\n마지막날짜:" + planInfo.end_dt
            + "\nx: " + planInfo.loc_x
            + "\ny: " + planInfo.loc_y
            + "\nzoom: " + planInfo.zoom);
 */
$('input#title').val(planInfo.title);
$('input#start_dt').val(planInfo.start_dt);
$('input#end_dt').val(planInfo.end_dt);
$('input#area_code').val(planInfo.area_code);

// 마지막일-시작일 일수 계산
let start_dt = planInfo.start_dt;
let end_dt = planInfo.end_dt;
let dateArr = start_dt.split("-");
start_dt = new Date(dateArr[0], dateArr[1], dateArr[2]);
dateArr = end_dt.split("-");
end_dt = new Date(dateArr[0], dateArr[1], dateArr[2]);
let diff = end_dt.getTime() - start_dt.getTime();
let tripDays = Math.abs(diff / (1000 * 60 * 60 * 24)) + 1;


// 며칠 여행 인지 넘어왔따고 치면 ex 2022/11/3부터 3일
// 모달 닫으면 넘겨받을 값들 (title, days, startday, endday)
function addDays(date, days) {
    const clone = new Date(date);
    clone.setDate(date.getDate() + days)
    return clone;
}
let daysResult = "";
let placeResult = "";
let dayArr = ['일', '월', '화', '수', '목', '금', '토'];
let date = start_dt;      // month는 -1
$('div#plan-day').text("DAY1 | " + (date.getMonth()+1) + "/" + date.getDate() + " (" + dayArr[date.getDay()] + ")");
for (let i = 1; i <= tripDays; i++) {
    // DAY
    daysResult += "<a class='trip-day' id='day" + i + "' href='#!'>" +
                    "<div class='day-info'>" +
                        "<span class='day-num'>DAY" + i + "</span>" +
                        "<div class='info-box'>" +
                            "<span class='date'>" + (date.getMonth()+1) + "/" + date.getDate() + "</span>" +
                            "<span class='day'>(" + dayArr[date.getDay()] + ")</span>" +
                        "</div>" +
                    "</div>" +
                  "</a>";

    // 일정 장소
    placeResult += "<div id='form" + i + "' class='selected-place'></div>";

    date = addDays(date, 1);
}
$('div#plan-daybar').html(daysResult);
$('form.place-form').append(placeResult);
$('div#form1').css('display', 'block');


// 현재 클릭되어 있는 DAY 파악
let nowDayPageNum = 1;  //현재 클릭되어 있는 DAY
// 처음엔 DAY1 a태그에 active 속성
$('a#day1').addClass('active');


$('a.trip-day').click(function () {
    let dayNum = $(this).prop('id').substring(3);

    // DAY별 일정 장소목록 & 지도
    for (let i = 1; i <= tripDays; i++) {
        if (i == dayNum) {
            $('div#form' + i).css('display', 'block');
            $('a#day' + i).addClass('active');

            $('div#map_div' + i).css('display', 'block');
        }
        else {
            $('div#form' + i).css('display', 'none');
            $('a#day' + i).removeClass('active');

            $('div#map_div' + i).css('display', 'none');
        }
    }


    nowDayPageNum = $('a.trip-day.active').prop('id').substring(3);

    //DAY1 | 11/3 (목)
    let day_order = $(this).find('span.day-num').text();
    let trip_date = $(this).find('span.date').text();
    let trip_day = $(this).find('span.day').text();
    let dayInfo = day_order + ' | ' + trip_date + ' ' + trip_day;
    $('div#plan-day').text(dayInfo);
});



$('a#search').click(function () {
    $('a#search').addClass('active');
    $('a#search').attr('aria-current', 'page');
    $('a#recommend').removeClass('active');
    $('a#recommend').removeAttr('aria-current');

    $('div.recommend-list').css('display', 'none');
    $('div.placelist').css('display', 'block');
});

$('a#recommend').click(function () {
    $('a#recommend').addClass('active');
    $('a#recommend').attr('aria-current', 'page');
    $('a#search').removeClass('active');
    $('a#search').removeAttr('aria-current');

    $('div.recommend-list').css('display', 'block');
    $('div.placelist').css('display', 'none');
});

/*지역에 대한 시군구 가져오기*/
$('select#areacode').change(function () {
    var areacode = $(this).val();
    if ( !(areacode == null || areacode == "") ) {
        $.ajax({
            method: "GET",
            url: "/triplan/getSigunguList",
            data: {"areaCode":areacode}
        })
        .done(function (response) {
            let result = "<option value=''>전체</option>";
            for (let item of response) {
                result += "<option value='" + item.code + "'>" + item.name + "</option>";
            }
            $('select#sigungucode').html(result);
        })
        .fail(function (e) {
            console.log(e.status);
            console.log(e.responseText);
        });
    } else {
        $('select#sigungucode').html("<option selected value=''>-- 시군구 --</option>");
    }
});

// 관광지 이름으로 검색
//$('#spot-search-btn').click(function () {
$('.spot-list-btn ').click(function () {
    let keyword = $('#spot-search').val();
    let areaCode = $('#areacode option:selected').val();
    let sigunguCode = $('#sigungucode option:selected').val();

    $.ajax({
        method: "GET",
        url: "/triplan/searchSpotList",
        data: {"keyword":keyword, "areaCode":areaCode, "sigunguCode":sigunguCode}
    })
    .done(function (response) {
        let result = "";
        for (let item of response) {
            result += "<div class='rec-list-box'>" +
                            "<img class='rec-img' src='" + item.imgPath + "'>" +
                            "<div class='rec-content'>" +
                                "<div class='rec-btn-box'>" +
                                    "<button type='button' class='add-place rec-add-btn' name='" + item.name + "' loc_x='" + item.loc_x + "' loc_y='" + item.loc_y + "' photo_path='" + item.imgPath + "'><i class='bi bi-plus'></i></button>" +
                                "</div>" +
                                "<span class='name'>" + item.name + "</span>" +
                            "</div>" +
                        "</div>"
        }
        $('#tour-list-box').html(result);
    })
    .fail(function (e) {
        console.log(e.status);
        console.log(e.responseText);
    });
});


var count = 1;
/*장소 추가 버튼*/
$(document).on('click', 'button.add-place', function() {
    var name = $(this).attr('name')
    var loc_x = $(this).attr('loc_x')
    var loc_y = $(this).attr('loc_y')
    var photo_path = $(this).attr('photo_path')

    $('div#form' + nowDayPageNum).append(
        "<div id='place" + count++ + "' class='select-place'>"
        +   "<span class='num-box'><i class='bi bi-check'></i></span>"
        +   "<input type='text' id='name' name='name' class='select-name' value='" + name + "' disabled>"
        +   "<button class='delete-place'><i class='bi bi-dash'></i></button>"
        +   "<div class='memo-box'>"
        +     "<span class='memo-tag'>메모</span>"
        +     "<textarea name='memo' class='select-memo'></textarea>"
        +   "</div>"
        +   "<input type='hidden' id='loc_x' name='loc_x' value='" + loc_x + "'>"
        +   "<input type='hidden' id='loc_y' name='loc_y' value='" + loc_y + "'>"
        +   "<input type='hidden' id='photo_path' name='photo_path' value='" + photo_path + "'>"
        +   "<input type='hidden' id='day' name='day' value='" + nowDayPageNum + "'>"
        +   "<input type='hidden' id='order' name='order' value=''>"
        + "</div>");
});

/*장소 제거 버튼*/
$(document).on('click', 'button.delete-place', function () {
    $(this).parent().remove();
});


// 폼 제출
$('#plan-savebtn').click(function () {

});