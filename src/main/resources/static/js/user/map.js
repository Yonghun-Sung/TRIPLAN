
// 현재 지도 선택
let mapId;
// 지도 배열
let mapArr = [];

let nowMapId;
let map_nowDayPageNum;

let mapInfo = JSON.parse(localStorage.getItem('planInfo'));
let x = mapInfo.loc_x;
let y = mapInfo.loc_y;
let zoom = mapInfo.zoom;

// 일수 넘겨받은거를 변수로
let start_day = mapInfo.start_dt;
let end_day = mapInfo.end_dt;
let arr = start_day.split("-");
start_day = new Date(arr[0], arr[1], arr[2]);
arr = end_day.split("-");
end_day = new Date(arr[0], arr[1], arr[2]);
let dif = end_day.getTime() - start_day.getTime();
let days = Math.abs(dif / (1000 * 60 * 60 * 24)) + 1;


// 기본 변수들-------------------------------
var map, marker;
//경로그림정보
var markerArr = [];		// 검색
var drawInfoArr = [];	// 경로최적화
//------------------------------------------

// 지도 들어갈 공간 만들기
for (let i = 1; i <= days; i++) {
  $('#map_wrap').append(
      "<div id='map_div" + i + "'></div>"
  );
}

function initTmap(){

  // 1. 지도 띄우기
  for (let i = 1; i <= days; i++) {

    mapId = 'map_div' + i;
    markerArr[i] = new Array();

    mapArr[i] = new Tmapv2.Map(mapId, {
      //center: new Tmapv2.LatLng(37.56701114710962, 126.9973611831669),
      center: new Tmapv2.LatLng(x, y),
      width : "100%",
      height : "745px",
      //zoom : 6,
      zoom : zoom,
      zoomControl : true,
      scrollwheel : true
    });
  }
}

// 검색 코드
// POI 통합 검색 API 요청
$("#map-search-btn").click(function(){
  map_nowDayPageNum = $('a.trip-day.active').prop('id').substring(3);

  var searchKeyword = $('#map-search').val();
  $.ajax({
    method:"GET",
    url:"https://apis.openapi.sk.com/tmap/pois?version=1&format=json&callback=result",
    async:false,
    data:{
      "appKey" : "l7xx3baa04961c80465ea1fe8a3be504d3e1",
      "searchKeyword" : searchKeyword,
      "resCoordType" : "EPSG3857",
      "reqCoordType" : "WGS84GEO",
      "count" : 10
    },
    success:function(response){
      var resultpoisData = response.searchPoiInfo.pois.poi;

      // 기존 마커, 팝업 제거
      /*
      if(markerArr.length > 0){
        for(var i in markerArr){
          markerArr[i].setMap(null);
        }
      }
      */
      if(markerArr[map_nowDayPageNum].length > 0){
        for(var i in markerArr[map_nowDayPageNum]){
          markerArr[map_nowDayPageNum][i].setMap(null);
        }
      }
      var innerHtml ="";	// Search Reulsts 결과값 노출 위한 변수
      var positionBounds = new Tmapv2.LatLngBounds();		//맵에 결과물 확인 하기 위한 LatLngBounds객체 생성

      for(var k in resultpoisData){

        var noorLat = Number(resultpoisData[k].noorLat);
        var noorLon = Number(resultpoisData[k].noorLon);
        var name = resultpoisData[k].name;

        var pointCng = new Tmapv2.Point(noorLon, noorLat);
        var projectionCng = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(pointCng);

        var lat = projectionCng._lat;
        var lon = projectionCng._lng;

        var markerPosition = new Tmapv2.LatLng(lat, lon);

        marker = new Tmapv2.Marker({
          position : markerPosition,
          //icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_a.png",
          icon : "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_" + k + ".png",
          iconSize : new Tmapv2.Size(24, 38),
          title : name,
          map:mapArr[map_nowDayPageNum]
        });
        /*
        innerHtml += "<li><img src='http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_" + k + ".png' style='vertical-align:middle;'/><span>"+name+"</span></li>";
         */

        innerHtml += "<li class='search-result'><img src='http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_" + k + ".png' style='vertical-align:middle;'/><span class='name'>"+name+"</span>"
            + "<button class='add-place' name='" + name + "' loc_x='" + lat + "'loc_y='" + lon + "' photo_path='/img/no-img.png'>"
            +   "<i class='bi bi-plus'></i>"
            + "</button></li>";

        //markerArr.push(marker);
        markerArr[map_nowDayPageNum].push(marker);
        positionBounds.extend(markerPosition);	// LatLngBounds의 객체 확장
      }

      $("#searchResult").html(innerHtml);	//searchResult 결과값 노출
      mapArr[map_nowDayPageNum].panToBounds(positionBounds);	// 확장된 bounds의 중심으로 이동시키기
      mapArr[map_nowDayPageNum].zoomOut();

    },
    error:function(request,status,error){
      console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    }
  });
});

