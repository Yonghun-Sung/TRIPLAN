
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
$('select#areacode').on('change', function () {
    var areacode = $(this).val();
    if ( !(areacode == null || areacode == "") ) {
        /*
        $.ajax({
            method: "GET",
            url: "/triplan/sigunguList"
            data: {}
        })
        */
    }
});

var count = 1;
/*장소 추가 버튼*/
$(document).on('click', 'button.add-place', function() {
    var name = $(this).attr('name')
    var loc_x = $(this).attr('loc_x')
    var loc_y = $(this).attr('loc_y')
    var photo_path = $(this).attr('photo_path')

    $('form.place-form').append(
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
        +   "<input type='hidden' id='day' name='day' value=''>"
        +   "<input type='hidden' id='order' name='order' value=''>"
        + "</div>");
});

/*장소 제거 버튼*/
$(document).on('click', 'button.delete-place', function () {
    $(this).parent().remove();
});
