var trAcctCd;
var dpstCdList;
var rowIdx;

var view = {		
		onLoadEvent : function() {
		
		$("#btnSearch").unbind('click');
		$("#btnSearch").click( function() {	
			var table = $('#dataTables-orderCntrctList').dataTable();
			table.fnReloadAjax();
			
			view.initDetail();
			
			//청구정보 목록 조회
	    	table1 = $('#dataTables-orderDpstList').dataTable();
	    	table1.fnReloadAjax();
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
			
			
			if($("#cntrctStatusCd").val() == "") {
				alert("계약상태를 선택하세요.");
				$("#cntrctStatusCd").focus();
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
				$("#dataTables-orderDpstList tr:not(:first)").remove();
			}
			
			var html = "";
			html += '<tr><td><input type="radio" name="choice" id="choice" class="form-control" style="width:100%;" value=""/></td>';
			html += '<td><input type="text" name="dpstDt" id="dpstDt" style="width:100%;" class="form-control" maxlength="8" numberonly="true" value=""/></td>';
			html +=  '<td><select name="dpstCd" id="dpstCd" class="form-control" style="width:100%;"><option value="">선택</option>';
        	for (var i=0; i<dpstCdList.length; i++) {
        		var val = dpstCdList[i];
        		 //console.log(dpstCdList[0][i]);
        		 html += '<option value="'+val[0]+'"';
		         html += ' >'+val[1]+'</option>';
        	}

        	
        	html += '</select></td>';
			html += '<td><input type="input" name="rmlrkDesc" id="rmlrkDesc" class="form-control" style="width:100%;" maxlength="100" value=""></td><td><input type="input" name="supplyAmt" id="supplyAmt" class="form-control" style="width:100%;" maxlength="9" value="" numberonly="true" onBlur="javascript:fnCalTaxAmt();"></td>';
			html += '<td><input type="input" name="taxAmt" id="taxAmt" class="form-control" style="width:100%;" maxlength="9" value="" numberonly="true" onBlur="javascript:fnCalTotAmt();"></td><td><input type="input" name="totAmt" id="totAmt" class="form-control" style="width:100%;" maxlength="9" readonly="readonly" value=""></td>';
			html += '<td><input type="input" name="dpstYn" id="dpstYn" class="form-control" style="width:100%;" maxlength="1" readonly="readonly" value="N"></td>';
			html += '<td><input type="input" name="memoDesc" id="memoDesc" class="form-control" style="width:100%;" maxlength="500" value=""></td></tr>';
			
			//alert(html);
			$('#dataTables-orderDpstList > tbody:last').append(html);
		});
		
		$("#btnRowDel").click( function() {
			//$('#dataTables-tracctChgrList > tbody:last > tr:last').remove();   
			var $obj = $("input[name='choice']");
			var checkCount = $obj.size();
			for (var i=0; i<checkCount; i++){
				if($("input:radio[id='choice']").eq(i).is(":checked") == true && $("#f input[name='dpstYn']").eq(i).val() == "Y") {
					alert("지급처리된 건은 삭제할 수 없습니다.");
					return;
				}
				if($obj.eq(i).is(":checked")){
					$obj.eq(i).parent().parent().remove();
				}
			}
		});
		
		
		view.selectTableData();
		
		//검색조건 계약구분코드
		view.selectSrchCommonCode004();
		
		//검색조건 계약상태코드
		view.selectSrchCommonCode014();
		
		//검색조건 구분코드
		view.selectSrchCommonCode011();
		
		//검색조건 지급정보구분코드 
		view.selectSrchCommonCode017();
		
		//지급정보구분코드
		view.selectCommonCode017();
		
		//상세조회 계약상태코드
		view.selectCommonCode014();
		view.selectOrderDpstList();
		
		//청구정보 목록 조회
    	table1 = $('#dataTables-orderDpstList').dataTable();
    	table1.fnReloadAjax();

		
		}

		, selectSrchCommonCode004 : function() {
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/004"
				  		, type : "GET"
						, success : view.selectSrchCommonCodeCallBack004
			});
		}
		, selectSrchCommonCodeCallBack004 : function(json) {
			var el = '';			
			$(json.list).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#srchCntrctSectCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		
		, selectSrchCommonCode014 : function() {
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/014"
				  		, type : "GET"
						, success : view.selectSrchCommonCodeCallBack014
			});
		}
		, selectSrchCommonCodeCallBack014 : function(json) {
			var el = '';			
			$(json.list).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#srchCntrctStatusCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		, selectSrchCommonCode011 : function() {
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/011"
				  		, type : "GET"
						, success : view.selectSrchCommonCodeCallBack011
			});
		}
		, selectSrchCommonCodeCallBack011 : function(json) {
			var el = '';			
			$(json.list).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#srchDstrbtSectCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		, selectSrchCommonCode017 : function() {
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/017"
				  		, type : "GET"
						, success : view.selectSrchCommonCodeCallBack017
			});
		}
		, selectSrchCommonCodeCallBack017 : function(json) {
			var el = '';			
			$(json.list).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#srchDsptCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		, selectCommonCode014 : function() {
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/014"
				  		, type : "GET"
						, success : view.selectCommonCodeCallBack014
			});
		}
		
		, selectCommonCodeCallBack014 : function(json) {
			var el = '';			
			$(json.list).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#cntrctStatusCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		, selectCommonCode017 : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/017"
				  		, type : "GET"
						, success : view.selectCommonCodeCallBack017
			});
		}
		, selectCommonCodeCallBack017 : function(json) {
			dpstCdList = new Array();
			$(json.list).each(function(i, itm){				
				var array = new Array();
				array.push(itm.dtlCd);
				array.push(itm.dtlCdNm);
				dpstCdList.push(array);
				//console.log(itm.dtlCdNm);
			});
		}
		, selectTableData : function() {
			var table = $('#dataTables-orderCntrctList').DataTable(
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
						        { data: 'chgrNm' },
						        { data: 'chgrHpNo',"sClass": "a-center" },
						        { data: 'cntrctStatusCdNm' ,"sClass": "a-center"},
						        { data: 'cntrctSectCdNm' ,"sClass": "a-center"},
						        { data: 'prsnNm' },
						        { data: 'hpNo',"sClass": "a-center" },
						        { data: 'dstrbtSectCdNm' },
						        { data: 'workStartDt',"sClass": "a-center" },
						        { data: 'workEndDt',"sClass": "a-center" }
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/orderCntrctMgt",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							aoData.push({
								"name" : "srchPrjtNm",
								"value" :  $("#srchPrjtNm").val()
							},
							{
								"name" : "srchCntrctSectCd",
								"value" :  $("#srchCntrctSectCd").val()
							},
							{
								"name" : "srchPrsnNm",
								"value" :  $("#srchPrsnNm").val()
							},
							{
								"name" : "srchCntrctStatusCd",
								"value" :  $("#srchCntrctStatusCd").val()
							},
							{
								"name" : "srchDstrbtSectCd",
								"value" :  $("#srchDstrbtSectCd").val()
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
			
			$('#dataTables-orderCntrctList tbody').on('click', 'tr', function () {
				
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				
		        var data = table.row( this ).data();
		        //console.log(data);
		        //alert( 'You clicked on '+data.cntrctCd+'\'s row' );
		        view.initDetail();
		        
		        //프로젝트 종료 여부
		        $("#prjtEndYn").val(data.prjtEndYn);
		        
		        //계약 상세 조회
		        view.selectOneData(data.prjtCd, data.dstrbtSeqNo);
		     
		        //청구정보 목록 조회
	        	table1 = $('#dataTables-orderDpstList').dataTable();
	        	table1.fnReloadAjax();

				
		    } );
			
		}
		, selectOneData : function(prjtCd, dstrbtSeqNo){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/orderCntrctMgt/"+prjtCd+"?dstrbtSeqNo="+dstrbtSeqNo
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
				$("#prsnNo").val(itm.prsnNo);
				
				
				$("#prjtNm").val(itm.prjtNm);
				$("#prsnNm").val(itm.prsnNm);
				$("#cntrctSectCdNm").val(itm.cntrctSectCdNm);			
				
				$("#juminNo").val(itm.juminNo);
				$("#workStartDt").val(itm.workStartDt);
				$("#workEndDt").val(itm.workEndDt);
				$("#prsnMm").val(itm.prsnMm);
				$("#ordrUnitCstAmt").val(itm.ordrUnitCstAmt);
				$("#cntrctStatusCd").val(itm.cntrctStatusCd);
				$("#memoDesc0").val(itm.memoDesc);
				
			});
		}
		
		, selectOrderDpstList : function() {
			
			var table = $('#dataTables-orderDpstList').DataTable(
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
						        { data: 'dpstDt' , "render": function ( data ) { return '<input type="text" name="dpstDt" id="dpstDt" class="form-control" style="width:100%;" maxlength="8" numberonly="true" value="'+data+'">';} },
						        { data: 'dsptCd' , "render": function ( data,  code) {
						        	//console.log(dpstCdList);
						        	var html =  '<select name="dpstCd" id="dpstCd" class="form-control"><option value="">선택</option>';
						        	for (var i=0; i<dpstCdList.length; i++) {
						        		var val = dpstCdList[i];
						        		 //console.log(dpstCdList[0][i]);
						        		 html += '<option value="'+val[0]+'"';
								         html += data == val[0] ? "selected=\"selected\"" : "\"\"";
								         html += ' >'+val[1]+'</option>';
						        	}
						        	
						        	html += '</select>';
						        	
						        	return html;
						        } },
						        { data: 'rmlrkDesc' , "render": function ( data ) { return '<input type="input" name="rmlrkDesc" id="rmlrkDesc" class="form-control" maxlength="100%" value="'+data+'">';} },
						        { data: 'supplyAmt' , "render": function ( data ) { return '<input type="input" name="supplyAmt" id="supplyAmt" class="form-control" style="width:100%;"maxlength="9" numberonly="true" value="'+data+'" onBlur="javascript:fnCalTaxAmt();">';} },					   
						        { data: 'taxAmt' , "render": function ( data ) { return '<input type="input" name="taxAmt" id="taxAmt" class="form-control" style="width:100%;" maxlength="9" numberonly="true" value="'+data+'" onBlur="javascript:fnCalTotAmt();">';} },
						        { data: 'totAmt' , "render": function ( data ) { return '<input type="input" name="totAmt" id="totAmt" class="form-control" style="width:100%;" maxlength="9" readonly="readonly" value="'+data+'">';} },
						        { data: 'dpstYn' , "render": function ( data ) { return '<input type="input" name="dpstYn" id="dpstYn" class="form-control" style="width:100%;" maxlength="9" readonly="readonly" value="'+data+'">';} },
						        { data: 'memoDesc' , "render": function ( data ) { return '<input type="input" name="memoDesc" id="memoDesc" class="form-control" maxlength="1000" style="width:100%;" title="'+data+'" value="'+data+'">';} },
						        
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/orderCntrctMgt/selectOrderDpstList",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							aoData.push(
							{
								"name" : "cntrctCd",
								"value" :  $("#cntrctCd").val()
							},
							{
								"name" : "srchDsptCd",
								"value" :  $("#srchDsptCd").val()
							});
							oSettings.jqXHR = $.ajax({
								"dataType" : 'json',
								"type" : "POST",
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
			
			$('#dataTables-orderDpstList tbody').on('click', 'tr', function () {
				
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				
				rowIdx = $(this).index();
				
				
				//지급처리건 비활성화 처리
				if($("#f input[name='dpstYn']").eq(rowIdx).val() == "Y") {
					alert("지급처리 건은 수정/삭제할 수 없습니다.");
					
					//$("#f input[name='choice']").eq(rowIdx).attr("disabled", true);
					$("input[name=choice]").eq(rowIdx).attr("disabled", true);  
					$("#f input[name='dpstDt']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f select[name='dpstCd']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='rmlrkDesc']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='supplyAmt']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='taxAmt']").eq(rowIdx).attr('disabled', 'disabled');	
					$("#f input[name='totAmt']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='dpstYn']").eq(rowIdx).attr('disabled', 'disabled');
					$("#f input[name='memoDesc']").eq(rowIdx).attr('disabled', 'disabled');
				}
			    
		    } );
		}
		
		, insertData : function() {
			
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/orderCntrctMgt/insertOrderCntrctMgt"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
			
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {
				alert("등록되었습니다.");
				var table = $('#dataTables-orderCntrctList').dataTable();
				table.fnReloadAjax();
				
				view.initDetail();
				
				//지급정보 목록 조회
		    	table1 = $('#dataTables-orderDpstList').dataTable();
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
			$("#prsnNo").val('');
			
			
			$("#prjtNm").val('');
			$("#prsnNm").val('');
			$("#cntrctSectCdNm").val('');			
			
			$("#juminNo").val('');
			$("#workStartDt").val('');
			$("#workEndDt").val('');
			$("#prsnMm").val('');
			$("#ordrUnitCstAmt").val('');
			$("#cntrctStatusCd").val('');
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
		
		if($("#f select[name='dpstCd']").eq(rowIdx).val() != "001")
			return;
		
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
		
		if($("#f select[name='dpstCd']").eq(rowIdx).val() != "001") 
			return;
		
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
	