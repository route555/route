var trAcctCd;
var dmndCdList;
var rowIdx;

var view = {
		codeDatas : '',	
		codeMap : {"cntrctStatusCd":"013", "dmndCd":"016"},	//매출계약코드, 매출청구코드
		onLoadEvent : function() {
		
			view.selectCommonCodes();
			
			
		$("#btnSearch").unbind('click');
		$("#btnSearch").click( function() {	
			var table = $('#dataTables-salesCntrctList').dataTable();
			table.fnReloadAjax();
			
			view.initDetail();
		});

		$("#btnSave").unbind('click');
		$("#btnSave").click( function() {
			
			if($("#prjtCd").val()=='') {
				alert("프로젝트를 선택하세요.");
				return;
			}
			
			if($("#prjtEndYn").val() == "Y") {
				alert("종료된 프로젝트는 저장할 수 없습니다.");
				return;
			}
			
			if($("#srvcCostSpplyAmt").val()=='') {
				alert("공급가액 입력하세요.");
				$("#srvcCostSpplyAmt").focus();
				return;
			}
			
			if($("#cntrctStatusCd").val() == "") {
				alert("계약상태를 선택하세요.");
				$("#cntrctStatusCd").focus();
				return;
			}
			
			if($("#examNeedYn").val() == "") {
				alert("검수필요여부를 선택하세요.");
				$("#examNeedYn").focus();
				return;
			}
			
			if (confirm("저장 하시겠습니까?") == false){
				  return;
			}

			view.insertData();	
			
		});
		
		
		$( "#srvcCostSpplyAmt" ).blur(function() {
			if($("#srvcCostSpplyAmt").val() == "")
				return;
			
			$("#srvcCostTaxAmt").val($("#srvcCostSpplyAmt").val()*0.1)
			
		});
		
		$( "#examNeedYn" ).change(function() {
			fnCtrlExamCfrmYn();
		});
		
		$("#btnPrjtPop").unbind('click');
		$("#btnPrjtPop").click( function() {
			if($("#prjtCd").val() == "") {
				alert("프로젝트를 선택하세요");
				return;
			}
			window.open(G_CONTEXT_PATH+"/web/prjtmgt/prjtpop?prjtCd="+$("#prjtCd").val()+"&trAcctCd="+$("#trAcctCd").val(), "window", "width=1200,height=800");
			
		});
		
		$("#btnRowAdd").click( function() {
			var $obj = $("input[name='choice']");
			var checkCount = $obj.size();
			if(checkCount == 0 || checkCount == "undefind") {
				$("#dataTables-salesDmndList tr:not(:first)").remove();
			}
			
			var html = "";
			html += '<tr><td><input type="radio" name="choice" id="choice" class="form-control" style="width:100%;" value=""/></td>';
			html += '<td><input type="text" name="dmndDate" id="dmndDate" style="width:100%;" class="form-control" maxlength="8" numberonly="true" value=""/></td>';
			html +=  '<td><select name="dmndCd" id="dmndCd" class="form-control" style="width:100%;"><option value="">선택</option>';
        	for (var i=0; i<dmndCdList.length; i++) {
        		var val = dmndCdList[i];
        		 //console.log(dmndCdList[0][i]);
        		 html += '<option value="'+val[0]+'"';
		         html += ' >'+val[1]+'</option>';
        	}

        	
        	html += '</select></td>';
			html += '<td><input type="input" name="rmlrkDesc" id="rmlrkDesc" class="form-control" style="width:100%;" maxlength="100" value=""></td><td><input type="input" name="supplyAmt" id="supplyAmt" class="form-control" style="width:100%;" maxlength="9" value="" numberonly="true" onBlur="javascript:fnCalTaxAmt();"></td>';
			html += '<td><input type="input" name="taxAmt" id="taxAmt" class="form-control" style="width:100%;" maxlength="9" value="" numberonly="true" onBlur="javascript:fnCalTotAmt();"></td><td><input type="input" name="totAmt" id="totAmt" class="form-control" style="width:100%;" maxlength="9" readonly="readonly" value=""></td>';
			
			html += '<td><select name="examCfrmYn" id="examCfrmYn" class="form-control" style="width:100%;"><option value="">선택</option>';
        	html += '<option value="Y"';
        	html += ' >Y</option>';
        	html += '<option value="N"';
        	html += ' >N</option>';
        	html += '</select></td>';
        	html += '<td><input type="input" name="dpstYn" id="dpstYn" class="form-control" style="width:100%;" maxlength="1" readonly="readonly" value="N"></td>';
			html += '<td><input type="input" name="memoDesc" id="memoDesc" class="form-control" style="width:100%;" maxlength="500" value=""></td></tr>';
			
			//alert(html);
			$('#dataTables-salesDmndList > tbody:last').append(html);
		});
		
		$("#btnRowDel").click( function() {
			//$('#dataTables-tracctChgrList > tbody:last > tr:last').remove();   
			var $obj = $("input[name='choice']");
			var checkCount = $obj.size();
			for (var i=0; i<checkCount; i++){
				if($("input:radio[id='choice']").eq(i).is(":checked") == true && $("#f input[name='dpstYn']").eq(i).val() == "Y") {
					alert("청구처리된 건은 삭제할 수 없습니다.");
					return;
				}
				if($obj.eq(i).is(":checked")){
					$obj.eq(i).parent().parent().remove();
				}
			}
		});
		
		
		
		
		}
		, onLoadForAsync : function() {
			
			view.selectTableData();				
			view.selectSalesDmndList();
			
			//청구정보 목록 조회
	    	table1 = $('#dataTables-salesDmndList').dataTable();
	    	table1.fnReloadAjax();

			
		}
		
		, selectCommonCodes : function() {
			var reqData = new Object();
			var array = [];
			$.each(view.codeMap, function(k, v) {
				array.push(v);
			});
			reqData.codes=array.toString();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/multiCodes"
				  		, data : reqData
				  		, type : "GET"
						, success : view.selectCommonCodesCallBack
			});
		}
		, selectCommonCodesCallBack : function(json) {
			//codeMap : {"cntrctStatusCd":"013", "dmndCd":"016"},	//매출계약코드, 매출청구코드
			view.codeDatas=json;
			
			$.each(view.codeMap, function(key, value) {
				if(key=='cntrctStatusCd'){
					view.selectSrchCommonCodeCallBack013(view.codeDatas[value]);
					view.selectCommonCodeCallBack013(view.codeDatas[value]);
				}else if(key=='dmndCd'){
					view.selectCommonCodeCallBack016(view.codeDatas[value]);
				} 
			});
			view.onLoadForAsync();
		}
		
		, selectSrchCommonCodeCallBack013 : function(json) {
			var el = '';			
			$(json).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#srchCntrctStatusCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
				
		, selectCommonCodeCallBack013 : function(json) {
			var el = '';			
			$(json).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#cntrctStatusCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		, selectCommonCodeCallBack016 : function(json) {
			dmndCdList = new Array();
			$(json).each(function(i, itm){				
				var array = new Array();
				array.push(itm.dtlCd);
				array.push(itm.dtlCdNm);
				dmndCdList.push(array);
				console.log(itm.dtlCdNm);
			});
		}
		, selectTableData : function() {
			var table = $('#dataTables-salesCntrctList').DataTable(
					{
						"processing" : true,
						"serverSide" : true,
						"bFilter": false,
						"autoWidth": true,
						"ordering": false,
						"iDisplayLength": 10,
						// "scrollY":        "300px",
						
					  //      "scrollCollapse": true,
						"aoColumns": [
						        { data: 'trAcctNm',"sClass": "a-left"},
						        { data: 'prjtNm' },
						        { data: 'cstmrNm' },
						        { data: 'workStartDt',"sClass": "a-center" },
						        { data: 'workEndDt' ,"sClass": "a-center"},
						        { data: 'cntrctStatusNm' },
						        { data: 'chgrNm' },
						        { data: 'hpNo' }
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/salesCntrctMgt",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							aoData.push({
								"name" : "srchPrjtNm",
								"value" :  $("#srchPrjtNm").val()
							},
							{
								"name" : "srchTrAcctNm",
								"value" :  $("#srchTrAcctNm").val()
							},
							{
								"name" : "srchCstmrNm",
								"value" :  $("#srchCstmrNm").val()
							},
							{
								"name" : "srchChgrNm",
								"value" :  $("#srchChgrNm").val()
							},
							{
								"name" : "srchCntrctStatusCd",
								"value" :  $("#srchCntrctStatusCd").val()
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
			
			$('#dataTables-salesCntrctList tbody').on('click', 'tr', function () {
				
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				
		        var data = table.row( this ).data();
		        //console.log(data);
		        //alert( 'You clicked on '+data.prjtCd+'\'s row' );
		        view.initDetail();
		        
		        //프로젝트 종료 여부
		        $("#prjtEndYn").val(data.prjtEndYn);
		        
		        //계약 상세 조회
		        view.selectOneData(data.prjtCd);
		        
		        
		        
		        //청구정보 목록 조회
	        	table1 = $('#dataTables-salesDmndList').dataTable();
	        	table1.fnReloadAjax();

				
		    } );
			
		}
		, selectOneData : function(prjtCd){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/salesCntrctMgt/"+prjtCd
				  		, type : "GET"
						, data  : reqData
						, success : view.selectOneDataCallBack
			});
			
		}
		, selectOneDataCallBack : function(json){
			$(json.detail).each(function(idx, itm) {
				$("#prjtCd").val(itm.prjtCd);
				$("#trAcctCd").val(itm.trAcctCd);
				$("#cntrctCd").val(itm.cntrctCd);
				
				$("#prjtNm").val(itm.prjtNm);
				$("#trAcctNm").val(itm.trAcctNm);
				$("#cstmrNm").val(itm.cstmrNm);			
				$("#bizRgtNo").val(itm.bizRgtNo);
				//$("#srvcCostTotAmt").val(itm.srvcCostTotAmt);
				$("#srvcCostSpplyAmt").val(itm.srvcCostSpplyAmt);
				$("#srvcCostTaxAmt").val(itm.srvcCostTaxAmt);
				$("#workStartDt").val(itm.workStartDt);
				$("#workEndDt").val(itm.workEndDt);
				$("#cntrctStatusCd").val(itm.cntrctStatusCd);
				$("#examNeedYn").val(itm.examNeedYn);
				$("#memoDesc0").val(itm.memoDesc);
				
				$("#prjtCfrmDt").val(itm.prjtCfrmDt);
				$("#prjtEndDt").val(itm.prjtEndDt);
				
				
			});
			
			$(json.detail2).each(function(idx, itm) {
				
				$("#srvcCostTotAmt").val(itm.salesTotAmt);
				
			});
		}
		
		, selectSalesDmndList : function() {
			var table = $('#dataTables-salesDmndList').DataTable(
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
						        { data: '' , "render": function ( data ) { return '<input type="radio" name="choice" id="choice" style="width:100%;" class="form-control" value="">';} },
						        { data: 'dmndDate' , "render": function ( data ) { return '<input type="text" name="dmndDate" id="dmndDate" class="form-control" style="width:100%;" maxlength="8" numberonly="true" value="'+data+'">';} },
						        { data: 'dmndCd' , "render": function ( data,  code) {
						        	//console.log(dmndCdList);
						        	var html =  '<select name="dmndCd" id="dmndCd" class="form-control"><option value="">선택</option>';
						        	for (var i=0; i<dmndCdList.length; i++) {
						        		var val = dmndCdList[i];
						        		 //console.log(dmndCdList[0][i]);
						        		 html += '<option value="'+val[0]+'"';
								         html += data == val[0] ? "selected=\"selected\"" : "\"\"";
								         html += ' >'+val[1]+'</option>';
						        	}
						        	
						        	html += '</select>';
						        	
						        	return html;
						        } },
						        { data: 'rmlrkDesc' , "render": function ( data ) { return '<input type="input" name="rmlrkDesc" id="rmlrkDesc" class="form-control" maxlength="100%" value="'+data+'">';} },
						        { data: 'supplyAmt' , "render": function ( data ) { return '<input type="input" name="supplyAmt" id="supplyAmt" class="form-control" style="width:100%;"maxlength="9" value="'+data+'" numberonly="true" onBlur="javascript:fnCalTaxAmt();">';} },					   
						        { data: 'taxAmt' , "render": function ( data ) { return '<input type="input" name="taxAmt" id="taxAmt" class="form-control" style="width:100%;" maxlength="9" value="'+data+'" numberonly="true" onBlur="javascript:fnCalTotAmt();">';} },
						        { data: 'totAmt' , "render": function ( data ) { return '<input type="input" name="totAmt" id="totAmt" class="form-control" style="width:100%;" maxlength="9" readonly="readonly" value="'+data+'">';} },
						        { data: 'examCfrmYn' , "render": function ( data ) {
						        	var html = '<select name="examCfrmYn" id="examCfrmYn" class="form-control" style="width:100%;"><option value="">선택</option>';
						        	html += '<option value="Y"';
						        	html += data == "Y" ? "selected=\"selected\"" : "\"\"";
						        	html += ' >Y</option>';
						        	html += '<option value="N"';
						        	html += data == "N" ? "selected=\"selected\"" : "\"\"";
						        	html += ' >N</option>';
						        	html += '</select>';
						        	
						        	return html;
						        } },
						        { data: 'dpstYn' , "render": function ( data ) { return '<input type="input" name="dpstYn" id="dpstYn" class="form-control" maxlength="1" readonly="readonly" style="width:100%;" value="'+data+'">';} },
						        { data: 'memoDesc' , "render": function ( data ) { return '<input type="input" name="memoDesc" id="memoDesc" class="form-control" maxlength="1000" style="width:100%;" title="'+data+'" value="'+data+'">';} }
						        
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/salesCntrctMgt/selectSalesDmndList",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							aoData.push({
								"name" : "prjtCd",
								"value" :  prjtCd
							},
							{
								"name" : "cntrctCd",
								"value" :  $("#cntrctCd").val()
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
			
			$('#dataTables-salesDmndList tbody').on('click', 'tr', function () {
				
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				
				rowIdx = $(this).index();
				
				fnCtrlExamCfrmYn();
			      
				//청구처리건 비활성화 처리
				if($("#f input[name='dpstYn']").eq(rowIdx).val() == "Y") {
					alert("청구처리 건은 수정/삭제할 수 없습니다.");

					$("input[name=choice]").eq(rowIdx).attr("disabled", true);  
					$("#f input[name='dmndDate']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f select[name='dmndCd']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='rmlrkDesc']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='supplyAmt']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='taxAmt']").eq(rowIdx).attr('disabled', 'disabled');	
					$("#f input[name='totAmt']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f select[name='examCfrmYn']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='dpstYn']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='memoDesc']").eq(rowIdx).attr('disabled', 'disabled');
				}
		    } );
		}
		
		, insertData : function() {
			
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/salesCntrctMgt/insertSalesCntrctMgt"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
			
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {
				alert("등록되었습니다.");
				var table = $('#dataTables-salesCntrctList').dataTable();
				table.fnReloadAjax();
				
				view.initDetail();
				
				//청구정보 목록 조회
		    	table1 = $('#dataTables-salesDmndList').dataTable();
		    	table1.fnReloadAjax();
			}
			else {
				alert(json.msg);
			}	
		}	

		, initDetail : function() {
			$("#prjtCd").val('');
			$("#trAcctCd").val('');
			$("#cntrctCd").val('');
			$("#prjtNm").val('');
			$("#trAcctNm").val('');
			$("#cstmrNm").val('');			
			$("#bizRgtNo").val('');
			$("#srvcCostTotAmt").val('');
			$("#srvcCostSpplyAmt").val('');
			$("#srvcCostTaxAmt").val('');
			$("#workStartDt").val('');
			$("#workEndDt").val('');
			$("#cntrctStatusCd").val('');
			$("#examNeedYn").val('');
			$("#memoDesc0").val('');
	        $("#prjtEndYn").val('');
		}
		, initTrAcctChgrList : function() {
			
		}
	};

	$(function() {
		view.onLoadEvent();
		
		$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});
		$(document).on("keyup", "input:text[datetimeOnly]", function() {$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );});
	});
	
	function fnCalTaxAmt() {
		var $obj = $("input[name='choice']");
		var checkCount = $obj.size();
		var vAmt = 0;
		var tAmt = 0;
		
		if(checkCount == 1) {
			vAmt = $("#f input[name='supplyAmt']").val();
			tAmt = $("#f input[name='taxAmt']").val();

		} else if(checkCount > 1) {
			vAmt = $("#f input[name='supplyAmt']").eq(rowIdx).val();
			tAmt = $("#f input[name='taxAmt']").eq(rowIdx).val();
		}
		
		if(checkCount == 1) {
	    	$("#f input[name='taxAmt']").val(vAmt*0.1);
	    	$("#f input[name='totAmt']").val(Number(vAmt*0.1) + Number(vAmt));
	    } 
	    else if(checkCount > 1) {
	    	$("#f input[name='taxAmt']").eq(rowIdx).val(vAmt*0.1);
	    	$("#f input[name='totAmt']").eq(rowIdx).val(Number(vAmt*0.1) + Number(vAmt));
	    }
	}
	
	function fnCalTotAmt() {
		var $obj = $("input[name='choice']");
		var checkCount = $obj.size();
		var vAmt = 0;
		var tAmt = 0;
		
		if(checkCount == 1) {
			vAmt = $("#f input[name='supplyAmt']").val();
			tAmt = $("#f input[name='taxAmt']").val();

		} else if(checkCount > 1) {
			vAmt = $("#f input[name='supplyAmt']").eq(rowIdx).val();
			tAmt = $("#f input[name='taxAmt']").eq(rowIdx).val();
		}
		
		if(checkCount == 1) {
	    	//$("#f input[name='taxAmt']").val(vAmt*0.1);
	    	$("#f input[name='totAmt']").val(Number(tAmt) + Number(vAmt));
	    } 
	    else if(checkCount > 1) {
	    	//$("#f input[name='taxAmt']").eq(rowIdx).val(vAmt*0.1);
	    	$("#f input[name='totAmt']").eq(rowIdx).val(Number(tAmt) + Number(vAmt));
	    }
	}
	
	function fnCtrlExamCfrmYn() {
		var $obj = $("input[name='choice']");
		var checkCount = $obj.size();
		
		var examNeedYn = $("#examNeedYn").val();
		
		//alert(examNeedYn);

		for(var i=0; i<checkCount; i++) {
			if(examNeedYn == "Y") {
				$("#f select[name='examCfrmYn']").eq(i).removeAttr("disabled");
				//$("#f select[name='examCfrmYn']").eq(i).val('Y');
			} else {
				$("#f select[name='examCfrmYn']").eq(i).attr('disabled', 'disabled');
				$("#f select[name='examCfrmYn']").eq(i).val('N');
			}
		}
	}