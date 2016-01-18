var view = {
			saleMgtTable : '',	
			codeDatas : '',	
			codeMap : {"dpstExpctDayCd":"015", "dmndCd":"016"},	//입급예정일코드, 청구구분
			onLoadEvent : function() {
				
				$('.input-group.date').datepicker({
					format : "yyyy-mm-dd",
					language : "kr",
					forceParse : true,
					autoclose : true,
					todayBtn : "linked",
					showOnFocus : false,
					todayHighlight : true
				}).on('changeDate', function(e) {
					var obj = $(this).find("input:hidden");
					obj.val(obj.val().replace(/-/g,''));
				});
						
				$('.send-date').blur(function(e) {
					var str = $(this).val();
					var isValidate = false;
					
					if (str.length == 8 && common.isNumber(str)){
						isValidate = true;
					} else if(str.length == 10 && str.split('-').length == 3){
						isValidate = true;
					}
								
					if (isValidate == false){
						alert('입력일자 형식 오류!');
						$(this).focus();
						return false;
					}
					
					if( str.indexOf('-') == -1){
						var match = str.match(/(\d{4})(\d{2})(\d{2})/);
						str = match[1] + '-' + match[2] + '-' + match[3];
					}
					
					$(this).parent().datepicker('update', str).trigger('changeDate');
					
				});
			
				$('#dmndDateStCom').datepicker('setDate', common.getCurrentFirstDate('-')).trigger('changeDate');
				$('#dmndDateEndCom').datepicker('setDate', common.getCurrentLastDate('-')).trigger('changeDate');

				
				view.selectCommonCodes();

				$('#searchBox input').keypress(function(e) {
				    if (e.keyCode == 13){			    	
				    	$("#btnSearch").click();
						return false;
				    }         
				});
				
				$("#btnSearch").unbind('click');
				$("#btnSearch").click( function() {
					view.saleMgtTable.fnReloadAjax();
				});
				
				$("#btnBillIssue").unbind('click');
				$("#btnBillIssue").click( function() {
					var sendDataArray = new Array();
					$("input:checked", view.saleMgtTable.fnGetNodes()).each(function(i){
						 data = view.saleMgtTable.fnGetData($(this).parent().parent());
						 
						 sendData = new Object();
						 
						 sendData.cntrctCd = data.cntrctCd;
						 sendData.dmndSeqNo = data.dmndSeqNo;
						 //sendData.billIssueYn = data.billIssueYn;
						 sendData.billIssueDt = data.billIssueDt;
						 //sendData.dpstYn = data.dpstYn;
						 sendData.dpstDt = data.dpstDt;
						 sendData.memoDesc = $("#memoDesc").val();
						 
						 sendDataArray.push(sendData);
					});
					console.log(sendDataArray);
					if(sendDataArray.length==0){
						alert('데이터를 선택하세요');
						return false;
					}
					var sendData = new Object();
					
					sendData.inData=sendDataArray;
									
					console.log(sendData);
					view.billIssue(sendData);
				});
				
				
			
				$("#btnSearchInit").unbind('click');
				$("#btnSearchInit").click( function() {			
					$('form[name="sf"]').each(function() {
						this.reset();  
					}); 
					
					$('#dmndDateSt').val('');
					$('#dmndDateEnd').val('');
				});
				
			
				
				
				
				// $('#datetimepicker12').datetimepicker();
				
				 /*
				  * 
				  * 
				  * 
				$('#dpstExpctDayView').mask('9999-99-99' ,{completed:function(){
					var numbers = this.val().replace(/-/g,'');
					$('#dpstExpctDay').val(numbers);
				}});
				
				  * $('#datetimepicker4').datetimepicker({
				      pickTime: false
				    });
				$('#dmndDateSt1').datepicker({
					
				})
				.on('changeDate', function(ev){
					//$('#dpstExpctDay').datepicker('hide');
				});
				
				$('#dmndDateEnd').datepicker({
					format: 'yyyy-mm-dd',
			        todayBtn: 'linked',
			        autoclose:true
				})
				.on('changeDate', function(ev){
					//$('#dpstExpctDay').datepicker('hide');
				});
				*/
				
				
			}
			, onLoadForAsync : function() {
				
				view.selectTableData();
				view.saleMgtTable = $('#dataTables-saleMgt').dataTable();

				$('#dataTables-saleMgt tbody').on('click', 'td', function(e) {
					// console.log(view.saleMgtTable.fnGetPosition(this)[1]);
					if (view.saleMgtTable.fnGetPosition(this)[1] == 0) {
						var chk = $(this).closest("tr").find("input:checkbox").get(0);
						if (e.target != chk) {
							chk.checked = !chk.checked;
						}
					}
				});
				
				$('#dataTables-saleMgt1111 tbody').on('click', 'tr', function () {
					
					data = view.saleMgtTable.fnGetData(this);
					
					console.log(this);
					console.log(data);
					/*
					data = view.personTable.fnGetData(this);
					if(data==null){
						return false;
					}
					view.selectOneData(data.prsnNo);
					*/
			    } );
				
				$('#dataTables-saleMgt tbody').on('click', 'span', function () {		
					$(this).prev().val('');
					$(this).prev().trigger('blur');
				});
				
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
				//codeMap : {"dpstExpctDayCd":"015", "dmndCd":"016"},	//입급예정일코드, 청구구분
				view.codeDatas=json;
				
				$.each(view.codeMap, function(key, value) {
					if(key=='dmndCd'){
						var el = '<option value="">전체</option>';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
						});						
						$("#dmndCdSf").append(el);
					} 
				});
				view.onLoadForAsync();
			}
			, converCodeNm : function(data, groupCodeNm) {
				var groupCode = view.codeMap[groupCodeNm];
				var gData = jQuery.grep(view.codeDatas[groupCode], function(obj) {
				    return obj.dtlCd === data;
				});
				//console.log(data,groupCode, gData[0]);
				if(gData[0] != undefined){
					return gData[0].dtlCdNm;
				}else{
					return 'N/A';
				}
			}
			, convertDateInput : function(data, name) {
				var el='';
				//el +='<input type="hidden" name="'+name+'Array" value="'+data+'">';
				el +='<div isExcel="true" style="width:110px;" ><input  class="input-sm form-control grid-mask" style="width:86px;" value="'+data+'" name="'+name+'">';
				el +='<span style="margin-left:5px;cursor:pointer" class="glyphicon glyphicon-remove deleteFn"></span></div>';
				return el;
			}
			, setMask : function() {
				$('#dataTables-saleMgt .grid-mask').mask('9999-99-99' ,{completed:function(){
					//this.get(0).setSelectionRange(0,0);
				}});
				
				$("#dataTables-saleMgt .grid-mask").click( function() {
					if($(this).val()=='____-__-__'){
						$(this).val(common.getAgoDate(0,0,0));
						this.setSelectionRange(0,0);
						//$(this).trigger('input');
					}
				});
				
				$("#dataTables-saleMgt .grid-mask").blur( function() {
					var numbers = $(this).val().replace(/-/g,'');
					data = view.saleMgtTable.fnGetData($(this).parent().parent().parent());
						
					if($(this).get(0).name=='billIssueDt'){
						data.billIssueDt=numbers;
					}else if($(this).get(0).name=='dpstDt'){
						data.dpstDt=numbers;
					}
								
				});
				
				
			}
			, selectTableData : function() {
				var table = $('#dataTables-saleMgt').DataTable(
						{
							dom: 'lBfrtip',
							buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} ,$.extend( true, {}, buttonExcel, {
								extend: 'excel',title: "매출관리" 
							} )   ],				        
							"paging": true,
							"processing" : true,
							"serverSide" : true,
							"bFilter": false,
							//"autoWidth": true,
							"scrollX": true,
							"ordering": false,
							"iDisplayLength": 10,
							"lengthMenu": [[10, 25, 50, 500, 1000, -1], [10, 25, 50, 500, 1000, "All"]],
							columnDefs: [ { visible: false, targets: [1,2] },{ className: "text-center", "targets": [ 0,3 ] } ],
							select:false,
							"aoColumns": [
							        {
						                data:   "active",
						                render: function ( data, type, row ) {
						                    if ( type === 'display' ) {
						                        return '<input type="checkbox" class="editor-active">';
						                    }
						                    return data;
						                }
						            },
							        { data: 'cntrctCd'},
							        { data: 'dmndSeqNo'},
							        { data: 'trAcctNm'},
							        { data: 'prjtNm'},
							        { data: 'workStartDt'},
							        { data: 'workEndDt'},
							        { data: 'dmndCd' , "render": function ( data ) { return view.converCodeNm(data, 'dmndCd');} }, 
							        { data: 'dmndDate'}, 
							        { data: 'billIssueDt', "render": function ( data ) { return view.convertDateInput(data,'billIssueDt');} }, 
							        { data: 'dpstExpctDayCd' , "render": function ( data ) { return view.converCodeNm(data, 'dpstExpctDayCd');} }, 
							        { data: 'dpstDt', "render": function ( data ) { return view.convertDateInput(data,'dpstDt');} }, 
							        { data: 'supplyAmt' },
							        { data: 'taxAmt' },
							        { data: 'totalAmt' },
							        { data: 'rmlrkDesc' },
							        { data: 'examNeedYn' },	
							        { data: 'examCfrmYn' },
							        { data: 'chgrNm' },
							        { data: 'telNo' }
							],
							"sAjaxSource" : G_CONTEXT_PATH+"/saleMgt",
							"fnDrawCallback": function( nRow, aData, iDataIndex ) {
								view.setMask();
								$("#memoDesc").val('');
							},
							"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
								
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
				
			}
		, selectOneData : function(prsnNo){
			common.ajax({
				  		url : G_CONTEXT_PATH+"/person/"+prsnNo
				  		, type : "GET"
						, success : view.selectOneDataCallBack
			});
		}
		, selectOneDataCallBack : function(json){
			//codeMap : {"sexCd":"002","acdmcCd":"003","cntrctSectCd":"004","skillSectCd":"006","certCd":"007" },	//성별코드, 학력코드, 계약유형코드, 기술분야코드, 기사자격코드
			$(json.detail).each(function(idx, itm) {
				$.each(itm, function(k, v) {
					//console.log(k,v);
					if(k=='sexCd'){
						 $('#sexCdTd input:radio[name="sexCd"][value="' + v +'"]').prop('checked', true);
					}else if(k=='certCd'){
						 $('#certCdTd input:radio[name="certCd"][value="' + v +'"]').prop('checked', true);
					}else if(k=='skillSectCd'){
						var dataSplit = v.split(',');
						for ( var i=0; i< dataSplit.length; i++) {
							$('#skillSectCdTd input:checkbox[name="skillSectCd"][value="' + dataSplit[i] +'"]').prop('checked', true);
						}
					}else{
						$("#"+k).val( v);
						
						if(k=='fileName'){
							$("#"+k).html( v);
						}
						
					}
				});
			});
			$("#detail").show();
		}
		, billIssue : function(sendDataArray) {
			var serializedMyObj = JSON.stringify(sendDataArray);
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/saleMgt"
			  			, contentType : "application/json; charset=UTF-8"
				  		, type : "PUT"
						, data  : serializedMyObj 
						, success : view.modifyDataCallBack
			});	
		}
		, modifyDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					view.saleMgtTable.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			} catch (e) {
				alert(e);
			}
		}
		, deleteData : function() {
			var prsnNo=$("#prsnNo").val();
			common.ajax({
						url : G_CONTEXT_PATH+"/person/" + prsnNo
				  		, type : "DELETE"
						, success : view.deleteDataCallBack
			});		
		}
		, deleteDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('삭제 완료되었습니다.');
					view.personTable.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			} catch (e) {
				alert(e);
			}
		}
		, initDetail : function() {
			$("#prsnNo").val('');
			$('form[name="f"]').each(function() {
				this.reset();  
			}); 
		}
	};

	$(function() {
		view.onLoadEvent();
	});
	
	/*
	$('#dpstExpctDay1').datepicker({
		format: 'yyyy-mm-dd',
        todayBtn: 'linked',
        autoclose:true
	})
	.on('changeDate', function(ev){
		//$('#dpstExpctDay').datepicker('hide');
	});
	*/
	