var trAcctCd;
var chgrSectCdList;

var view = {
		codeDatas : '',	
		codeMap : {"chgrSectCd":"001","dpstExpctDayCd":"015"},	//담당유형코드, 입급예정일코드
		onLoadEvent : function() {
		
		$("#btnSearch").unbind('click');
		$("#btnSearch").click( function() {	
			var table = $('#dataTables-tracctList').dataTable();
			table.fnReloadAjax();
		});

		$("#btnSearchInit").unbind('click');
		$("#btnSearchInit").click( function() {	
			$('form[name="sf"]').each(function() {
				this.reset();  
			}); 
		});
		
		$('#sf input').keypress(function(e) {
		    if (e.keyCode == 13){			    	
		    	$("#btnSearch").click();
				return false;
		    }         
		});

		
		$("#btnSave").unbind('click');
		$("#btnSave").click( function() {
			
			if($("#trAcctNm").val()=='') {
				alert("거래처명을 입력하세요.");
				$("#trAcctNm").focus();
				return;
			}
			
			if (confirm("저장 하시겠습니까?") == false){
				  return;
			}

			if($("#trAcctCd").val()==''){
				view.insertData();	
			}else{
				view.modifyData();
			}
			
		});
		
		$("#btnCopyBizGrndAddr").click( function() {
			$("#mnShpAddr").val($("#bizGrndAddr").val());
		});
		
		$("#btnDelete").unbind('click');
		$("#btnDelete").click( function() {
			if($("#trAcctCd").val() == "") {
				alert("삭제할 행을 선택하세요.");
				return;
			}
			if($("#trAcctCnt").val() != "0") {
				alert("프로젝트에 등록된 거래처는 삭제할 수 없습니다.");
				return;
			} 
			if (confirm("삭제 하시겠습니까?") == false){
				  return;
			}
			view.deleteData();
		});

		
		$("#btnRowAdd").click( function() {
			var $obj = $("input[name='choice']");
			var checkCount = $obj.size();
			if(checkCount == 0 || checkCount == "undefind") {
				$("#dataTables-tracctChgrList tr:not(:first)").remove();
			}
			
			var html = "";
			html += '<tr><td><input type="radio" name="choice" id="choice"  style="width:100%;" value=""/></td>';
			html +=  '<td><select name="chgSectCd" id="chgSectCd" class="form-control" style="width:100%;"><option value="">선택</option>';
        	
        	for (var i=0; i<chgrSectCdList.length; i++) {
        		var val = chgrSectCdList[i];
        		 //console.log(chgrSectCdList[0][i]);
        		 html += '<option value="'+val[0]+'"';
		         html += ' >'+val[1]+'</option>';
        	}
        	
        	html += '</select></td>';
			html += '<td><input type="input" name="chgrNm" id="chgrNm" class="form-control" style="width:100%;" maxlength="10" value=""></td><td><input type="input" name="pstnNm" id="pstnNm" class="form-control" style="width:100%;" maxlength="10" value=""></td><td><input type="input" name="telNo" id="telNo" class="form-control" style="width:100%;" maxlength="20" value=""></td><td><input type="input" name="hpNo" id="hpNo" class="form-control" style="width:100%;" maxlength="20" value=""></td><td><input type="input" name="emailAddr" id="emailAddr" class="form-control" style="width:100%;" maxlength="50" value=""></td><td><input type="input" name="addr" id="addr" class="form-control" style="width:100%;" maxlength="100" value=""></td><td><input type="input" name="memoDesc" id="memoDesc" class="form-control" style="width:100%;" maxlength="1000" value=""></td></tr>';
			
			//alert(html);
			$('#dataTables-tracctChgrList > tbody:last').append(html);
			
			//$('#dataTables-tracctChgrList').dataTable().fnUpdate(  );
		});

		$("#btnRowDel").click( function() {
			//$('#dataTables-tracctChgrList > tbody:last > tr:last').remove();   
			var $obj = $("input[name='choice']");
			var checkCount = $obj.size();
			for (var i=0; i<checkCount; i++){
				if($obj.eq(i).is(":checked")){
					$obj.eq(i).parent().parent().remove();
				}
			}
		});
		
		view.selectCommonCodes();
		
		
		}
		, onLoadForAsync : function() {
			$.fn.dataTable.ext.buttons.newTracct = {
				    text: '신규등록',
				    action: function ( e, dt, node, config ) {	
				    	$("#detail").show(0, view.initDetail);
				    }
				};
			view.selectTableData();
			view.selectTracctChgrList();
			
			
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
			//codeMap : {"chgrSectCd":"001","dpstExpctDayCd":"015"},	//담당유형코드, 입급예정일코드
			view.codeDatas=json;
			
			$.each(view.codeMap, function(key, value) {
				if(key=='chgrSectCd'){
					chgrSectCdList = new Array();	
					
					$(view.codeDatas[value]).each(function(i, itm){	
						var array = new Array();
						array.push(itm.dtlCd);
						array.push(itm.dtlCdNm);
						chgrSectCdList.push(array);
					});						
					
				} else if(key=='dpstExpctDayCd'){
					var el = '';			
					$(view.codeDatas[value]).each(function(i, itm){			
						el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
						//console.log(el);
					});
					$("#dpstExpctDayDesc").append(el);
					$("select:eq(0) option:eq(0)").attr("selected", "selected");
					$("select:eq(0) option:eq(0)").trigger('change');	
				} 
			});
			view.onLoadForAsync();
		}
		, selectCommonCode1 : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/001"
				  		, type : "GET"
						, success : view.selectCommonCodeCallBack
			});
		}
		, selectCommonCodeCallBack1 : function(json) {
			chgrSectCdList = new Array();
			$(json.list).each(function(i, itm){				
				var array = new Array();
				array.push(itm.dtlCd);
				array.push(itm.dtlCdNm);
				chgrSectCdList.push(array);
				//console.log(itm.dtlCdNm);
			});
		}
		, selectCommonCode2 : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/015"
				  		, type : "GET"
						, success : view.selectCommonCodeCallBack2
			});
		}
		, selectCommonCodeCallBack2 : function(json) {
			var el = '';			
			$(json.list).each(function(i, itm){				
				el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				//console.log(el);
			});
			$("#dpstExpctDayDesc").append(el);
			$("select:eq(0) option:eq(0)").attr("selected", "selected");
			$("select:eq(0) option:eq(0)").trigger('change');	
		}
		, selectTableData : function() {
			var table = $('#dataTables-tracctList').DataTable(
					{
						dom: 'lBfrtip',
						buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} , 'newTracct' ],	
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
						        { data: 'bizRgtNo',"sClass": "a-center" },
						        { data: 'chgSectNm' },
						        { data: 'chgrNm' },
						        { data: 'pstnNm' },
						        { data: 'telNo' },
						        { data: 'hpNo' },
						        { data: 'emailAddr' }
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/trAcctMgt",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							//$("#detail").hide();
							//$("#tracctChgrList").hide();
							$("#detail").hide(0, view.initDetail);
							aoData.push({
								"name" : "srchTrAcctNm",
								"value" :  $("#srchTrAcctNm").val()
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
		        //alert( 'You clicked on '+data.trAcctCd+'\'s row' );
		        view.initDetail();
		        
		        //프로젝트에 등록된 거래처 수(0보다 크면 삭제 못함)
		        $("#trAcctCnt").val(data.trAcctCnt);
		        
		        //거래처 상세 조회
		        view.selectOneData(data.trAcctCd);
				
		        //담당자 목록 조회
	        	trAcctCd = data.trAcctCd;
	        	var table1 = $('#dataTables-tracctChgrList').dataTable();
	        	table1.fnReloadAjax();
				
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
				$("#dpstExpctDayDesc").val( itm.dpstExpctDayDesc);
				$("#emoDesc").val( itm.emoDesc);
				
			});
			
			
			$("#detail").show();
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
						"columnDefs": [ { visible: false, targets: [0]  },{ className: "text-center", "targets": [ 0,1 ] } ],
						"deferLoading": 0,
						"iDisplayLength": 10,
						// "scrollY":        "300px",
					  //      "scrollCollapse": true,
						"aoColumns": [
						        { data: 'chgrSeqNo' , "render": function ( data ) { return '<input type="text" name="chgrSeqNo" id="chgrSeqNo" readonly="readonly" value="'+data+'">';} },
						        { data: '' , "render": function ( data ) { return '<input type="radio" name="choice" id="choice" style="width:50%;" value="">';} },
						        { data: 'chgSectCd' , "render": function ( data,  code) {
						        	//console.log(chgrSectCdList);
						        	html =  '<select name="chgSectCd" id="chgSectCd" class="form-control" style="width:100px;"><option value="">선택</option>';
						        	
						        	for (var i=0; i<chgrSectCdList.length; i++) {
						        		var val = chgrSectCdList[i];
						        		 //console.log(chgrSectCdList[0][i]);
						        		 html += '<option value="'+val[0]+'"';
								         html += data == val[0] ? "selected=\"selected\"" : "\"\"";
								         html += ' >'+val[1]+'</option>';
						        	}
						        	
						        	html += '</select>';
						        	
						        	return html;
						        } },
						        { data: 'chgrNm' , "render": function ( data ) { return '<input type="input" name="chgrNm" id="chgrNm" class="form-control" style="width:100%;" maxlength="10" value="'+data+'">';} },
						        { data: 'pstnNm' , "render": function ( data ) { return '<input type="input" name="pstnNm" id="pstnNm" class="form-control" style="width:100%;" maxlength="10" value="'+data+'">';} },
						        { data: 'telNo' , "render": function ( data ) { return '<input type="input" name="telNo" id="telNo" class="form-control" style="width:100%;" maxlength="20" value="'+data+'">';} },
						        { data: 'hpNo' , "render": function ( data ) { return '<input type="input" name="hpNo" id="hpNo" class="form-control" style="width:100%;" maxlength="20" value="'+data+'">';} },
						        { data: 'emailAddr' , "render": function ( data ) { return '<input type="input" name="emailAddr" id="emailAddr" class="form-control" style="width:100%;" maxlength="50" value="'+data+'">';} },
						        { data: 'addr' , "render": function ( data ) { return '<input type="input" name="addr" id="addr" class="form-control" style="width:100%;" maxlength="100" value="'+data+'">';} },
						        { data: 'memoDesc' , "render": function ( data ) { return '<input type="input" name="memoDesc" id="memoDesc" class="form-control" style="width:100%;" maxlength="1000" title="'+data+'" value="'+data+'">';} }

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
				/*
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
		        */
				
		   
		    } );
			
			$('#dataTables-tracctChgrList tbody').on('click', 'td', function(e) {
				
				if($(this).children(":first").is(':radio')){
					var chk = $(this).closest("tr").find("input:radio").get(0);
					if (e.target != chk) {
						chk.checked = !chk.checked;
					}
				}
				
				
			});
			
		}
		, insertData : function() {
			
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/trAcctMgt/insertTrAcct"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
			
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {
				alert("등록되었습니다.");
				
				view.initDetail();
				
				var table = $('#dataTables-tracctList').dataTable();
				table.fnReloadAjax();
				
				//담당자 목록 조회
	        	trAcctCd = "";
	        	
	        	
	        	var table1 = $('#dataTables-tracctChgrList').dataTable();
	        	table1.fnReloadAjax();
			}
			else {
				alert(json.msg);
			}	
		}	

		
		, modifyData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			var trAcctCd=$("#trAcctCd").val();
			
			//alert(trAcctCd);
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/trAcctMgt/"+trAcctCd
				  		, type : "PUT"
						, data  : reqData 
						, success : view.modifyDataCallBack
			});	
			
		}
		, modifyDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert("저장되었습니다.");
					
					view.initDetail();
					
					var table = $('#dataTables-tracctList').dataTable();
					table.fnReloadAjax();
					
					//담당자 목록 조회
		        	trAcctCd = "";
		        	var table1 = $('#dataTables-tracctChgrList').dataTable();
		        	table1.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, deleteData : function() {
			var trAcctCd=$("#trAcctCd").val();
			common.ajax({
						url : G_CONTEXT_PATH+"/trAcctMgt/" + trAcctCd
				  		, type : "DELETE"
						, success : view.deleteDataCallBack
			});		
		}
		, deleteDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('삭제 완료되었습니다.');
					var table = $('#dataTables-tracctList').dataTable();
					table.fnReloadAjax();
					
					//담당자 목록 조회
		        	trAcctCd = "";
		        	var table1 = $('#dataTables-tracctChgrList').dataTable();
		        	table1.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, initDetail : function() {
			$("#trAcctCd").val(''); //거래처 코드
			$("#trAcctCnt").val(''); //프로젝트에 매핑된 거래처 건수
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
			
			//담당자 목록 모두 삭제				
			$("#dataTables-tracctChgrList tr:not(:first)").remove();	
			
			//담당자 목록 1건 추가
			var html = "";
			html += '<tr><td><input type="radio" name="choice" id="choice" class="form-control" style="width:100%;" value=""/></td>';
			html +=  '<td><select name="chgSectCd" id="chgSectCd" class="form-control" style="width:100%;"><option value="">선택</option>';
        	
        	for (var i=0; i<chgrSectCdList.length; i++) {
        		var val = chgrSectCdList[i];
        		 //console.log(chgrSectCdList[0][i]);
        		 html += '<option value="'+val[0]+'"';
		         html += ' >'+val[1]+'</option>';
        	}
        	
        	html += '</select></td>';
        	html += '<td><input type="input" name="chgrNm" id="chgrNm" class="form-control" style="width:100%;" maxlength="10" value=""></td><td><input type="input" name="pstnNm" id="pstnNm" class="form-control" style="width:100%;" maxlength="10" value=""></td><td><input type="input" name="telNo" id="telNo" class="form-control" style="width:100%;" maxlength="20" value=""></td><td><input type="input" name="hpNo" id="hpNo" class="form-control" style="width:100%;" maxlength="20" value=""></td><td><input type="input" name="emailAddr" id="emailAddr" class="form-control" style="width:100%;" maxlength="50" value=""></td><td><input type="input" name="addr" id="addr" class="form-control" style="width:100%;" maxlength="100" value=""></td><td><input type="input" name="memoDesc" id="memoDesc" class="form-control" style="width:100%;" maxlength="1000" value=""></td></tr>';
			
			$('#dataTables-tracctChgrList > tbody:last').append(html);
		}
		, initTrAcctChgrList : function() {
			//$("#tracctChgrList").removeAll();
			//$("#tracctChgrList tr:not(:first)").remove();
		}
	};

	$(function() {
		view.onLoadEvent();
	});