var trAcctCd;
var prjtCd;
var dstrbtSectCdList;
var skillSectCdList;
var table1;
var rowIdx;

var view = {
		
		onLoadEvent : function() {
			
			$("#btnRowAdd").click( function() {
				var $obj = $("input[name='choice']");
				var checkCount = $obj.size();
				if(checkCount == 0 || checkCount == "undefind") {
					$("#dataTables-prjtPrsnList tr:not(:first)").remove();
				}
				
				var html = "";
				html += '<tr><td><input type="radio" name="choice" id="choice" style="width:100%;" value=""/></td>';
				html += '<td class="hideTr"><input type="text" name="prsnNo" id="prsnNo" readonly="readonly" style="width:50px;" class="form-control text-center" value=""/></td>';
				html +=  '<td><select name="dstrbtSectCd" id="dstrbtSectCd" class="form-control" style="width:120px;"><option value="">선택</option>';
	        	
				
				
	        	for (var i=0; i<dstrbtSectCdList.length; i++) {
	        		var val = dstrbtSectCdList[i];
	        		 //console.log(chgrSectCdList[0][i]);
	        		 html += '<option value="'+val[0]+'"';
			         html += ' >'+val[1]+'</option>';
	        	}
	        	
	        	html += '</select></td>';
				html += '<td><input type="input" name="prsnNm" id="prsnNm" class="form-control" style="width:100%;" maxlength="10" value=""></td><td><input type="input" name="cntrctSectCdNm" id="cntrctSectCdNm" class="form-control" style="width:100%;" maxlength="10" value=""></td>';
				
				html +=  '<td><select name="skillSectCd" id="skillSectCd" class="form-control" style="width:120px;"><option value="">선택</option>';
				for (var i=0; i<skillSectCdList.length; i++) {
	        		var val = skillSectCdList[i];
	        		 //console.log(chgrSectCdList[0][i]);
	        		 html += '<option value="'+val[0]+'"';
			         html += ' >'+val[1]+'</option>';
	        	}
				html += '</select></td>';
				
				html += '<td><input type="input" name="_workStartDt" id="_workStartDt" class="form-control" style="width:100%;" maxlength="8" value="" onBlur="javascript:fnCalMm();"></td><td><input type="input" name="_workEndDt" id="_workEndDt" class="form-control" style="width:100%;" maxlength="8" value="" onBlur="javascript:fnCalMm();"></td><td><input type="input" name="prsnMm" id="prsnMm" class="form-control" style="width:60px;" maxlength="5" value=""></td><td><input type="input" name="salesUnitCostAmt" id="salesUnitCostAmt" class="form-control mask-number" style="width:100%;" maxlength="9" value=""></td><td><input type="input" name="ordrUnitCstAmt" id="ordrUnitCstAmt" class="form-control mask-number" style="width:100%;" maxlength="9" value=""></td><td><input type="input" name="_memoDesc" id="_memoDesc" class="form-control" style="width:100%;" maxlength="1000" value=""></td><td><input type="input" name="prjtPrsnCnt" id="prjtPrsnCnt" class="form-control" style="width:100%;" maxlength="10" value="N"></td>';
				html += '<td><button type="button" style="align:center;" class="btn btn-success" id="btnPrsnPop1">선택</button></td></tr>';
				
				//alert(html);
				$('#dataTables-prjtPrsnList > tbody:last').append(html);
				view.setMask();
			});
			
			$("#btnRowDel").click( function() {
				//$('#dataTables-tracctChgrList > tbody:last > tr:last').remove();   
				var $obj = $("input[name='choice']");
				var checkCount = $obj.size();
				for (var i=0; i<checkCount; i++){
					if($obj.eq(i).is(":checked")  && $("#f input[name='prjtPrsnCnt']").eq(i).val() == "N"){ //매입계약 체결이 안되면 삭제
						$obj.eq(i).parent().parent().remove();
					} else if($obj.eq(i).is(":checked")  && $("#f input[name='prjtPrsnCnt']").eq(i).val() == "Y"){
						alert("매입계약중 데이터는 삭제할 수 없습니다.");
						return;
					}
				}
			});
			
			$("#btnSave").unbind('click');
			$("#btnSave").click( function() {
				var $obj = $("input[name='choice']");
				var checkCount = $obj.size();
				
				if($("#prjtEndYn").val() == "Y") {
					alert("종료된 프로젝트에 인력을 배정할 수 없습니다.");
					return;
				}
				
				
				if (confirm("저장 하시겠습니까?") == false){
					  return;
				}
				
				view.insertData();	

				
			});
			
			$("#btnList").unbind('click');
			$("#btnList").click( function() {
				var url = G_CONTEXT_PATH+"/web/prjtmgt/prjtmgt";
				$(location).attr('href', url);
				
			});
			
			//프로젝트상세
			prjtCd = $("#prjtCd").val();
			trAcctCd = $("#trAcctCd").val();
			view.selectPrjtDetail(prjtCd);
			
			view.selectCommonCode();
			view.selectCommonCode2();
			view.selectPrjtPrsnList();
			view.selectSalesTotAmt();
			
			//인력 목록 조회
        	table1 = $('#dataTables-prjtPrsnList').dataTable();
        	table1.fnReloadAjax();
			
		}

		, setMask : function(){
			$(".mask-number").unpriceFormat();
			$(".mask-number").priceFormat({
				 prefix: '',
				  centsLimit: 0
			 });
		}
		, selectPrjtDetail : function(pPrjtCd){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/prjtPrsn/"+pPrjtCd
				  		, type : "GET"
						, data  : reqData
						, success : view.selectPrjtDetailCallBack
			});
			
		}
		, selectPrjtDetailCallBack : function(json){
			$(json.detail).each(function(idx, itm) {
				$("#trAcctCd").val( itm.trAcctCd);
				$("#trAcctNm").val( itm.trAcctNm);
				$("#prjtCd").val( itm.prjtCd);
				$("#prjtNm").val( itm.prjtNm);
				$("#cstmrNm").val( itm.cstmrNm);
		
				$("#prjtCfrmDt").val( itm.prjtCfrmDt);
				$("#prjtCfrmYn").val( itm.prjtCfrmYn);
				$("#prjtEndDt").val( itm.prjtEndDt);
				$("#prjtEndYn").val( itm.prjtEndYn);
				$("#workStartDt").val( itm.workStartDt);
				$("#workEndDt").val( itm.workEndDt);
				$("#abrdCdNm").val( itm.abrdCdNm);
				$("#prjtPrptyCdNm").val( itm.prjtPrptyCdNm);
				$("#prjtGrndDesc").val( itm.prjtGrndDesc);
				$("#memoDesc").val( itm.memoDesc);
				
				$("#prjtCfrmDt").val( itm.prjtCfrmDt);
				$("#prjtEndDt").val( itm.prjtEndDt);
				
				$("#prjtEndYn").val( itm.prjtEndYn);
				
				
			});

			$(json.detail2).each(function(idx, itm) {	
				$("#chgSeqNo1").val( itm.chgrSeqNo);
				//$("#chgSectNm1").val( itm.chgSectNm);
				$("#chgrNm1").val( itm.chgrNm);
				$("#pstnNm1").val( itm.pstnNm);
				$("#telNo1").val( itm.telNo);
				$("#hpNo1").val( itm.hpNo);
				$("#emailAddr1").val( itm.emailAddr);
			});
			
			$(json.detail3).each(function(idx, itm) {
				$("#chgSeqNo2").val( itm.chgrSeqNo);
				//$("#chgSectNm2").val( itm.chgSectNm);
				$("#chgrNm2").val( itm.chgrNm);
				$("#pstnNm2").val( itm.pstnNm);
				$("#telNo2").val( itm.telNo);
				$("#hpNo2").val( itm.hpNo);
				$("#emailAddr2").val( itm.emailAddr);
			});
			
		}
		, selectSalesTotAmt : function(pPrjtCd){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/prjtPrsn/selectSalesTotAmt"
				  		, type : "POST"
						, data  : reqData
						, success : view.selectPSalesTotAmtCallBack
			});
			
		}
		, selectPSalesTotAmtCallBack : function(json){
			$(json.detail).each(function(idx, itm) {
				$("#salesWorkTotAmt").val( itm.workSalesTotAmt);
				$("#salesEtcTotAmt").val( itm.ectSalesTotAmt);
				$("#salesTotAmt").val( itm.salesTotAmt);				
			});

		}
		, selectCommonCode : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/011"
				  		, type : "GET"
						, success : view.selectCommonCodeCallBack
			});
		}
		, selectCommonCodeCallBack : function(json) {
			dstrbtSectCdList = new Array();
			$(json.list).each(function(i, itm){				
				var array = new Array();
				array.push(itm.dtlCd);
				array.push(itm.dtlCdNm);
				dstrbtSectCdList.push(array);
				//console.log(itm.dtlCdNm);
			});
		}
		, selectCommonCode2 : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/006"
				  		, type : "GET"
						, success : view.selectCommonCodeCallBack2
			});
		}
		, selectCommonCodeCallBack2 : function(json) {
			skillSectCdList = new Array();
			$(json.list).each(function(i, itm){				
				var array = new Array();
				array.push(itm.dtlCd);
				array.push(itm.dtlCdNm);
				skillSectCdList.push(array);
				//console.log(itm.dtlCdNm);
			});
		}
		, selectPrjtPrsnList : function() {
			var table = $('#dataTables-prjtPrsnList').DataTable(
					{
						"processing" : true,
						"serverSide" : true,
						"bFilter": false,
						"autoWidth": false,
						"ordering": false,
						"paging": false,
						//"columnDefs": [ { visible: false, targets: [1]  } ],
						"deferLoading": 0,
						"iDisplayLength": 10,
						"columnDefs": [ { visible: false, targets: []  },{ className: "text-center", "targets": [ 0 ] }, { className: "hideTr", "targets": [ 1 ] }],
						"aoColumns": [
						        { data: '' , "render": function ( data ) { return '<input type="radio" name="choice" id="choice" style="width:100%;" class="" value="">';} },
						        { data: 'prsnNo' , "render": function ( data ) { return '<input type="text" name="prsnNo" id="prsnNo" readonly="readonly" style="width:50px;" class="form-control" value="'+data+'">';} },
						        { data: 'dstrbtSectCd' , "render": function ( data,  code) {
						        	//console.log(dstrbtSectCdList);
						        	var html =  '<select name="dstrbtSectCd" id="dstrbtSectCd" style="width:120px;" class="form-control"><option value="">선택</option>';
						        	for (var i=0; i<dstrbtSectCdList.length; i++) {
						        		var val = dstrbtSectCdList[i];
						        		 //console.log(chgrSectCdList[0][i]);
						        		 html += '<option value="'+val[0]+'"';
								         html += data == val[0] ? "selected=\"selected\"" : "\"\"";
								         html += ' >'+val[1]+'</option>';
						        	}
						        	
						        	html += '</select>';
						        	
						        	return html;
						        } },
						        { data: 'prsnNm' , "render": function ( data ) { return '<input type="input" name="prsnNm" id="prsnNm" class="form-control" style="width:100%;" readonly="readonly" value="'+data+'">';} },
						        { data: 'cntrctSectCdNm' , "render": function ( data ) { return '<input type="input" name="cntrctSectCdNm" id="cntrctSectCdNm" style="width:100%;" class="form-control" readonly="readonly" value="'+data+'">';} },
						        { data: 'skillSectCd' , "render": function ( data,  code) {
						        	//console.log(skillSectCdList);
						        	var html =  '<select name="skillSectCd" id="skillSectCd" style="width:100px;" class="form-control"><option value="">선택</option>';
						        	
						        	for (var i=0; i<skillSectCdList.length; i++) {
						        		var val = skillSectCdList[i];
						        		 //console.log(skillSectCdList[0][i]);
						        		 html += '<option value="'+val[0]+'"';
								         html += data == val[0] ? "selected=\"selected\"" : "\"\"";
								         html += ' >'+val[1]+'</option>';
						        	}
						        	
						        	html += '</select>';
						        	
						        	return html;
						        } },
						        
						        { data: 'workStartDt' , "render": function ( data ) { return '<input type="input" name="_workStartDt" id="_workStartDt" style="width:100%;" class="form-control" maxlength="8" value="'+data+'" onBlur="javascript:fnCalMm();">';} },
						        { data: 'workEndDt' , "render": function ( data ) { return '<input type="input" name="_workEndDt" id="_workEndDt" style="width:100%;" class="form-control" maxlength="8" value="'+data+'" onBlur="javascript:fnCalMm();">';} },
						        { data: 'prsnMm' , "render": function ( data ) { return '<input type="input" name="prsnMm" id="prsnMm" style="width:60px;" class="form-control" maxlength="5" value="'+data+'">';} },
						        { data: 'salesUnitCostAmt' , "render": function ( data ) { return '<input type="input"  name="salesUnitCostAmt" id="salesUnitCostAmt" style="width:100%;" class="form-control mask-number" maxlength="9" value="'+data+'">';} },
						        { data: 'ordrUnitCstAmt' , "render": function ( data ) { return '<input type="input" name="ordrUnitCstAmt" id="ordrUnitCstAmt" style="width:100%;" class="form-control mask-number" maxlength="9" value="'+data+'">';} },
						        { data: 'memoDesc' , "render": function ( data ) { return '<input type="input" name="_memoDesc" id="_memoDesc" style="width:100%;" class="form-control" maxlength="1000" title="'+data+'" value="'+data+'">';} },
						        { data: 'prjtPrsnCnt' , "render": function ( data ) { return '<input type="input" name="prjtPrsnCnt" id="prjtPrsnCnt" style="width:100%;" class="form-control" maxlength="10" value="'+data+'">';} },
						        { data: '' , "render": function ( data ) { return '<button type="button" style="align:center;" class="btn btn-success" id="btnPrsnPop">선택</button>';} }

						],
						"sAjaxSource" : G_CONTEXT_PATH+"/prjtPrsn/selectPrjtPrsnList",
						"fnDrawCallback": function( nRow, aData, iDataIndex ) {
							view.setMask();
						},
			
						
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							aoData.push({
								"name" : "prjtCd",
								"value" :  prjtCd
							});
							oSettings.jqXHR = $.ajax({
								"dataType" : 'json',
								"type" : "GET",
								"url" : sSource,
								"data" : aoData,
								"success" : function(json) {
									var o = {
											recordsTotal : json.totalCnt,
											recordsFiltered : json.totalCnt,
											data : json.list
									};
									fnCallback(o);
								}
							});
						}
					});
			
			$('#dataTables-prjtPrsnList tbody').on('click', 'tr', function () {
				/*
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				*/
				rowIdx = $(this).index();
			      
			    //alert(rowIdx);
		    } );
			
			
			$('#dataTables-prjtPrsnList tbody').on('click', 'td', function(e) {
				console.log('aaaa')
				if($(this).children(":first").is(':button')){
					console.log('asdf')
					
					window.open(G_CONTEXT_PATH+"/web/person/personPop?prjtCd="+$("#prjtCd").val()+"&trAcctCd="+$("#trAcctCd").val(), "window", "width=1200,height=800,scrollbars=yes");
					
					
					//view.popEvent('a')
					/*
					var chk = $(this).closest("tr").find("input:radio").get(0);
					if (e.target != chk) {
						chk.checked = !chk.checked;
					}
					*/
				}
				
				
			});
			
		}
		
		
		, popEvent : function(data) {
			
			console.log(rowIdx,data);
			$('input[name="prsnNo"]').eq(rowIdx).val(data.prsnNo);
			$('input[name="prsnNm"]').eq(rowIdx).val(data.prsnNm);
			$('input[name="cntrctSectCdNm"]').eq(rowIdx).val(data.cntrctSectCdNm);
			
		}
		, insertData : function() {
			$(".mask-number").unpriceFormat();
			
			$(".mask-number").priceFormat({
				 prefix: '',
				 thousandsSeparator: '',
				  centsLimit: 0
			 });
			
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/prjtPrsn/insertPrjtPrsn"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
			
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {
				alert("등록되었습니다.");
				var table = $('#dataTables-prjtPrsnList').dataTable();
				table.fnReloadAjax();
				
				view.selectSalesTotAmt();
			}
			else {
				alert(json.msg);
			}	
		}	


};
$(function() {
	view.onLoadEvent();
});

