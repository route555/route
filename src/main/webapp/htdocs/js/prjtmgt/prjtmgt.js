var trAcctCd;
var chgrSectCdList;

var view = {		
		codeDatas : '',	
		codeMap : {"abrdCd":"005","prjtPrptyCd":"012","prjtCondCd":"022"},	//국내외구분코드, 프로젝트특성코드, 프로젝트상태코드
		onLoadEvent : function() {
		
			$('#searchBox input').keypress(function(e) {
			    if (e.keyCode == 13){			    	
			    	$("#btnSearch").click();
					return false;
			    }         
			});	
			
			$("#btnSearch").unbind('click');
			$("#btnSearch").click( function() {	
				var table = $('#dataTables-prjtList').dataTable();
				table.fnReloadAjax();
				view.initDetail();
			});
			
			$("#btnSearchInit").unbind('click');
			$("#btnSearchInit").click( function() {			
				$('form[name="sf"]').each(function() {
					this.reset();  
				}); 
			});

		$("#btnSave").unbind('click');
		$("#btnSave").click( function() {
			
			if($("#prjtNm").val()=='') {
				alert("프로젝트명을 입력하세요.");
				$("#prjtNm").focus();
				return;
			}
			
			if($("#trAcctNm").val()=='') {
				alert("거래처명을 입력하세요.");
				$("#trAcctNm").focus();
				return;
			}
			
			if($("#prjtEndYn").val() == "Y") {
				alert("종료된 프로젝트는 수정할 수 없습니다.");
				return;
			}
			
			if (confirm("저장 하시겠습니까?") == false){
				  return;
			}

			if($("#prjtCd").val()==''){
				view.insertData();	
			}else{
				view.modifyData();
			}
			
		});
		
		$("#btnTrAcctPop").unbind('click');
		$("#btnTrAcctPop").click( function() {

			window.open(G_CONTEXT_PATH+"/web/tracctmgt/tracctpop?trAcctCd=''&srchType=T&srchChgSectCd=", "window", "width=1200,height=800");
			
			
			
		});
		
		$("#btnSalesChgr").unbind('click');
		$("#btnSalesChgr").click( function() {
			if($("#trAcctCd").val() == "") {
				alert("거래처명을 입력하세요.");
				$("#trAcctCd").focus();
				return;
			}
			
			window.open(G_CONTEXT_PATH+"/web/tracctmgt/tracctpop?trAcctCd="+$("#trAcctCd").val()+"&srchType=S&srchChgSectCd=002", "window", "width=1200,height=800");
			
			
			
		});
		
		$("#btnCalChgr").unbind('click');
		$("#btnCalChgr").click( function() {
			if($("#trAcctCd").val() == "") {
				alert("거래처명을 입력하세요.");
				$("#trAcctCd").focus();
				return;
			}
			
			window.open(G_CONTEXT_PATH+"/web/tracctmgt/tracctpop?trAcctCd="+$("#trAcctCd").val()+"&srchType=C&srchChgSectCd=004", "window", "width=1200,height=800");
			
			
			
		});
		
		$("#btnDelete").unbind('click');
		$("#btnDelete").click( function() {	
			if($("#prjtCd").val() == "") {
				alert("삭제할 행을 선택하세요.");
				return;
			}
			
			if($("#prjtCfrmYn").val() == "Y") {
				alert("확정된 프로젝트는 삭제할 수 없습니다.");
				return;
			}
			
			if($("#prjtEndYn").val() == "Y") {
				alert("종료된 프로젝트는 삭제할 수 없습니다.");
				return;
			}
			
			if (confirm("프로젝트정보 및 배정인력을 삭제 하시겠습니까?") == false){
				  return;
			}
			view.deleteData();
		});
		
		$("#btnNew").unbind('click');
		$("#btnNew").click( function() {			
			view.initDetail();
		});
		
		
		$("#btnPrjtCfrm").unbind('click');
		$("#btnPrjtCfrm").click( function() {
			if($("#prjtCd").val() == "") {
				alert("확정할 행을 선택하세요.");
				return;
					
			}
			
			if($("#prjtCfrmYn").val() == "Y") {
				alert("이미 확정되었습니다.");
				return;
			}
			
			if (confirm("확정 하시겠습니까?") == false){
				  return;
			}
			
			view.updatePrjtCfrm();

			
		});
		
		$("#btnPrjtCfrmCanCel").unbind('click');
		$("#btnPrjtCfrmCanCel").click( function() {
			if($("#prjtCd").val() == "") {
				alert("확정취소할 행을 선택하세요.");
				return;
					
			}
			
			if($("#prjtCfrmYn").val() == "N" || $("#prjtCfrmYn").val() == "") {
				alert("이미 확정취소되었습니다.");
				return;
			}
			
			if (confirm("확정취소 하시겠습니까?") == false){
				  return;
			}
			
			view.updatePrjtCfrmCancel();

			
		});
		
		$("#btnPrjtEnd").unbind('click');
		$("#btnPrjtEnd").click( function() {
			if($("#prjtCd").val() == "") {
				alert("종료할 행을 선택하세요.");
				return;
					
			}
			
			if($("#prjtEndYn").val() == "Y") {
				alert("이미 종료되었습니다.");
				return;
			}
			
			if($("#prjtCfrmYn").val() == "N" || $("#prjtCfrmYn").val() == "") {
				alert("확정되지 않아 종료할 수 없습니다.");
				return;
			}
			
			if (confirm("종료 하시겠습니까?") == false){
				  return;
			}
			
			view.updatePrjtEnd();

			
		});
		
		$("#btnPrjtEndCanCel").unbind('click');
		$("#btnPrjtEndCanCel").click( function() {
			if($("#prjtCd").val() == "") {
				alert("종료취소할 행을 선택하세요.");
				return;
					
			}
			
			if($("#prjtEndYn").val() == "N" || $("#prjtEndYn").val() == "") {
				alert("이미 종료취소되었습니다.");
				return;
			}
			
			if (confirm("종료취소 하시겠습니까?") == false){
				  return;
			}
			
			view.updatePrjtEndCancel();

			
		});
		
		$("#btnPrsnView").unbind('click');
		$("#btnPrsnView").click( function() {
			if($("#prjtCd").val() == "") {
				alert("인력배정할 행을 선택하세요.");
				return;
			}
			
			var url = G_CONTEXT_PATH+"/web/prjtprsnmgt/prjtprsnmgt?prjtCd="+$("#prjtCd").val()+"&trAcctCd="+$("#trAcctCd").val();
			$(location).attr('href', url);

			
		});
		
		
		view.selectCommonCodes();
		
		//view.selectTableData();
		
		//국내외구분코드
		//view.selectCommonCode005();
		
		//프로젝트특성코드
		//view.selectCommonCode012();

		
		}
		, onLoadForAsync : function() {
			view.selectTableData();
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
			//codeMap : {"abrdCd":"005","prjtPrptyCd":"012","prjtCondCd":"022"},	//국내외구분코드, 프로젝트특성코드, 프로젝트상태코드
			view.codeDatas=json;
			
			$.each(view.codeMap, function(key, value) {
				if(key=='abrdCd'){
					view.selectCommonCodeCallBack005(view.codeDatas[value]);
				} else if(key=='prjtPrptyCd'){
					view.selectCommonCodeCallBack012(view.codeDatas[value]);
				} else if(key=='prjtCondCd'){
					var el = '';			
					$(view.codeDatas[value]).each(function(i, itm){	
						el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
					});						
					
					$("#prjtCondCdSf").append(el);

					var newEl = '<option value="">전체</option>';
					$("#prjtCondCdSf").prepend(newEl);
				} 
			});
			view.onLoadForAsync();
		}
		
		, selectCommonCodeCallBack005 : function(json) {
			var el = '';			
			$(json).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#abrdCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
	
		, selectCommonCodeCallBack012 : function(json) {
			var el = '';			
			$(json).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#prjtPrptyCd").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		, selectTableData : function() {
			var table = $('#dataTables-prjtList').DataTable(
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
						        { data: 'prjtCfrmDt', "sClass": "a-center"},
						        { data: 'prjtEndDt', "sClass": "a-center"},
						        { data: 'workStartDt' ,"sClass": "a-center"},
						        { data: 'workEndDt' ,"sClass": "a-center"},
						        { data: 'totMm' ,"sClass": "a-center"},
						        { data: 'salesChgrNm' },
						        { data: 'prjtGrncDesc' }
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/prjtMgt",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							//$("#detail").hide();
							//$("#tracctChgrList").hide();
							/*
							aoData.push({
								"name" : "srchPrjtNm",
								"value" :  $("#srchPrjtNm").val()
							});
							*/
							var reqData = $('form[name="sf"]').serializeArray();
							$.merge(aoData,reqData);
							
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
			
			$('#dataTables-prjtList tbody').on('click', 'tr', function () {
				
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
		        
		        //프로젝트 상세 조회
		        view.selectOneData(data.prjtCd);
		        
		        //매출계약에 매핑된 거래처 건수(0 아니면 삭제 불가)
		        $("#prjtCnt").val(data.prjtCnt);
		        
		        
				
		    } );
			
		}
		, selectOneData : function(prjtCd){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/prjtMgt/"+prjtCd
				  		, type : "GET"
						, data  : reqData
						, success : view.selectOneDataCallBack
			});
			
		}
		, selectOneDataCallBack : function(json){
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
				$("#abrdCd").val( itm.abrdCd);
				$("#prjtPrptyCd").val( itm.prjtPrptyCd);
				$("#prjtGrndDesc").val( itm.prjtGrndDesc);
				$("#memoDesc").val( itm.memoDesc);
				
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
		
		, selectOneData2 : function(prjtCd){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/prjtMgt/"+prjtCd
				  		, type : "GET"
						, data  : reqData
						, success : view.selectOneDataCallBack2
			});
			
		}
		, selectOneDataCallBack2 : function(json){
			$(json.detail).each(function(idx, itm) {
				$("#trAcctCd").val( itm.trAcctCd);
				$("#trAcctNm").val( itm.trAcctNm);
				$("#prjtCd").val( itm.prjtCd);
				$("#prjtNm").val( itm.prjtNm);
				$("#cstmrNm").val( itm.cstmrNm);
				
			});
			
		}
		
		, insertData : function() {
			
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/prjtMgt/insertPrjt"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
			
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {
				alert("등록되었습니다.");
				var table = $('#dataTables-prjtList').dataTable();
				table.fnReloadAjax();
				
				view.initDetail();
			}
			else {
				alert(json.msg);
			}	
		}	

		
		, modifyData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			var prjtCd=$("#prjtCd").val();
			
			//alert(prjtCd);
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/prjtMgt/"+prjtCd
				  		, type : "PUT"
						, data  : reqData 
						, success : view.modifyDataCallBack
			});	
			
		}
		, modifyDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert("저장되었습니다.");
					var table = $('#dataTables-prjtList').dataTable();
					table.fnReloadAjax();
					
					view.initDetail();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, deleteData : function() {
			var prjtCd=$("#prjtCd").val();
			common.ajax({
						url : G_CONTEXT_PATH+"/prjtMgt/" + prjtCd
				  		, type : "DELETE"
						, success : view.deleteDataCallBack
			});		
		}
		, deleteDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('삭제 완료되었습니다.');
					var table = $('#dataTables-prjtList').dataTable();
					table.fnReloadAjax();
					
					view.initDetail();
					
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		
		, updatePrjtCfrm : function() {
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/prjtMgt/updatePrjtCfrm"
				  		, type : "POST"
						, data  : reqData 
						, success : view.updatePrjtCfrmCallBack
			});
		}
		, updatePrjtCfrmCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('확정되었습니다.');
					var table = $('#dataTables-prjtList').dataTable();
					table.fnReloadAjax();
					
					view.initDetail();
					
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, updatePrjtCfrmCancel : function() {
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/prjtMgt/updatePrjtCfrmCancel"
				  		, type : "POST"
						, data  : reqData 
						, success : view.updatePrjtCfrmCancleCallBack
			});
		}
		, updatePrjtCfrmCancleCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('확정취소되었습니다.');
					var table = $('#dataTables-prjtList').dataTable();
					table.fnReloadAjax();
					
					view.initDetail();
					
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, updatePrjtEnd : function() {
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/prjtMgt/updatePrjtEnd"
				  		, type : "POST"
						, data  : reqData 
						, success : view.updatePrjtEndCallBack
			});
		}
		, updatePrjtEndCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('종료되었습니다.');
					var table = $('#dataTables-prjtList').dataTable();
					table.fnReloadAjax();
					
					view.initDetail();
					
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, updatePrjtEndCancel : function() {
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/prjtMgt/updatePrjtEndCancel"
				  		, type : "POST"
						, data  : reqData 
						, success : view.updatePrjtEndCancleCallBack
			});
		}
		, updatePrjtEndCancleCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('종료취소되었습니다.');
					var table = $('#dataTables-prjtList').dataTable();
					table.fnReloadAjax();
					
					view.initDetail();
					
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		
		, initDetail : function() {
			$("#trAcctCd").val('');
			$("#trAcctNm").val('');
			$("#prjtCd").val('');
			$("#prjtNm").val('');
			$("#cstmrNm").val('');

			$("#prjtCfrmDt").val('');
			$("#prjtEndDt").val('');
			$("#workStartDt").val('');
			$("#workEndDt").val('');
			$("#abrdCd").val('');
			$("#prjtPrptyCd").val('');
			$("#prjtGrndDesc").val('');
			$("#memoDesc").val('');
			
			//$("#chgSectNm1").val('');
			$("#chgrNm1").val('');
			$("#pstnNm1").val('');
			$("#telNo1").val('');
			$("#hpNo1").val('');
			$("#emailAddr1").val('');
			
			//$("#chgSectNm2").val('');
			$("#chgrNm2").val('');
			$("#pstnNm2").val('');
			$("#telNo2").val('');
			$("#hpNo2").val('');
			$("#emailAddr2").val('');
			
		}
		, initTrAcctChgrList : function() {
			
		}
	};

	$(function() {
		view.onLoadEvent();
	});