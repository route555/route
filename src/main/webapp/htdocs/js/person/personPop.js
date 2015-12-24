var view = {
			personTable : '',	
			codeDatas : '',	
			sendData : '',	
			codeMap : {"sexCd":"002","acdmcCd":"003","cntrctSectCd":"004","skillSectCd":"006","certCd":"007" },	//성별코드, 학력코드, 계약유형코드, 기술분야코드, 기사자격코드
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
				
				$("#btnSearchInit").unbind('click');
				$("#btnSearchInit").click( function() {			
					$('form[name="sf"]').each(function() {
						this.reset();  
					}); 
				});
				
				$("#btnClose").unbind('click');
				$("#btnClose").click( function() {
					window.close();
				});
				
				$("#btnSelect").unbind('click');
				$("#btnSelect").click( function() {
					
					if(view.sendData==''){
						alert('인력을 선택해주세요');
					}else{
						window.opener.view.popEvent(view.sendData);  
						window.close(); 
					}

				});
				$('form').find('input[type=text],textarea,select').filter(':visible:first').focus();
								
			}
			, onLoadForAsync : function() {
				view.selectTableData();
				view.personTable = $('#dataTables-person').dataTable();
				
				$('#dataTables-person tbody').on('click', 'tr', function () {
					view.initDetail();
					data = view.personTable.fnGetData(this);
					if(data==null){
						return false;
					}
					view.sendData=data;
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
				//codeMap : {"sexCd":"002","acdmcCd":"003","cntrctSectCd":"004","skillSectCd":"006","certCd":"007" },	//성별코드, 학력코드, 계약유형코드, 기술분야코드, 기사자격코드
				view.codeDatas=json;
				
				$.each(view.codeMap, function(key, value) {
					if(key=='sexCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<label class="radio-inline">';
							el += '<input type="radio" name="sexCd" id="sexCd" value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</label>';
						});						
						$("#sexCdTdSf").append(el);
						var newEl='';
						newEl += '<label class="radio-inline">';
						newEl += '<input type="radio" name="sexCd" id="sexCd" value="">전체</label>';
						$("#sexCdTdSf").prepend(newEl);
					} else if(key=='skillSectCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<label class="checkbox-inline">';
							el += '<input type="checkbox" name="skillSectCd" id="skillSectCd" value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</label>';
						});						
						$("#skillSectCdTdSf").append(el);
					} else if(key=='acdmcCd'){
						var el = '';			
						$(view.codeDatas[value]).each(function(i, itm){	
							el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
						});						
						$("#acdmcCdSf").append(el);
						var newEl = '<option value="">무관</option>';
						$("#acdmcCdSf").prepend(newEl);
					}
				});
				
				view.onLoadForAsync();
			}
			, converCodeNm : function(data, groupCodeNm) {
				var groupCode = view.codeMap[groupCodeNm];
				var gData = jQuery.grep(view.codeDatas[groupCode], function(obj) {
				    return obj.dtlCd === data;
				});
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
			, selectTableData : function() {
				var table = $('#dataTables-person').DataTable(
						{
							dom: 'lBfrtip',
							buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} ],				        
							"paging": true,
							"processing" : true,
							"serverSide" : true,
							"bFilter": false,
							"autoWidth": true,
							"ordering": false,
							deferLoading: 0, 
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
					if(k=='cntrctSectCd'){
						$("#"+k).html(view.converCodeNm(v, 'cntrctSectCd'));
					}else if(k=='sexCd'){
						$("#detail #"+k).html(view.converCodeNm(v, 'sexCd'));
					}else if(k=='certCd'){
						$("#"+k).html(view.converCodeNm(v, 'certCd'));
					}else if(k=='acdmcCd'){
						$("#"+k).html(view.converCodeNm(v, 'acdmcCd'));	
					}else if(k=='skillSectCd'){
						$("#detail #"+k).html(view.converCodeNmSkill(v, 'skillSectCd'));
					}else{
						$("#"+k).html( v);
					}
				});
			});
			
			$("#detail").show();
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
	
	
	