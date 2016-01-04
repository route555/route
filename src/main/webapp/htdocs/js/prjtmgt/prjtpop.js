var trAcctCd;
var prjtCd;
var dstrbtSectCdList;
var skillSectCdList;
var table1;
var rowIdx;

var view = {
		
		onLoadEvent : function() {
			
			
			$("#btnClose").unbind('click');
			$("#btnClose").click( function() {
				self.close();
				
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
						"autoWidth": true,
						"ordering": false,
						"paging": false,
						//"columnDefs": [ { visible: false, targets: [1]  } ],
						"deferLoading": 0,
						"iDisplayLength": 10,
						// "scrollY":        "300px",
						
					  //      "scrollCollapse": true,
						"aoColumns": [
						        { data: 'dstrbtSectCd' , "render": function ( data,  code) {
						        	//console.log(dstrbtSectCdList);
						        	var html =  '<select name="dstrbtSectCd" id="dstrbtSectCd" style="width:120px;" class="form-control" disabled="true"><option value=""></option>';
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
						        { data: 'prsnNm' , "render": function ( data ) { return '<input type="input" name="prsnNm" id="prsnNm" class="form-control" style="width:60px;" disabled="true" value="'+data+'">';} },
						        { data: 'cntrctSectCdNm' , "render": function ( data ) { return '<input type="input" name="cntrctSectCdNm" id="cntrctSectCdNm" style="width:100%;" class="form-control" disabled="true" value="'+data+'">';} },
						        { data: 'skillSectCd' , "render": function ( data,  code) {
						        	//console.log(skillSectCdList);
						        	var html =  '<select name="skillSectCd" id="skillSectCd" style="width:100px;" class="form-control" disabled="true"><option value=""></option>';
						        	
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
						        
						        { data: 'workStartDt' , "render": function ( data ) { return '<input type="input" name="_workStartDt" id="_workStartDt" style="width:100%;" disabled="true" class="form-control" maxlength="8" value="'+data+'">';} },
						        { data: 'workEndDt' , "render": function ( data ) { return '<input type="input" name="_workEndDt" id="_workEndDt" style="width:100%;" disabled="true" class="form-control" maxlength="8" value="'+data+'">';} },
						        { data: 'prsnMm' , "render": function ( data ) { return '<input type="input" name="prsnMm" id="prsnMm" style="width:60px;"  disabled="true" class="form-control" maxlength="5" value="'+data+'">';} },
						        { data: 'salesUnitCostAmt' , "render": function ( data ) { return '<input type="input" name="salesUnitCostAmt" id="salesUnitCostAmt" style="width:80px;" disabled="true" class="form-control" maxlength="9" value="'+data+'">';} },
						        { data: 'ordrUnitCstAmt' , "render": function ( data ) { return '<input type="input" name="ordrUnitCstAmt" id="ordrUnitCstAmt" style="width:80px;" disabled="true" class="form-control" maxlength="9" value="'+data+'">';} },
						        { data: 'memoDesc' , "render": function ( data ) { return '<input type="input" name="_memoDesc" id="_memoDesc" style="width:100%;" readonly="readonly" class="form-control" maxlength="1000" title="'+data+'" value="'+data+'">';} },
						        { data: '' , "render": function ( data ) { return '<button type="button" style="align:center;" class="btn btn-success" id="btnPrsnPop">보기</button>';} }

						],
						"sAjaxSource" : G_CONTEXT_PATH+"/prjtPrsn/selectPrjtPrsnList",
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
				
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				
				rowIdx = $(this).index();
			      
			    //alert(rowIdx);
		    } );
		}
		

};
$(function() {
	view.onLoadEvent();
});
