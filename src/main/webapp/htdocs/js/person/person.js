var view = {
			personTable : '',	
			codeDatas : '',	
			codeMap : {"sexCd":"002","acdmcCd":"003","cntrctSectCd":"004","skillSectCd":"006","certCd":"007", "payDayCd":"015" },	//성별코드, 학력코드, 계약유형코드, 기술분야코드, 기사자격코드, 입금예정일
			onLoadEvent : function() {
		
				view.selectCommonCodes();

				$('#searchBox input').keypress(function(e) {
				    if (e.keyCode == 13){			    	
				    	$("#btnSearch").click();
						return false;
				    }         
				});
				
				$("#btnSearch").unbind('click');
				$("#btnSearch").click( function() {
					view.personTable.fnReloadAjax();
				});
				
				$("#btnSave").unbind('click');
				$("#btnSave").click( function() {
					//if(!view.validator()) return false;
					if($("#prsnNo").val()==''){
						view.insertData();
					}else{
						view.modifyData();
					}
				});
				
				$("#btnDelete").unbind('click');
				$("#btnDelete").click( function() {
					if($("#prsnNo").val()==''){
						alert('선택된 데이터가 없습니다.');
					}else{
						view.deleteData();
					}
				});
				
				$("#btnSearchInit").unbind('click');
				$("#btnSearchInit").click( function() {			
					$('form[name="sf"]').each(function() {
						this.reset();  
					}); 
				});
				
				$('#prflAtchtFile').unbind('change');
				$('#prflAtchtFile').bind('change', function() {
					$('#fileName').html('');
					//$('#prflAtchtFlNo').val('');
				});	
					
				$("#btnSearchInit").unbind('click');
				$("#btnSearchInit").click( function() {			
					$('form[name="sf"]').each(function() {
						this.reset();  
					}); 
				});
				
				
				
				$("#f").submit(function(e) {
					
					//$('#lawRgtNo').val($('#lawRgtNo1')+$('#lawRgtNo2'));
					var formData = new FormData(this);
					
					if($("#prsnNo").val()==''){
						urlStr = G_CONTEXT_PATH+"/person";
						successObj = view.insertDataCallBack;
						errorObj = view.insertDataCallBack;
					}else{
						var prsnNo=$("#prsnNo").val();
						urlStr = G_CONTEXT_PATH+"/person/"+prsnNo+"/modify";
						successObj = view.modifyDataCallBack;
						errorObj = view.modifyDataCallBack;
					}
					
					jQuery.ajax({
				  		url :  urlStr,
						type: "POST",
						data:  formData,
						mimeType:"multipart/form-data",
						contentType: false,
						cache: false,
						processData:false,
						success: successObj,
						error: errorObj
					});
					
					e.preventDefault();
				});
				
			}
			, onLoadForAsync : function() {
				$.fn.dataTable.ext.buttons.newPerson = {
					    text: '신규등록',
					    action: function ( e, dt, node, config ) {				        
					    	$("#detail").show(0, view.initDetail);
					    }
					};
				view.selectTableData();
				view.personTable = $('#dataTables-person').dataTable();
				
				$('#dataTables-person tbody').on('click', 'tr', function () {
					view.initDetail();
					data = view.personTable.fnGetData(this);
					if(data==null){
						return false;
					}
					view.selectOneData(data.prsnNo);
			    } );
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
				//codeMap : {"sexCd":"002","acdmcCd":"003","cntrctSectCd":"004","skillSectCd":"006","certCd":"007", "payDayCd":"015" },	//성별코드, 학력코드, 계약유형코드, 기술분야코드, 기사자격코드, 입금예정일
				view.codeDatas=json;
				
				$.each(view.codeMap, function(key, value) {
					if(key=='sexCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<label class="radio-inline">';
							el += '<input type="radio" name="sexCd" id="sexCd" value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</label>';
						});						
						$("#sexCdTd").append(el);
						$("#sexCdTdSf").append(el);
						
						var newEl='';
						newEl += '<label class="radio-inline">';
						newEl += '<input type="radio" name="sexCd" id="sexCd" value="">전체</label>';
						$("#sexCdTdSf").prepend(newEl);
						
					} else if(key=='certCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<label class="radio-inline">';
							el += '<input type="radio" name="certCd" id="certCd" value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</label>';
						});						
						$("#certCdTd").append(el);
					} else if(key=='skillSectCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<label class="checkbox-inline">';
							el += '<input type="checkbox" name="skillSectCd" id="skillSectCd" value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</label>';
						});						
						$("#skillSectCdTd").append(el);
						$("#skillSectCdTdSf").append(el);
					} else if(key=='cntrctSectCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
						});						
						$("#cntrctSectCd").append(el);
						$("#cntrctSectCdSf").append(el);
						
						var newEl = '<option value="">무관</option>';
						$("#cntrctSectCdSf").prepend(newEl);
						
					} else if(key=='acdmcCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
						});						
						$("#acdmcCd").append(el);
						
						$("#acdmcCdSf").append(el);

						var newEl = '<option value="">무관</option>';
						$("#acdmcCdSf").prepend(newEl);
						
					} else if(key=='payDayCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
						});						
						$("#payDayCd").append(el);
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
			, converCodeNmSkill : function(data, groupCodeNm) {
				var dataSplit = data.split(',');
				var el='';
				for ( var i=0; i< dataSplit.length; i++) {
					
					if(i!=0){
						el +=',';
					}
					el += view.converCodeNm(dataSplit[i],groupCodeNm );
				}
				return el;
			}
			, makeCombo : function(groupCode) {
				
			}
			, selectTableData : function() {
				var table = $('#dataTables-person').DataTable(
						{
							dom: 'lBfrtip',
							//buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} , 'newPerson' ],		
							buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} ,$.extend( true, {}, buttonExcel, {
								extend: 'excel',title: "인력" 
							} )  , 'newPerson'  ],
							"paging": true,
							"processing" : true,
							"serverSide" : true,
							"bFilter": false,
							"autoWidth": true,
							"ordering": false,
							"lengthMenu": [[10, 25, 50, 500, 1000, -1], [10, 25, 50, 500, 1000, "All"]],
							"iDisplayLength": 10,
							columnDefs: [ { visible: false, targets: [0] } ],
							select:true,
							"aoColumns": [
							        { data: 'prsnNo' },
							        { data: 'prsnNm'},
							        { data: 'skillSectCd' , "render": function ( data ) { return view.converCodeNmSkill(data, 'skillSectCd');} }, 
							        { data: 'lastWorkStartDt' },
							        { data: 'lastWorkEndDt' },
							        { data: 'totalWork' },							        
							        { data: 'cntrctSectCd' , "render": function ( data ) { return view.converCodeNm(data, 'cntrctSectCd');} }, 
							        { data: 'acdmcCd' , "render": function ( data ) { return view.converCodeNm(data, 'acdmcCd');} }, 
							        { data: 'hpNo' },
							        { data: 'prsnEmailAddr' },
							        { data: 'memoDesc' }
							],
							"sAjaxSource" : G_CONTEXT_PATH+"/person",
							"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
								$("#detail").hide(0, view.initDetail);
								
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
							var fv = '<a id="aFileName">' +v +'</a>';
							$("#"+k).html( fv);
						}
						
					}
				});
			});
			
			$(json.prjtInfo).each(function(idx, itm) {
				var prjtCd = json.prjtInfo.prjtCd
				$.each(itm, function(k, v) {
					if(k=='prjtNm'){
						var pv = '<a id="btnPrjtPop" prjtCd="'+prjtCd+'" >' +v +'</a>';
						$("#"+k).html( pv);
					}else{
						
						if (k.indexOf("Amt") > -1) {
							v = common.toDotNumber(v);
						}
						$("#"+k).html(v);
					}
				});
			});
			
			$("#btnPrjtPop").unbind('click');
			$("#btnPrjtPop").click( function(e) {
				e.preventDefault();  //stop the browser from following
				window.open(G_CONTEXT_PATH+"/web/prjtmgt/prjtpop?prjtCd="+$(this).attr('prjtCd')+"&trAcctCd="+$("#trAcctCd").val(), "window", "width=1200,height=800,scrollbars=yes");
			});
			
			$("#detail").show();
			
			$('#aFileName').unbind('click');
			$('#aFileName').bind('click', function(e) {
				e.preventDefault();  //stop the browser from following
				window.location.href = G_CONTEXT_PATH + '/download/'+ $('#prflAtchtFlNo').val();
    
			});	
			
		}
		, insertData : function() {
			
			

			
			$("#f").submit();
			
			/*
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
			  			url : G_CONTEXT_PATH+"/person"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
			*/
		}
		, insertDataCallBack : function(data){
			var json = jQuery.parseJSON(data);
			
			if ( json.status == 200 ) {
				view.personTable.fnReloadAjax();	
			}
			else {
				alert(json.msg);
			}	
		}
		, modifyData : function() {
			
			$("#f").submit();
			/*
			var reqData = $('form[name="f"]').serializeArray();
			var prsnNo=$("#prsnNo").val();
			common.ajax({
			  			url : G_CONTEXT_PATH+"/person/" + prsnNo
				  		, type : "PUT"
						, data  : reqData 
						, success : view.modifyDataCallBack
			});
			*/	
		}
		, modifyDataCallBack : function(data){
			
			var json = jQuery.parseJSON(data);
			
			try {
				if ( json.status == 200 ) {
					view.personTable.fnReloadAjax();
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
			$("#prjtNm").html('');
			$("#prjtDt").html('');
			$("#prjtAmt").html('');
			
		}
	};

	$(function() {
		view.onLoadEvent();
	});
	
	
	