function fnCalMm() {
	var $obj = $("input[name='choice']");
	var checkCount = $obj.size();
	var sDate;
	var eDate;
	
	//alert($("#f select[name='dstrbtSectCd']").eq(rowIdx).val());
	//console.log($("#f select[name='dstrbtSectCd']"));
	//console.log(rowIdx);
	if($("#f select[name='dstrbtSectCd']").eq(rowIdx).val() != "001") {
		return;
	}
	
	if(checkCount == 1) {
		sDate = $("#f input[name='_workStartDt']").val();
		eDate = $("#f input[name='_workEndDt']").val();

	} else if(checkCount > 1) {
		sDate = $("#f input[name='_workStartDt']").eq(rowIdx).val();
		eDate = $("#f input[name='_workEndDt']").eq(rowIdx).val();
	}
	
	
	//alert(sDate);
	//alert(eDate);
	
	//일수계수
    //alert(sDate.substring(0, 4)+"-"+sDate.substring(4, 6)+"-"+sDate.substring(6, 8));
    //alert(eDate.substring(0, 4)+"-"+eDate.substring(4, 6)+"-"+eDate.substring(6, 8));
    
    sDate = sDate.substring(0, 4)+"-"+sDate.substring(4, 6)+"-"+sDate.substring(6, 8);
    eDate = eDate.substring(0, 4)+"-"+eDate.substring(4, 6)+"-"+eDate.substring(6, 8);
     

    //날짜 유효성검사
    if (!isValidDate(sDate) ) {
    	//alert(rowIdx+'번째 행의 투입시작일자 오류입니다.');
    	//return;
    } 
    if (!isValidDate(eDate) ) {
    	//alert(rowIdx+'번째 행의 투입종료일자 오류입니다.');
    	//return;
    }
   
    var days = calDateRange(sDate, eDate);
    var num = days/30;
    var mm = (Math.floor(num*10)) / 10;
    
    //alert("aaa:"+days);
    
    if(checkCount == 1) {
    	$("#f input[name='prsnMm']").val(mm);
    } 
    else if(checkCount > 1) {
    	$("#f input[name='prsnMm']").eq(rowIdx).val(mm);
    }
    
	
	
	
}

