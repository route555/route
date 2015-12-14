/**
 * 공통 common
 * 
 */
//array 중복 제거
Array.prototype.uniqueReplace = function()
{
	var a = {};
	for(var i=0; i <this.length; i++){
	if(typeof a[this[i]] == "undefined")
		a[this[i]] = 1;
	}
	this.length = 0;
	for(var i in a) {
		this[this.length] = i;
	}
	return this;
}

var common = {
	pageSize : 10
	, isSession : false
	, serverCallCnt : 0
	, ajax : function() {
		var _opts = null;
		var type = "";
		var dataType = "json";
		var async = false;

		_opts = arguments[0];

		if (_opts.url == undefined) {
			alert(common.getMsg(message.not_found.ajax_param));
			return;
		}

		_opts.url = _opts.url;
		_opts.type = (_opts.type == undefined) ? type : _opts.type;
		_opts.dataType = (_opts.dataType == undefined) ? dataType
				: _opts.dataType;
		_opts.async = (_opts.async == undefined) ? async : _opts.async;

		var successCallback = _opts.success;
		var errorCallback = function(request, status, error) {
			alert(common.getMsg(message.system.error, status, error));
		};
		_opts.success = function(json, textStatus) {
			common.ajaxCallback(json, successCallback, errorCallback);
		};
		$.ajax(_opts);
	}
	, ajaxCallback : function(json, successCallback, errorCallback) {

		if (json.exceptionKey != null
				&& (json.exceptionKey == "-1" || json.exceptionKey == "-99")) {
			common.serverCallCnt++;
			if (common.serverCallCnt > 1) {
				return;
			}

			alert(json.exceptionValue);
			if (json.exceptionKey == "-99") {
				document.location.href = "/index.html";
			}
			return;
		} else {
			if (successCallback != null) {
				successCallback.call(null, json);
			}
		}
	}
	, logout : function() {
		common.ajax({
			url : "/account/logout.do",
			data : {},
			success : function(json) {
				location.href = "/index.html";
			}
		});
	}
	, isSession : function() {
		common.ajax({
			url : "/basic/isSession.do",
			data : {},
			success : function(json) {

				common.isSession = json.resultData.isSession;
			}
		});
	}
	, toStr : function(str) {
		if (str == undefined || str == 'undefined') {
			return "-";
		}
		return str;
	}
	, toStrSpc : function(str) {
		if (str == undefined || str == 'undefined') {
			return "";
		}
		return str;
	}
	, hypenToStr : function(str) {
		if (str == undefined || str == "") {
			return "-";
		}
		return common.toEnter(str);
	}
	, toNumber : function(str) {
		if (str == undefined) {
			return new Number("");
		}

		return new Number(str);
	}
	, toDotNumber : function(n) {
		if (n == undefined) {
			return "";
		}

		var reg = /(^[+-]?\d+)(\d{3})/; // 정규식
		n += ''; // 숫자를 문자열로 변환
		while (reg.test(n))
			n = n.replace(reg, '$1' + ',' + '$2');
		return n;
	}
	, toEasyView : function(n) {
		if ( $.isNumeric(n) ) {
			n = common.toDotNumber(n);
		} else {
			n = common.removeComma(n);
			n = common.toDotNumber(n);
		}
		return n;
	}
	, removeComma : function(n) {
		n = parseInt( n.replace(/,/g , "") );
		return n;
	}
	, isJson : function(str) {
		try {
			jQuery.parseJSON(str);
			return true;
		}
		catch(e) {
			//console.error(e);
			return false;
		}
	}
	, toEasyTime : function(n) {
		if (n == undefined) {
			return "";
		}

		var reg = /(^\d+)(\d{2})/; // 정규식
		n += '';
		while (reg.test(n))
			n = n.replace(reg, '$1' + ':' + '$2');
		return n;
	}
	, isEmpty : function(str) {
		if (str == null) {
			return true;
		}

		return !(str.replace(/(^\s*)|(\s*$)/g, ""));
	}
	
	, isNumber : function(str) {
		if (str == null) {
			return false;
		}

		return !(str.match(/[^0-9]/ig));
	}
	, isDotNumber : function(str) {
		var number = str.match(/^\d*(\.?\d*)$/ig);

		if (number != null) {
			return false;
		} else {
			return true;
		}
	}
	, isEmail : function(str) {
		var pattern = /^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/;
		return pattern.test(str);
	}
	, isPassword : function(str, isMsg) {
		var pattern1 = /^[a-zA-Z0-9]{6,13}$/; // 영문, 숫자 자리수
		var pattern2 = /[0-9]/g; // 숫자
		var pattern3 = /[a-zA-Z]/ig; // 영문
		var pattern4 = /(\w)\1\1\1/; // 같은문자 연속 제한 4회
		
		if (isMsg != undefined && isMsg) {
			if (!pattern1.test(str)) {
				alert(common.getMsg(message.validate.password1));
				return false;
			}

			if (!(pattern2.test(str) && pattern3.test(str))) {
				alert(common.getMsg(message.validate.password2));
				return false;
			}

			if (pattern4.test(str)) {
				alert(common.getMsg(message.validate.password3));
				return false;
			}
		}

		return pattern1.test(str) && pattern2.test(str) && pattern3.test(str)
				&& pattern4.test(str);
	}
	
	,	 isBrn : function(str) {
		var pattern = /^[0-9]{10}$/;
		return pattern.test(str);
	}
	, isPhone : function(str) {
		var pattern = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/;
		return pattern.test(str);
	}
	, isMobilePhone : function(str) {
		var pattern = /^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$/;
		return pattern.test(str);
	}
	, isNotOverMaxLength : function( ele, maxLen ) {
		var str = (ele == null || typeof(ele) == "undefined" ? "" : ele.value);
		var strLength = str.length;
		var cntHan = 0;
		for (var i = 0;i < strLength;i++) {
			var code = str.charCodeAt(i);
			var ch = str.substr(i,1).toUpperCase();
			code = parseInt(code);
			
			if ((ch < "0" || ch > "9") && (ch <"A" || ch > "Z") && (code > 255 || code <0)) {
				cntHan += 1;
			}
		}
		
		if (maxLen < str.length + cntHan) return false;
		else return true;
	}
	, onlyNumberKeyDown : function(_obj) {
		$(_obj).keydown( function(event) {
			if (event.which && (event.which > 47 && event.which < 58 
					|| event.which == 110 || event.which == 190 || event.which == 8 
					|| event.which == 9 || (event.which > 95 && event.which < 106))) {
			} else {
				event.preventDefault();
				return false;
			}
		});
	}
	, inputCheckSpecial : function(str , id) {
		$("#" + id).keyup(function( event ) {
			if ( event.which == 13 ) event.preventDefault();
			if ( !common.checkSpecial( $("#"+id).val() ) ) {
				var len = $("#"+id).val().length;
				var s = $("#"+id).val().substr(0,len-1);
				$("#" + id).val( s );
				alert("특수문자는 입력하실수 없습니다.");
				return false;
			}
		});	
	}
	, checkSpecial : function(str) {
		var strobj = str;
	    re = /[~!@\#$%^&*\()\=+_']/gi;
	    if(re.test(strobj)){
		    return false;
	    }
	    return true;
	}
	, strMaxLengthChk : function( value , id , text , maxLenth ) {
		$("#" + id).keyup(function( event ) {
			if ( !common.isNotOverMaxLength(value, maxLenth ) ) {
				var s = $("#"+id).val().substr(0,maxLenth);
				$("#" + id).val( s );
				alert("입력한 " + text + "이 최대길이가 초과했습니다.");
				return false;
			}
		});
	}
	, fncPopup : function(url, title , width, height) {
		var popup = window.open(url , "", 'top='+((screen.availHeight - height)/2 - 40) +', left='+(screen.availWidth - width)/2+', width='+width+', height='+height+', toolbar=no, location=no, directories=no, status=no, menubar=no, resizable=no, scrollbars=auto, copyhistory=no');
		if( popup ){
			popup.focus();
		}
		return popup;
	}
	, extensionCheck : function(str , id) {
		var value = str;
		var v = $("#"+id).val();
		var flag = false;
		for ( var i=0; i<value.length;i++){
			if ( value[i] == v.substring(  v.lastIndexOf(".")+1 , v.length ).toLowerCase() ) {
				flag = true;
				break;
			}
		}
		if ( flag == false ) {
			alert(value+ " 확장자만 등록할 수 있습니다.");
			return false;
		}
		return flag;
	} 
	, log : function(json) {
		alert(JSON.stringify(json, null, '    '));
	}
	, dateFormat : function(value , str) {
		return value.substring(0 ,10).replace('/' + str + '/gi', "/");
	}
	, calDateRange : function(start, end) {
        var FORMAT = "-";

        if (start.length != 10 || end.length != 10)
            return null;
        
        if (start.indexOf(FORMAT) < 0 || end.indexOf(FORMAT) < 0)
            return null;
        
        var start_dt = start.split(FORMAT);
        var end_dt = end.split(FORMAT);

        start_dt[1] = (Number(start_dt[1]) - 1) + "";
        end_dt[1] = (Number(end_dt[1]) - 1) + "";

        var from_dt = new Date(start_dt[0], start_dt[1], start_dt[2]);
        var to_dt = new Date(end_dt[0], end_dt[1], end_dt[2]);

        return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60 / 24;
	}
	, getDate: function(value) {
        var FORMAT = "-";

        if (value.length != 10)
            return null;
        
        if (value.indexOf(FORMAT) < 0)
            return null;
        
        var value_dt = value.split(FORMAT);

        value_dt[1] = (Number(value_dt[1]) - 1) + "";

        return new Date(value_dt[0], value_dt[1], value_dt[2]);
	}
	, transDateFormat: function(date, format) {
		if (!date.valueOf()) return " ";
		
		var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"]; 
		
		return format.replace(/(yyyy|yy|MM|dd|E|hh|HH|mm|ss|a\/p)/g, function($1) {
			switch ($1) {
			case "yyyy": return date.getFullYear();
            case "yy": return common.Lpadding(date.getFullYear() % 1000, 2, "0");
            case "MM": return common.Lpadding(date.getMonth() + 1, 2, "0");
            case "dd": return common.Lpadding(date.getDate(), 2, "0");
            case "E": return weekName[date.getDay()];
            case "HH": return common.Lpadding(date.getHours(), 2, "0");
            case "hh": return common.Lpadding(((h = date.getHours() % 12) ? h : 12), 2, "0");
            case "mm": return common.Lpadding(date.getMinutes(), 2, "0");
            case "ss": return common.Lpadding(date.getSeconds(), 2, "0");
            case "a/p": return date.getHours() < 12 ? "오전" : "오후";
            default: return $1;
            }
		});
	}
	, getStrToDate: function(str) {
		if ((typeof str == 'undefined') && (str.length != 19)) {
			console.log(str + '은 yyyy-MM-dd HH:mm:ss 형식이어야 합니다.');
			return null;
		}
		
		var pattern = /(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})/;
		var arrDate = pattern.exec(str);
		
	    return new Date(arrDate[1], arrDate[2] - 1, arrDate[3], arrDate[4], arrDate[5], arrDate[6]);
	}
	, Lpadding: function(val, len, padding) {
	  var valLen = (val + "").length;
	  var s = '';
	  
	  for (var i = 0, size = (len - valLen); i < size; i++) {
	    s += padding;
	  }
	  
	  return s + val;
	}
	, dateSlushReplace : function(value) {
		return value.replace(/\//gi, '');
	}
	, hourFormat : function(value) {
		return value.substring(0,2)+":"+value.substring(2,4);
	}
	, paging : function(appendEle, totCnt, curPage, callback, l_cnt, p_cnt, sellBoolean) {
		var sellBoolean = (sellBoolean == null || sellBoolean == undefined) ? false : sellBoolean; // 처음, 끝 표현 여부
		
		appendEle.html("");

		if (totCnt == undefined) {
			return;
		}

		var pageCnt = (sellBoolean == true) ? common.pageSize : 5;

		pageCnt = (sellBoolean == true) ? common.pageSize : 5;
		p_cnt = (sellBoolean == true) ? p_cnt : 5;

		if (p_cnt != null) {
			pageCnt = p_cnt;
		}

		var listCnt = 5;
		if (l_cnt != null) {
			listCnt = l_cnt
		}

		var totPageCnt = Math.floor(totCnt / listCnt) + Math.ceil((totCnt % listCnt) / listCnt);
		var start = (pageCnt * (Math.ceil(curPage / pageCnt) - 1)) + 1;

		start = (totPageCnt < start) ? 1 : start;

		var end = start + pageCnt - 1;
		if (end > totPageCnt) {
			end = totPageCnt;
		}
		
		var startImg = $('<li class="prev"><a href="#" title="Prev"><i class="fa fa-angle-left"></i></a></li>');

		if (curPage != 1) {
			startImg.bind("click", {
				page : (curPage - 1)
			}, callback);
		}

		appendEle.append(startImg);
		
		if (curPage == 1) {
			$("li.prev").addClass("disabled").children("a").click(function() { return false;});
		}

		for ( var i = start; i <= end; i++) {
			var pageTag = i;

			if (i == curPage) {
				pageTag = $('<li class="active"><a href="#">' + i + '</a></li>');
			} else {
				pageTag = $('<li><a href="#">' + i + '</a></li>');
				pageTag.bind("click", {
					page : i
				}, callback);
			}

			appendEle.append(pageTag);
		}

		var endImg = $('<li class="next"><a href="#" title="Next"><i class="fa fa-angle-right"></i></a></li>');
		
		if (curPage != totPageCnt) {
			endImg.bind("click", {
				page : (curPage + 1)
			}, callback);
		}

		appendEle.append(endImg);
		
		if ((totPageCnt == 0) || (curPage == totPageCnt)) {
			$("li.next").addClass("disabled").children("a").click(function() { return false;});
		}
	}
	, makeComboBox : function(url, obj, grp_cd_id, userTitle, selectedValue) {
		var reqData = {
			'grp_cd_id' : grp_cd_id
		};

		common
				.ajax({
					"url" : url,
					"data" : reqData,
					"success" : function(json) {

						if (typeof userTitle == "boolean") {
							if (userTitle) {
								$(obj).append('<option value="">' + common.getMsg(message.selectbox.title) + '</option>');
							}
						} else {
							$(obj).append('<option value="">' + userTitle + '</option>');
						}

						$(json.resultData).each(
								function(i, itm) {
									if (selectedValue == itm.cd_id) {
										$(obj).append('<option value="' + itm.cd_id + '" selected>' + itm.cd_nm + '</option>');
									} else {
										$(obj).append('<option value="' + itm.cd_id + '">' + itm.cd_nm + '</option>');
									}

								});
					}
				});
	}

	, getMsg : function() {
		var regPlaceHolder = /(\{\d+\})/g;
		var regRepPlaceHolder = /\{(\d+)\}/g;
		var str = arguments[0];

		if (regPlaceHolder.test(str)) {
			for ( var j = 1; j < arguments.length; j++) {
				var parts = str.split(regPlaceHolder);
				for ( var i = 0; i < parts.length; i++) {
					if (regPlaceHolder.test(parts[i])) {
						str = str.replace(parts[i], arguments[j]);
					}
				}
			}
		}

		if (str == undefined) {
			str = "message code not found !!!";
		}
		return str;
	}
	, reduceText : function(str, n) {
		if (n == undefined) {
			return str;
		}
		var rStr = null;

		rStr = (str == null || str.length < n) ? str : str.substr(0, n) + "...";
		return rStr;
	}
	, toEnter : function(data) {
		if (data == undefined || data == "") {
			return "-";
		}

		var str = data;
		for ( var i = 0; i < str.length; i++) {
			str = str.replace("\n", "<br />");
		}
		return str;
	}
	, toRound : function(n, pos) {

		if (n == null || n == undefined || n == "" || n == "-") {
			return "-";
		}

		var digits = Math.pow(10, pos);
		var sign = 1;
		if (n < 0) {
			sign = -1;
		}
		// 음수이면 양수처리후 반올림 한 후 다시 음수처리
		n = n * sign;
		var num = Math.round(n / digits) * digits;
		num = num * sign;

		return num.toFixed(pos);
	}
	, toFloor : function(n, pos) {

		var digits = Math.pow(10, pos);
		var num = Math.floor(n / digits) * digits;

		return num.toFixed(pos);
	}
	, toCeil : function(n, pos) {

		var digits = Math.pow(10, pos);
		var num = Math.ceil(n / digits) * digits;

		return num.toFixed(pos);
	}
	, replaceAll : function(inputString, targetString, replacement) {
		var v_ret = null;
		var v_regExp = new RegExp(targetString, "g");
		v_ret = inputString.replace(v_regExp, replacement);

		return v_ret;
	}
	, windowClose : function() {
		if (/MSIE/.test(navigator.userAgent)) {
			// Explorer 7이상 일때
			if (navigator.appVersion.indexOf("MSIE 7.0") >= 0) {
				window.open('about:blank', '_self').close();
			}
			// Explorer 7이하 일때
			else {
				window.opener = self;
				self.close();
			}
		} else {
			window.open('', '_self', '');
			window.close();
		}
	}
	, checkExt : function(filename, regex) {
		if (!(new RegExp(regex, "i")).test(filename)) {
			return false;
		}
		return true;
	}
	, checkRegex : function(value, regex) {
		if (!(new RegExp(regex, "i")).test(value)) {
			return false;
		}
		return true;
	}
	, imagePreView : function(fileObj, targetId) {
		var dest = document.getElementById(targetId);
		if (fileObj.val() != "") {
			pathpoint = fileObj.val().lastIndexOf('.');

			filepoint = fileObj.val().substring(pathpoint + 1);
			filetype = filepoint.toLowerCase();
			// document.getElementById("profileImgView").style.display='none';
			dest.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"
					+ fileObj.val() + "',sizingMethod=scale)";
		}
	}
	// 상태리턴
	, returnState : function(s) {
		if(s == "B") return "<span class='status7'>미배정</span>";
		if(s == "S") return "<span class='status9'>가배정</span>";
		if(s == "R") return "<span class='status8'>배정</span>";
		if(s == "C") return "<span class='status10'>인증</span>";
		if(s == "A") return "<span class='status4'>광고중</span>";
		if(s == "N") return "<span class='status3'>종료</span>";
		if(s == "F") return "<span class='status6'>장애</span>";
	}
	, selectBoxMake : function(idx , id) {
		var index = 0;
		for( var i=index; i<=idx; i++ ) {
			var number = i;
			if ( number < 10 ) number = "0" + number;
			$("#" + id).append('<option value="'+number+'" >' + number +'</option>');
		}				
	}
	, selectBoxMakeFirstOne : function(idx , id) {
		var index = 1;
		for( var i=index; i<=idx; i++ ) {
			var number = i;
			if ( number < 10 ) number = "0" + number;
			$("#" + id).append('<option value="'+number+'" >' + number +'</option>');
		}				
	}
	, commify : function(n) {
		var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
		n += '';                          // 숫자를 문자열로 변환
	 
		while (reg.test(n))
			n = n.replace(reg, '$1' + ',' + '$2');
	 
		return n;
	}
	, getCurrentMonth : function() {
		var now 	= new Date();
		var year	= now.getFullYear();
		var month	= now.getMonth() + 1;
		
		if(month < 10) month = '0' + month;
		return year + '' + month;
	}
	, distinctArray : function(dupArray) {
		var uniqArray = [];
		$.each(dupArray, function(i, el){
		    if($.inArray(el, uniqArray) === -1) uniqArray.push(el);
		});
		
		return uniqArray;
	}
	, parseKV : function(kv, seperator) { 
		return kv.split(seperator);
	}
	, beautifier : function(value, defaultValue, unit) { 
		if (typeof value == "undefined") {
			return defaultValue;
		}
		else if (value > 0) {
			return value + unit;
		}
		
		return defaultValue;
	}
	, videoTag : function(url, width) {
		var el = '';
		
		el +=  '<video width="' + width + '" controls>';
		el +=   '<source src="' + url + '" type="video/mp4" />';
		el +=   '<source src="' + url + '" type="video/webm" />';
		el +=   '<source src="' + url + '" type="video/ogg" />';
		el +=   '<object>';
		el +=    '<embed src="' + url + '" width="' + width + '" showStatusBar="true" EnableContextMenu="true"></embed>';
		el +=   '</object>';
		el +=   '<p>지원하지 않는 브라우저 입니다.</p>';
		el +=   '<a href="' + url + '">Download</a>';
		el +=  '</video>';
		
		return el;
	}
};
