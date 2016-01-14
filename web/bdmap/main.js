$(function() {
	// 百度地图API功能
	map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(112.881776, 28.202387), 18);
	map.enableScrollWheelZoom(true);
	localSearch = new BMap.LocalSearch(map);

	map.addEventListener("rightclick", function(evt) {
		var point = evt.point;
		$('#currentAddr').html($('#position').val());
		$('#currentClickPosition').html("lng : " + point.lng + ", lat :" + point.lat);
		setTimeout(function() {
			SelectText("currentClickPosition");
			// SelectText("currentAddr");
		}, 500);

		var marker = new BMap.Marker(point); // 创建标注
		map.addOverlay(marker);
		marker.addEventListener("click", function(m_evt) {
			map.removeOverlay(marker);
		});
	});
});
var map = null;
var localSearch = null;

// 用经纬度设置地图中心点
function theLocation() {
	if (document.getElementById("longitude").value != "" && document.getElementById("latitude").value != "") {
		map.clearOverlays();
		var new_point = new BMap.Point(document.getElementById("longitude").value,
				document.getElementById("latitude").value);
		var marker = new BMap.Marker(new_point); // 创建标注
		map.addOverlay(marker); // 将标注添加到地图中
		map.panTo(new_point);
	}
}

function searchByStationName() {
	map.clearOverlays();// 清空原来的标注
	var keyword = document.getElementById("position").value;
	localSearch.setSearchCompleteCallback(function(searchResult) {
		var poi = searchResult.getPoi(0);
		document.getElementById("result").value = poi.point.lng + "," + poi.point.lat;
		map.centerAndZoom(poi.point, 18);

		var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat)); // 创建标注，为要查询的地址对应的经纬度
		map.addOverlay(marker);
		marker.addEventListener("click", function(m_evt) {
			map.removeOverlay(marker);
		});

		return;
		var content = document.getElementById("position").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度："
				+ poi.point.lat;
		var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
		marker.addEventListener("click", function() {
			this.openInfoWindow(infoWindow);
		}); // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	});
	localSearch.search(keyword);
}

function addMarker(point, text) {
	var marker = new BMap.Marker(point); // 创建标注
	map.addOverlay(marker); // 将标注添加到地图中
	var label = new BMap.Label("text", {
		offset : new BMap.Size(20, -10)
	});
	marker.setLabel(label);
}

function SelectText(element) {
	var doc = document, text = doc.getElementById(element), range, selection;
	if (doc.body.createTextRange) {
		range = document.body.createTextRange();
		range.moveToElementText(text);
		range.select();
	} else if (window.getSelection) {
		selection = window.getSelection();
		range = document.createRange();
		range.selectNodeContents(text);
		selection.removeAllRanges();
		selection.addRange(range);
	}
}

var houseData = [ {
	"name" : "好莱城",
	"poi" : {
		lng : 3,
		lat : 4
	}
}, {
	"name" : "梅溪清秀",
	"poi" : {
		lng : 3,
		lat : 4
	}
}, {
	"name" : "梅溪紫郡",
	"poi" : {
		lng : 112.896451,
		lat : 28.210019
	}
}, {
	"name" : "麓谷星辰",
	"poi" : {
		lng : 3,
		lat : 4
	}
}, {
	"name" : "湘熙水郡",
	"poi" : {
		lng : 3,
		lat : 4
	}
} ]