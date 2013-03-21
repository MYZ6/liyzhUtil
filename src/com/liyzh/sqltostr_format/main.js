function transfer() {
	var source = $('#formatsqls').val();
	$('#formatsqld').val(source);
	var re = /(.+)((\n)|($))/g;//
	var result = source.replace(re, function(a, b, c, d, e) {
		// alert(b);
		console.log(a, b, c, d);
		var str = '" ' + b;
		if(str.length<170){
			str += getSpace(170 - str.length);
		}
		return str + ' "' + c + ' + ';
	});
	var r2 = result.substring(0,result.lastIndexOf('+ '));
	$('#formatsqld').val(r2);
}

function getSpace(count){
	var a = ' ';
	for(var i=0;i<count;i++){
		a += ' ';
	}
	return a;
}
/**
 * 下面分享一个函数 使用jQuery 把一个JSP文件中的所有id名字替换成以b开头，再加上原来的名字（首字母变为大写） 为了让ajax在获取文件时不会被服务器解析，暂时把jsp文件重命名为txt文件
 * 
 * 
 * 把编辑界面转换成浏览界面的批处理 除了这些，还有下面的地方需要手动调整 1.浏览时经纬度的显示，与编辑时不同，直接拼装以后显示到界面 2.档案年份browseYearCombo 3.地貌特征 4.海拔范围(米)：name="avgHeight"
 */
function replaceFileByRegex() {
	jQuery.ajax({
		type : "POST",
		url : appRoot + "/BAS/basicUnitInfo/jsp/basicUnitInfoInput.txt",
		// async : false,
		error : function() {
			alert("获取基地单元信息失败！");
		},
		success : function(data) {
			// console.log(data);
			var re = /(id=")(\w)(\w*")/g;//
			var result = data.replace(re, function(a, b, c, d) {
				// alert(b);
				return b + 'b' + c.toUpperCase() + d;
			});
			// 去掉不要的span
			// var re =
			// /(<span\r\n\s*class.*)(<input.*\r\n\s*.*\r\n\s*.*\r\n\s*)(<\/span>)/g;//
			// [\s\r\n]*<input[\r\n.]*\/>[\s\r\n]*(<\/\1>)+)/i;
			// var result = data.replace(re, function(a, b, c, d) {
			// // alert(b);
			// return c;
			// });
			// 去掉隐藏字段
			re = /<input.*hidden="true" style="display: none;">/g;

			var result2 = result.replace(re, '');
			// 将input和textarea替换成label
			re = /<input|<textarea/g;
			var result3 = result2.replace(re, '<label');
			// var result = re.exec(data);
			// console.log(result);
			// for ( var i = 0; i < 88; i++) {
			// var result = re.exec(data);
			// console.log(result);
			// }
			// do {
			// var result = re.exec(data);
			// console.log(result);
			// } while (result != null)

			// 可使用火狐的firebug直接显示所有内容
			console.log(result3);
			// 将内容复制到剪贴板
			window.prompt("Copy to clipboard: Ctrl+C, Enter", result3);
		}
	});
}