
var view = {
		userTable : '',	
		selectedUserId : '',	
		onLoadEvent : function() {
			
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
								
			view.selectUserTableData();
			view.userTable = $('#dataTables-user').dataTable();
			
		}		

		, selectUserTableData : function() {
			var table = $('#dataTables-user').DataTable(
					{
						dom: 'lfrtip',
				        //buttons: [{extend: 'colvis', postfixButtons: [ 'colvisRestore' ]}  ],				        
				        columnDefs: [ { visible: false, targets: [0,4] } ],
						processing : true,
						serverSide : true,
						bFilter: false,
						autoWidth: true,
						ordering: false,
						select: true,
						iDisplayLength: 10,					
						aoColumns: [
						        { data: 'userId' },
						        { data: 'loginId' },
						        { data: 'userName' },
						        { data: 'email' },
						        { data: 'allowMenu' }
						],						
						sAjaxSource : G_CONTEXT_PATH+"/user",
						fnServerData : function(sSource, aoData, fnCallback, oSettings) {							
							var reqData = $('form[name="sf"]').serializeArray();
							$.merge(aoData,reqData);
							selectedUserId='';
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
			
			$('#dataTables-user tbody').on('click', 'tr', function () {				
				var data = table.row( this ).data();
				view.selectedUserId = data.userId;
				
				viewMenu.bindAllowMenu(data.allowMenu);	
		    } );
			
		}				
					
	};

	
	var viewMenu = {			
			menuTable : '',
			onLoadEvent : function() {
				
				$("#btnSave").unbind('click');
				$("#btnSave").click( function() {	
					
					if(view.selectedUserId == ''){
						alert('선택된 유저가 없습니다.');
						return false;
					}
					
					viewMenu.modifyData();
				});
				
				
				$.fn.dataTable.ext.buttons.allSelectTrue = {
				    text: '전체선택',
				    action: function ( e, dt, node, config ) {				        
				    	$('input[type=checkbox]').prop('checked',true);

				    }
				};
				
				$.fn.dataTable.ext.buttons.allSelectFalse = {
					    text: '전체해제',
					    action: function ( e, dt, node, config ) {				        
					    	$('input[type=checkbox]').prop('checked',false);
					    }
					};
				
				viewMenu.selectMenuTableData();
				viewMenu.menuTable = $('#dataTables-menu').dataTable();
				
				$('#dataTables-menu tbody').on('click', 'tr', function (e) {
					//data = viewMenu.detailCodeTable.fnGetData(this);
			        //viewMenu.selectOneData(data.dtlCd, "detailCode");
				
						var chk = $(this).closest("tr").find("input:checkbox").get(0);
						if (e.target != chk) {
							chk.checked = !chk.checked;
						}
					
					
			    } );
			}		

			, selectMenuTableData : function() {
				var table = $('#dataTables-menu').DataTable(
						{
							dom: 'Bt',
					        buttons: [ 'allSelectTrue','allSelectFalse' ],				        
					        columnDefs: [ { visible: false, targets: [0] },{ className: "text-center", "targets": [ 3 ] } ],
							processing : true,
							serverSide : true,
							bFilter: false,
							autoWidth: true,
							ordering: false,
							iDisplayLength: 1000,
							//select: true,
							// deferLoading: 0, 
							
							aoColumns: [
							        { data: 'menuId' },
							        { data: 'menuName' },
							        { data: 'menuDesc'},
							        {
						                data:   "menuId",
						                render: function ( data, type, row ) {
						                    if ( type === 'display' ) {
						                        return '<input type="checkbox" class="editor-active" name="allow-check" menuId="'+data+'">';
						                    }
						                    return data;
						                }
						            }
							],
							sAjaxSource : G_CONTEXT_PATH+"/menu",							
							fnServerData : function(sSource, aoData, fnCallback, oSettings) {
								//aoData.push({"name" : "grpCd",	"value" : $("#groupCodeDetail #grpCd").val() });

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
			, bindAllowMenu : function(allowMenu){			
				//클릭후 이벤트
				//console.log(allowMenu)
				//data = viewMenu.menuTable.fnGetData();
				//console.log(data)
				$('input[type=checkbox]').prop('checked',false);
				var menuArr = allowMenu.split(',');
				$.each(menuArr, function(index, value) { 
					$('input[menuId="'+value+'"]').click();
					
				});

				
			}
		
			, modifyData : function() {
			
				var chkVal = '';
				$("input:checked", viewMenu.menuTable.fnGetNodes()).each(function(i){
					
					if(i==0){
						chkVal += $(this).attr('menuId');
					}else{
						chkVal += ',' + $(this).attr('menuId');
					}
					
					 //console.log($(this).attr('menuId'))
				});
				
				
				
				
				
				var reqData = [];
				var userId = view.selectedUserId;
				
				reqData.push({"name" : "allowMenu",	"value" : chkVal });
				
				//reqData.test = chkVal;
				console.log(reqData, view.selectedUserId);
				//return; 
				
				
				common.ajax({
				  			url : G_CONTEXT_PATH+"/user/" + userId
					  		, type : "POST"
							, data  : reqData 
							, success : viewMenu.modifyDataCallBack
				});	
				
			
				
			}
			, modifyDataCallBack : function(json){
				try {
					if ( json.status == 200 ) {
						//viewMenu.detailCodeTable.fnReloadAjax();
						alert('저장완료');
						$('input[type=checkbox]').prop('checked',false);
						view.userTable.fnReloadAjax();
					} else {
						alert(json.msg);
					}	
				}
				catch (e) {
					alert(e);
				}
			}
			
			
		};
	
	
	
	
	$(function() {
		view.onLoadEvent();
		viewMenu.onLoadEvent();
	});