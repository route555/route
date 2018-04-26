	var view = {
			userTable : '',	
			onLoadEvent : function() {
		
				//view.selectCommonCode();

				$('#searchBox input').keypress(function(e) {
				    if (e.keyCode == 13){			    	
				    	$("#btnSearch").click();
						return false;
				    }         
				});
				
				$("#btnSearch").unbind('click');
				$("#btnSearch").click( function() {
					view.userTable.fnReloadAjax();
				});
				
				$("#btnSave").unbind('click');
				$("#btnSave").click( function() {
					//if(!view.validator()) return false;
					
					if($("#userId").val()==''){
						view.insertData();
					}else{
						view.modifyData();
					}
				});
				
				$("#btnDelete").unbind('click');
				$("#btnDelete").click( function() {			
					view.deleteData();
				});
				
				$.fn.dataTable.ext.buttons.newUser = {
					    text: '신규등록',
					    action: function ( e, dt, node, config ) {				        
					    	$("#detail").show(0, view.initDetail);
					    }
				};
					
				view.selectTableData();
				view.userTable = $('#dataTables-user').dataTable();
				
				$('#dataTables-user tbody').on('click', 'tr', function () {
					view.initDetail();
					data = view.userTable.fnGetData(this);
					view.selectOneData(data.userId);
			    } );
			}
			, selectCommonCode : function() {			
				common.ajax({
					  		url : G_CONTEXT_PATH+"/codes/xxx"
					  		, type : "GET"
							, success : view.selectCommonCodeCallBack
				});
			}
			, selectCommonCodeCallBack : function(json) {
				var el = '';			
				$(json.list).each(function(i, itm){				
					el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				});
				
				$("select:eq(0)").append(el);
				$("select:eq(0) option:eq(0)").attr("selected", "selected");
				$("select:eq(0) option:eq(0)").trigger('change');	
			}

			, makeComboTest : function(data) {
				/*
				var el = '<select id="test">';			
				$(view.testCode).each(function(i, itm){
					
					if(data==itm.dtlCd){
						el += '<option value="' + itm.dtlCd + '" selected>' + itm.dtlCdNm + '</option>';
					}else{
						el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
					}
					
					
				});
				el += '</select>';
				
				return el;
				*/
			}
			, selectTableData : function() {
				var table = $('#dataTables-user').DataTable(
						{
							dom: 'lBfrtip',
							buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]} , 'newUser', 'excel', 'print' ],				        
							"paging": true,
							"processing" : true,
							"serverSide" : true,
							"bFilter": false,
							"autoWidth": true,
							"ordering": false,
							"iDisplayLength": 10,
							columnDefs: [ { visible: false, targets: [0,1] } ],
							select:true,
							"aoColumns": [
							        { data: 'userId' },
							        { data: 'tenantId'},
							        { data: 'loginId' },
							        { data: 'userName' },
							        { data: 'phone' },
							        { data: 'email' },
							        { data: 'rightsType' },
							        { data: 'isUse' }
							        //{ data: 'job' , "render": function ( data ) { return view.makeComboTest(data);} }, 
							],
							"sAjaxSource" : G_CONTEXT_PATH+"/user",
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
		, selectOneData : function(userId){
			common.ajax({
				  		url : G_CONTEXT_PATH+"/user/"+userId
				  		, type : "GET"
						, success : view.selectOneDataCallBack
			});
		}
		, selectOneDataCallBack : function(json){
			$(json.detail).each(function(idx, itm) {
				$("#userId").val( itm.userId);
				//$("#tenantId").val( itm.tenantId);
				$("#loginId").val( itm.loginId);
				$("#userName").val( itm.userName );
				$("#phone").val( itm.phone);
				$("#email").val( itm.email);
				$("#rightsType").val( itm.rightsType);
				$("#isUse").val( itm.isUse );
			});
			$("#detail").show();
		}
		, insertData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
			  			url : G_CONTEXT_PATH+"/user"
				  		, type : "POST"
						, data  : reqData 
						, success : view.insertDataCallBack
			});
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {
				view.userTable.fnReloadAjax();	
			}
			else {
				alert(json.msg);
			}	
		}
		, modifyData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			var userId=$("#userId").val();
			common.ajax({
			  			url : G_CONTEXT_PATH+"/user/" + userId
				  		, type : "POST"
						, data  : reqData 
						, success : view.modifyDataCallBack
			});	
		}
		, modifyDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					view.userTable.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, deleteData : function() {
			var userId=$("#userId").val();
			common.ajax({
						url : G_CONTEXT_PATH+"/user/" + userId
				  		, type : "DELETE"
						, success : view.deleteDataCallBack
			});		
		}
		, deleteDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('삭제 완료되었습니다.');
					view.userTable.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, initDetail : function() {
			$("#userId").val('');
			//$("#tenantId").val(''); //일단 고정
			$("#loginId").val('');
			$("#loginPw").val('');
			$("#userName").val('');
			$("#phone").val('');
			$("#email").val('');
			$("#rightsType").val('');
			$("#isUse").val('');
		}
	};

	$(function() {
		view.onLoadEvent();
	});