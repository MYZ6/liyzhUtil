<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String appRoot = request.getContextPath();
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#allmap{height:500px;width:100%;}
		#r-result{width:100%; font-size:14px;}
	</style>
	
	<script type="text/javascript" src="<%=appRoot %>/common/lib/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=s61E0es0Tp5DKF8AywSUavCz"></script>
	<script type="text/javascript" src="main.js"></script>
	<title>房源定位图</title>
</head>
<body>
	<div id="r-result">
		经度: <input id="longitude" type="text" style="width:100px; margin-right:10px;" />
		纬度: <input id="latitude" type="text" style="width:100px; margin-right:10px;" />
		<input type="button" value="查询" onclick="theLocation()" />
	</div>
	
    <div style="width:730px;margin:auto;">   
        要查询的地址：<input id="position" type="text" value="宁波天一广场" onkeydown="if(event.keyCode==13)searchByStationName();" style="margin-right:150px;"/>
        查询结果(经纬度)：<input id="result" type="text" />
        <input type="button" value="查询" onclick="searchByStationName();"/>
        当前地址名称：<span id="currentAddr"></span>
        当前点击经纬度：<span id="currentClickPosition"></span>
    </div>
    
	<div id="allmap"></div>
</body>
</html>
