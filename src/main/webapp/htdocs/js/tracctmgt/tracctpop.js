var trAcctCd;
var chgrSectCdList;

var view = {		
		onLoadEvent : function() {

		$("#btnSearch").unbind('click');
		$("#btnSearch").click( function() {
			view.initDetail();

			var table = $('#dataTables-tracctList').dataTable();
			table.fnReloadAjax();
		});
		
		$("#btnConfirm").unbind('click');
		$("#btnConfirm").click( function() {
			if($("#trAcctCd").val() == "" || $("#trAcctNm").val() == "") {
				alert("거래처선택하세요");
				return;
			}
			
			if($("#srchType").val() == "T") {
				$(opener.document).find("#trAcctCd").val($("#trAcctCd").val());
				$(opener.document).find("#trAcctNm").val($("#trAcctNm").val());
			}
			
			if($("#srchType").val() == "S") {
				$(opener.document).find("#chgSeqNo1").val($("#hdnChgSeqNo").val()); //번호
				$(opener.document).find("#chgrNm1").val($("#hdnChgrNm").val()); //담당자명
				$(opener.document).find("#pstnNm1").val($("#hdnPstnNm").val()); //직위
				$(opener.document).find("#telNo1").val($("#hdnTelNo").val()); //연락처
				$(opener.document).find("#hpNo1").val($("#hdnHpNo").val()); //휴대폰번호
				$(opener.document).find("#emailAddr1").val($("#hdnEmailAddr").val()); //이메일
			}
			
			if($("#srchType").val() == "C") {
				$(opener.document).find("#chgSeqNo2").val($("#hdnChgSeqNo").val()); //번호
				$(opener.document).find("#chgrNm2").val($("#hdnChgrNm").val()); //담당자명
				$(opener.document).find("#pstnNm2").val($("#hdnPstnNm").val()); //직위
				$(opener.document).find("#telNo2").val($("#hdnTelNo").val()); //연락처
				$(opener.document).find("#hpNo2").val($("#hdnHpNo").val()); //휴대폰번호
				$(opener.document).find("#emailAddr2").val($("#hdnEmailAddr").val()); //이메일
			}
			
			self.close();
			
			
		});
		
		$("#btnClose").unbind('click');
		$("#btnClose").click( function() {
			self.close();
			
		});
		
		view.selectTableData();
		view.selectTracctChgrList();
		view.selectCommonCode001();
		

		
		}
		
		, selectCommonCode001 : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/001"
				  		, type : "GET"
						, success : view.selectCommonCodeCallBack001
			});
		}
		, selectCommonCodeCallBack001 : function(json) {
			var el = '';			
			$(json.list).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#srchChgSectCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');
			
			if($("#srchType").val() == "S") {
				$("#srchChgSectCd").val("002");
				
			}

			if($("#srchType").val() == "C") {
				$("#srchChgSectCd").val("004");
			}
			
			if($("#srchType").val() == "T") {
				$("#srchChgSectCd").attr("disabled", false);
			} else {
				$("#srchChgSectCd").attr("disabled", true);
			}
			
			
		}
		, selectTableData : function() {
			
			if($("#srchType").val() == "T") {
				$("#trAcctCd").val("");
				trAcctCd = "";
			} else {
				trAcctCd = $("#trAcctCd").val();
			}
			
			//alert($("#srchType").val());
			//alert($("#trAcctCd").val());
			
			var table = $('#dataTables-tracctList').DataTable(
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
						        { data: 'ceoNm' },
						        { data: 'bizRgtNo', "sClass": "a-center"},
						        { data: 'chgSectNm' },
						        { data: 'chgrNm' },
						        { data: 'pstnNm' },
						        { data: 'telNo',"sClass": "a-center" },
						        { data: 'hpNo', "sClass": "a-center" },
						        { data: 'emailAddr' }
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/trAcctMgt",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							//$("#detail").hide();
							//$("#tracctChgrList").hide();
							aoData.push({
								"name" : "srchTrAcctNm",
								"value" :  $("#srchTrAcctNm").val()
							},
							{
								"name" : "srchChgSectCd",
								"value" :  $("#srchChgSectCd").val()
							},
							{
								"name" : "srchChgrNm",
								"value" :  $("#srchChgrNm").val()
							},
							{
								"name" : "trAcctCd",
								"value" :  trAcctCd
							},
							{
								"name" : "srchType",
								"value" :  $("#srchType").val()
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
			
			$('#dataTables-tracctList tbody').on('click', 'tr', function () {
				
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				
		        var data = table.row( this ).data();
		        //console.log(data);
		        //alert( 'You clicked on '+data.chgrSeqNo+'\'s row' );
		        
		        view.initDetail();
		        
		        //거래처 상세 조회
		        view.selectOneData(data.trAcctCd);
				
		        //담당자 목록 조회
	        	trAcctCd = data.trAcctCd;
	        	var table1 = $('#dataTables-tracctChgrList').dataTable();
	        	table1.fnReloadAjax();
	        	
	        	$("#hdnChgSeqNo").val(data.chgrSeqNo); //번호
				$("#hdnChgrNm").val(data.chgrNm); //담당자명
				$("#hdnPstnNm").val(data.pstnNm); //직위
				$("#hdnTelNo").val(data.telNo); //연락처
				$("#hdnHpNo").val(data.hpNo); //휴대폰번호
				$("#hdnEmailAddr").val(data.emailAddr); //이메일
				
		    } );
			
		}
		, selectOneData : function(trAcctCd){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/trAcctMgt/"+trAcctCd
				  		, type : "GET"
						, data  : reqData
						, success : view.selectOneDataCallBack
			});
			
		}
		, selectOneDataCallBack : function(json){
			$(json.detail).each(function(idx, itm) {
				$("#trAcctCd").val( itm.trAcctCd);
				$("#trAcctNm").val( itm.trAcctNm);
				$("#ceoNm").val( itm.ceoNm);
				$("#bizGrndAddr").val( itm.bizGrndAddr);
				$("#mnShpAddr").val( itm.mnShpAddr);
				$("#lawRgtNo1").val( itm.lawRgtNo1);
				$("#lawRgtNo2").val( itm.lawRgtNo2);
				$("#bizRgtNo1").val( itm.bizRgtNo1);
				$("#bizRgtNo2").val( itm.bizRgtNo2);
				$("#bizRgtNo3").val( itm.bizRgtNo3);
				$("#bizStatuNm").val( itm.bizStatuNm);
				$("#bizDtlNm").val( itm.bizDtlNm);
				$("#opnDt").val( itm.opnDt);
				$("#dpstExpctDayTxt").val( itm.dpstExpctDayTxt);
				$("#emoDesc").val( itm.emoDesc);
			});
			
			
			//$("#detail").show();
			//$("#tracctChgrList").show();
			
		}
		
		, selectTracctChgrList : function() {
			var table = $('#dataTables-tracctChgrList').DataTable(
					{
						"processing" : true,
						"serverSide" : true,
						"bFilter": false,
						"autoWidth": true,
						"ordering": false,
						"paging": false,						
						"deferLoading": 0,
						"iDisplayLength": 10,
						// "scrollY":        "300px",
						
					  //      "scrollCollapse": true,
						"aoColumns": [   
                                { data: 'chgSectNm'},
						        { data: 'chgrNm'},
						        { data: 'pstnNm'},
						        { data: 'telNo',"sClass": "a-center"},
						        { data: 'hpNo',"sClass": "a-center"},
						        { data: 'emailAddr'},
						        { data: 'addr'},
						        { data: 'memoDesc'}

						],
						"sAjaxSource" : G_CONTEXT_PATH+"/trAcctMgt/selectTrAcctChgrList",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							aoData.push({
								"name" : "trAcctCd",
								"value" :  trAcctCd
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
			
			$('#dataTables-tracctChgrList tbody').on('click', 'tr', function () {
				/**
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }**/
				
		   
		    } );
		}
		
		, initDetail : function() {
			if($("#srchType").val() == "T") {
				$("#trAcctCd").val("");
				trAcctCd = "";
			} else {
				trAcctCd = $("#trAcctCd").val();
			}
			
			//trAcctCd = "";
			//$("#trAcctCd").val(''); //거래처 코드
			$("#trAcctNm").val(''); //거래처 명
			$("#ceoNm").val(''); //대표자명
			$("#bizGrndAddr").val(''); //사업장주소
			$("#mnShpAddr").val(''); //본점주소주소
			$("#lawRgtNo1").val(''); //법인번호1
			$("#lawRgtNo2").val(''); //법인번호2
			$("#bizRgtNo1").val(''); //사업번호1
			$("#bizRgtNo2").val(''); //사업번호2
			$("#bizRgtNo3").val(''); //사업번호3
			
			$("#bizStatuNm").val(''); //업태
			$("#bizDtlNm").val(''); //종목
			$("#opnDt").val(''); //개업년월일
			$("#dpstExpctDayDesc").val(''); //입금예정일
			$("#emoDesc").val(''); //메모
			
		}
		
	};

	$(function() {
		view.onLoadEvent();
	});