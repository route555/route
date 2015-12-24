
var view = {
		groupCodeTable : '',		
		onLoadEvent : function() {
			
			$('#searchBox input').keypress(function(e) {
			    if (e.keyCode == 13){			    	
			    	$("#btnSearch").click();
					return false;
			    }         
			});
			
			$("#btnSearch").unbind('click');
			$("#btnSearch").click( function() {			
				view.groupCodeTable.fnReloadAjax();
			});
			
			$("#groupCodeDetail #btnSave").unbind('click');
			$("#groupCodeDetail #btnSave").click( function() {	
				
				if(!view.validator()) return false;
				
				if($("#groupCodeDetail #isGroupView").val()==''){
					view.insertData();
				}else{
					view.modifyData();
				}
			});
			
			$("#groupCodeDetail #btnDelete").unbind('click');
			$("#groupCodeDetail #btnDelete").click( function() {			
				view.deleteData();
			});
						
			$.fn.dataTable.ext.buttons.newGroupCode = {
			    text: '신규등록',
			    action: function ( e, dt, node, config ) {				        
			    	$("#groupCodeDetail").show(0, view.onGroupDetailInit);
			    	
			    	$('#dataTables-detailCode').DataTable().clear();
			    	viewDtl.detailCodeTable.fnDestroy();
			    	viewDtl.selectDetailTableData();
			    	$("#detailCodeDetail").hide(0, view.onDetailDetailInit);
			    	
			    	
			    }
			};
			
			view.selectGroupTableData();
			view.groupCodeTable = $('#dataTables-groupCode').dataTable();
			
		}		

		, selectGroupTableData : function() {
			var table = $('#dataTables-groupCode').DataTable(
					{
						dom: 'lBfrtip',
				        buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} , 'newGroupCode' ],				        
				        columnDefs: [ { visible: false, targets: [3,4,5,6] } ],
						processing : true,
						serverSide : true,
						bFilter: false,
						autoWidth: true,
						ordering: false,
						select: true,
						iDisplayLength: 10,					
						aoColumns: [
						        { data: 'grpCd' },
						        { data: 'grpCdNm' }, 					 
						        { data: 'grpCdDesc' },
						        { data: 'rgtId' },
						        { data: 'rgtTm' },
						        { data: 'uptId' },
						        { data: 'uptTm' }
						],						
						sAjaxSource : G_CONTEXT_PATH+"/groupCode",
						fnServerData : function(sSource, aoData, fnCallback, oSettings) {							
							$('#groupCodeDetail').hide(0, view.onGroupDetailInit);
							aoData.push({"name" : "Input1",	"value" : "xx"});
							
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
			
			$('#dataTables-groupCode tbody').on('click', 'tr', function () {				
				var data = table.row( this ).data();			    
				view.selectOneData(data.grpCd);								
		    } );
			
		}				
		, selectOneData : function(grpCd){			
			common.ajax({
				  		url : G_CONTEXT_PATH + "/groupCode/" + grpCd
				  		, type : "GET"						
						, success : view.selectOneDataCallBack
			});
		}
		, selectOneDataCallBack : function(json){
			
			$(json.detail).each(function(idx, itm) {
				$("#groupCodeDetail #isGroupView").val( 'true');
				$("#groupCodeDetail #grpCd").val( itm.grpCd);
				$("#groupCodeDetail #grpCdNm").val( itm.grpCdNm);
				$("#groupCodeDetail #grpCdDesc").val( itm.grpCdDesc );				
			});
			
			$("#groupCodeDetail").show();
			viewDtl.detailCodeTable.fnReloadAjax();
			
		}		
		
		, insertData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			
			common.ajax({
			  			url : G_CONTEXT_PATH + "/groupCode"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {				
				view.groupCodeTable.fnReloadAjax();				
			} else {
				alert(json.msg);
			}	
		}
		, modifyData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			var grpCd = $("#groupCodeDetail #grpCd").val() ;
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/groupCode/" + grpCd
				  		, type : "PUT"
						, data  : reqData 
						, success : view.modifyDataCallBack
			});	
			
		}
		, modifyDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {					
					view.groupCodeTable.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, deleteData : function() {
			var grpCd = $("#groupCodeDetail #grpCd").val() ;
			common.ajax({
						url : G_CONTEXT_PATH+"/groupCode/" + grpCd
				  		, type : "DELETE"
						, success : view.deleteDataCallBack
			});		
		}
		, deleteDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('삭제 완료되었습니다.');
					view.groupCodeTable.fnReloadAjax();
					
					$('#dataTables-detailCode').DataTable().clear();
			    	viewDtl.detailCodeTable.fnDestroy();
			    	viewDtl.selectDetailTableData();
			    	//viewDtl.detailCodeTable = $('#dataTables-detailCode').dataTable();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, onGroupDetailInit : function() {
			$("#groupCodeDetail #isGroupView").val('');
			$("#groupCodeDetail #grpCd").val('');
			$("#groupCodeDetail #grpCdNm").val('');
			$("#groupCodeDetail #grpCdDesc").val('');			
		}
		, validator : function() {
			if ( $('#grpCd').val().length == 0 && $.trim($('#grpCd').val()) == "" ) {
				alert("그룹코드를 입력하세요.");
				return false;
			} else if( $('#grpCd').val().length != 3) {
				alert("그룹코드는 3자리여야 합니다.");
				return false;
			}
						
			if ( $('#grpCdNm').val().length == 0 && $.trim($('#grpCdNm').val()) == "" ) {
				alert("그룹코드명을 입력하세요.");
				return false;
			}
			
		
		
			return true;
		}
		
		
	};

	
	var viewDtl = {			
			detailCodeTable : '',
			onLoadEvent : function() {
				
				$("#detailCodeDetail #btnSave").unbind('click');
				$("#detailCodeDetail #btnSave").click( function() {	
					
					if($("#detailCodeDetail #isDetailView").val()==''){
						viewDtl.insertData();
					}else{
						viewDtl.modifyData();
					}
					
				});
				
				$("#detailCodeDetail #btnDelete").unbind('click');
				$("#detailCodeDetail #btnDelete").click( function() {			
					viewDtl.deleteData();
				});
				
				$.fn.dataTable.ext.buttons.newDetailCode = {
				    text: '신규등록',
				    action: function ( e, dt, node, config ) {				        
				    	$("#detailCodeDetail").show(0,viewDtl.onDetailDetailInit);
				    	$("#detailCodeDetail #grpCd").val($("#groupCodeDetail #grpCd").val());
				    	
				    }
				};
				
				viewDtl.selectDetailTableData();
				viewDtl.detailCodeTable = $('#dataTables-detailCode').dataTable();
				
				$('#dataTables-detailCode tbody').on('click', 'tr', function () {
					data = viewDtl.detailCodeTable.fnGetData(this);
			        viewDtl.selectOneData(data.dtlCd, "detailCode");
			    } );
			}		

			, selectDetailTableData : function() {
				var table = $('#dataTables-detailCode').DataTable(
						{
							dom: 'lBfrtip',
					        buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} , 'newDetailCode' ],				        
					        columnDefs: [ { visible: false, targets: [4,5,6,7] } ],
							processing : true,
							serverSide : true,
							bFilter: false,
							autoWidth: true,
							ordering: false,
							iDisplayLength: 10,
							select: true,
							deferLoading: 0, 
							aoColumns: [
							        { data: 'grpCd' },
							        { data: 'dtlCd' },
							        { data: 'dtlCdNm'},
							        { data: 'dtlCdDesc' },
							        { data: 'rgtId' },
							        { data: 'rgtTm' },
							        { data: 'uptId' },
							        { data: 'uptTm' }
							],
							sAjaxSource : G_CONTEXT_PATH+"/detailCode",							
							fnServerData : function(sSource, aoData, fnCallback, oSettings) {
																
								$("#detailCodeDetail").hide(0, view.onDetailDetailInit);
								aoData.push({"name" : "grpCd",	"value" : $("#groupCodeDetail #grpCd").val() });

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
			
			, selectOneData : function(dtlCd){		
				viewDtl.onDetailDetailInit();
				var reqData= new Object();		
				reqData.grpCd = $("#groupCodeDetail #grpCd").val();
				
				common.ajax({
					  		url : G_CONTEXT_PATH + "/detailCode/" + dtlCd
					  		, type : "GET"
							, data  : reqData
							, success : viewDtl.selectOneDataCallBack
				});
			}			
			, selectOneDataCallBack : function(json){
				
				$(json.detail).each(function(idx, itm) {
					$("#detailCodeDetail #isDetailView").val('true');
					$("#detailCodeDetail #grpCd").val(itm.grpCd);
					$("#detailCodeDetail #dtlCd").val(itm.dtlCd);
					$("#detailCodeDetail #dtlCdNm").val(itm.dtlCdNm);
					$("#detailCodeDetail #dtlCdDesc").val(itm.dtlCdDesc); 
							
				});
				
				$("#detailCodeDetail").show();
				
				
			}
			
			, insertData : function() {
				
				var reqData = $('form[name="df"]').serializeArray();
				console.log(reqData)
				common.ajax({
				  			url : G_CONTEXT_PATH + "/detailCode"
					  		, type : "POST"
							, data  : reqData 
							, success : viewDtl.insertDataCallBack
				});
			}
			, insertDataCallBack : function(json){
				if ( json.status == 200 ) {					
					viewDtl.detailCodeTable.fnReloadAjax();
					
				} else {
					alert(json.msg);
				}	
			}
			, modifyData : function() {
				var reqData = $('form[name="df"]').serializeArray();
				var dtlCd = $("#detailCodeDetail #dtlCd").val() ;
				
				common.ajax({
				  			url : G_CONTEXT_PATH+"/detailCode/" + dtlCd
					  		, type : "PUT"
							, data  : reqData 
							, success : viewDtl.modifyDataCallBack
				});	
				
			}
			, modifyDataCallBack : function(json){
				try {
					if ( json.status == 200 ) {
						viewDtl.detailCodeTable.fnReloadAjax();
					} else {
						alert(json.msg);
					}	
				}
				catch (e) {
					alert(e);
				}
			}
			, deleteData : function() {
				var reqData = $('form[name="df"]').serializeArray();
				console.log(reqData)
				var grpCd = $("#detailCodeDetail #grpCd").val() ;
				var dtlCd = $("#detailCodeDetail #dtlCd").val() ;
				common.ajax({
							url : G_CONTEXT_PATH+"/detailCode/" + grpCd + "/"+dtlCd
					  		, type : "DELETE"
					  		, data  : reqData 
							, success : viewDtl.deleteDataCallBack
				});		
			}
			, deleteDataCallBack : function(json){
				try {
					if ( json.status == 200 ) {
						alert('삭제 완료되었습니다.');
						viewDtl.detailCodeTable.fnReloadAjax();
					} else {
						alert(json.msg);
					}	
				}
				catch (e) {
					alert(e);
				}
			}

			, onDetailDetailInit : function() {	
				$("#detailCodeDetail #isDetailView").val('');
				$("#detailCodeDetail #grpCd").val('');
				$("#detailCodeDetail #dtlCd").val('');
				$("#detailCodeDetail #dtlCdNm").val('');
				$("#detailCodeDetail #dtlCdDesc").val(''); 
			}
			
			
		};
	
	
	
	
	$(function() {
		view.onLoadEvent();
		viewDtl.onLoadEvent();
	});