$('button#placeOk-btn').click(function () {

  map_nowDayPageNum = $('a.trip-day.active').prop('id').substring(3);

  let loc = [];
  let viaPointIds = [];
  let count = 1;
  for (let i = 1; i <= $('div#form' + map_nowDayPageNum + '>.select-place').length; i++) {
    loc.push([$('div#form' + map_nowDayPageNum + '>.select-place:nth-child(' + i + ') input#loc_x').val()
            , $('div#form' + map_nowDayPageNum + '>.select-place:nth-child(' + i + ') input#loc_y').val()]);
    viaPointIds.push($('div#form' + map_nowDayPageNum + '>.select-place:nth-child(' + i + ')').prop('id'));
  }


  // 2. 시작, 도착 마커 생성
  // 시작
  addMarker("llStart",loc[0][1],loc[0][0], count++);
  // 도착
  addMarker("llEnd",loc[1][1],loc[1][0], count++);

  function addMarker(status, lon, lat, tag) {

    //출도착경유구분
    var markerLayer;
    switch (status) {
      case "llStart":
        imgURL = 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png';
        break;
      case "llPass":
        imgURL = 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_b_m_p.png';
        break;
      case "llEnd":
        imgURL = 'http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png';
        break;
      default:
    };
    var marker = new Tmapv2.Marker({
      position: new Tmapv2.LatLng(lat,lon),
      icon: imgURL,
      map: mapArr[map_nowDayPageNum]
    });
    return marker;
  }

  // 경유지 마커 생성
  for (let i = 2; i < loc.length; i++) {
    addMarker("llPass",loc[i][1],loc[i][0],i + 1);
  }
  /*
  addMarker("llPass",126.98735015742581,37.56626352138058,3);
  addMarker("llPass",127.00221495976581,37.56568310756034,4);
  addMarker("llPass",126.992153,37.570369,5);
  addMarker("llPass",127.00352387777271,37.56335290252303,6);
  addMarker("llPass",127.00186090818215,37.570721714117965,7);
  addMarker("llPass",126.99066536776698,37.56515390827723,8);
  addMarker("llPass",127.006757,37.562856,9);
  */

  let results = [];
  for (let i = 2; i < loc.length; i++) {
    let result = {};

    // 객체 형식으로 만들어서 전송
    // 배열로 만들어서 넘기려고 했는데
    // 아마 {}랑 [] 쌍 제대로 맞았으면 됐을 듯
    //result['viaPointId'] = 'test0' + (i-1);
    result['viaPointId'] = viaPointIds[i];
    result['viaPointName'] = 'test0' + (i-1);
    result['viaX'] = loc[i][1];
    result['viaY'] = loc[i][0];

    results.push(result);
  }


  var headers = {};
  headers["appKey"]="l7xx3baa04961c80465ea1fe8a3be504d3e1";

  $.ajax({
    type:"POST",
    headers : headers,
    url:"https://apis.openapi.sk.com/tmap/routes/routeOptimization10?version=1&format=json",//
    async:false,
    contentType: "application/json",
    data: JSON.stringify({
      "reqCoordType": "WGS84GEO",
      "resCoordType" : "EPSG3857",
      "startName": "출발",
      "startX": loc[0][1],
      "startY": loc[0][0],
      "startTime": "201711121314",
      "endName": "도착",
      "endX": loc[1][1],
      "endY": loc[1][0],
      "searchOption" : "0",
      "viaPoints": results      //-- 위에서 객체로 만든거 여기서 전송해줌
    }),
    success:function(response){
      var resultData = response.properties;
      var resultFeatures = response.features;

      // 결과 출력
      var tDistance = "총 거리 : " + (resultData.totalDistance/1000).toFixed(1) + "km,  ";
      var tTime = "총 시간 : " + (resultData.totalTime/60).toFixed(0) + "분,  ";
      var tFare = "총 요금 : " + resultData.totalFare + "원";

      $("#result").text(tDistance+tTime+tFare);

      // 경유 순서 갖고 오기 위해 테스트 진행중
      var test = '';

      for(var i in resultFeatures) {
        var geometry = resultFeatures[i].geometry;
        var properties = resultFeatures[i].properties;
        var polyline_;

        drawInfoArr = [];

        if(geometry.type == "LineString") {
          for(var j in geometry.coordinates){
            // 경로들의 결과값(구간)들을 포인트 객체로 변환
            var latlng = new Tmapv2.Point(geometry.coordinates[j][0], geometry.coordinates[j][1]);
            // 포인트 객체를 받아 좌표값으로 변환
            var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(latlng);
            // 포인트객체의 정보로 좌표값 변환 객체로 저장
            var convertChange = new Tmapv2.LatLng(convertPoint._lat, convertPoint._lng);

            drawInfoArr.push(convertChange);
          }

          polyline_ = new Tmapv2.Polyline({
            path : drawInfoArr,
            strokeColor : "#ff0000",
            strokeWeight: 6,
            map : mapArr[map_nowDayPageNum]
          });

        }

        // 경유 순서 value 보내줌 --------------------------------------------------
        map_nowDayPageNum = $('a.trip-day.active').prop('id').substring(3);

        if(properties.index == 0) {
          $('#form' + map_nowDayPageNum + ' .select-place:nth-child(1) input#order').val(properties.index);
          continue;
        }
        if(properties.index == ($('#form' + map_nowDayPageNum + ' .select-place').length) - 1) {
          $('#form' + map_nowDayPageNum + ' .select-place:nth-child(2) input#order').val(properties.index);
          continue;
        }
        $('div#' + properties.viaPointId + ' input#order').val(properties.index);
        // -----------------------------------------------------------------------

      }
    },
    error:function(request,status,error){
      console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    }
  });
});