/**
 * 두 날짜의 차이를 일자로 구한다.(조회 종료일 - 조회 시작일)
 *
 * @param val1 - 조회 시작일(날짜 ex.2002-01-01)
 * @param val2 - 조회 종료일(날짜 ex.2002-01-01)
 * @return 기간에 해당하는 일자
 */
function calDateRange(val1, val2)
{
    var FORMAT = "-";

   

    // FORMAT을 포함한 길이 체크
    if (val1.length != 10 || val2.length != 10)
        return null;



    // FORMAT이 있는지 체크
    if (val1.indexOf(FORMAT) < 0 || val2.indexOf(FORMAT) < 0)
        return null;



    // 년도, 월, 일로 분리
    var start_dt = val1.split(FORMAT);
    var end_dt = val2.split(FORMAT);



    // 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
    // Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
    start_dt[1] = (Number(start_dt[1]) - 1) + "";
    end_dt[1] = (Number(end_dt[1]) - 1) + "";



    var from_dt = new Date(start_dt[0], start_dt[1], start_dt[2]);
    var to_dt = new Date(end_dt[0], end_dt[1], end_dt[2]);


    return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60 / 24;
}


function isValidDate(param) {
    try
    {
        param = param.replace(/-/g,'');

        // 자리수가 맞지않을때
        if( isNaN(param) || param.length!=8 ) {
            return false;
        }
         
        var year = Number(param.substring(0, 4));
        var month = Number(param.substring(4, 6));
        var day = Number(param.substring(6, 8));

        var dd = day / 0;

         
        if( month<1 || month>12 ) {
            return false;
        }
         
        var maxDaysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        var maxDay = maxDaysInMonth[month-1];
         
        // 윤년 체크
        if( month==2 && ( year%4==0 && year%100!=0 || year%400==0 ) ) {
            maxDay = 29;
        }
         
        if( day<=0 || day>maxDay ) {
            return false;
        }
        return true;

    } catch (err) {
        return false;
    }                       
}
