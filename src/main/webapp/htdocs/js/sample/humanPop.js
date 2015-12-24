	var viewPop = {
		testCode : '',
		onLoadEvent : function() {
		
			//viewPop.selectCommonCode();
			//viewPop.selectCommonCodeTest();
			$("#btnSearch").unbind('click');
			$("#btnSearch").click( function() {			
				var table = $('#dataTables-example').dataTable();
				table.fnReloadAjax();
			});
			
			$("#btnSave").unbind('click');
			$("#btnSave").click( function() {				
				if($("#humanId").val()==''){
					viewPop.insertData();					
				}else{
					viewPop.modifyData();
				}
			});
			
			$("#btnDelete").unbind('click');
			$("#btnDelete").click( function() {			
				viewPop.deleteData();
			});
			
			$("#btnNew").unbind('click');
			$("#btnNew").click( function() {			
				viewPop.initDetail();
				$("#detail").show();
			});			
		
			//viewPop.selectTableData();
		}
		, selectCommonCode : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/G01"
				  		, type : "GET"
						, success : viewPop.selectCommonCodeCallBack
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
		, selectCommonCodeTest : function() {			
			common.ajax({
				  		url : G_CONTEXT_PATH+"/codes/111"
				  		, type : "GET"
						, success : viewPop.selectCommonCodeCallBackTest
			});
		}
		, selectCommonCodeCallBackTest : function(json) {
			viewPop.testCode = json.list;
		}
		, makeComboTest : function(data) {
			var el = '<select id="test">';			
			$(viewPop.testCode).each(function(i, itm){
				
				if(data==itm.dtlCd){
					el += '<option value="' + itm.dtlCd + '" selected>' + itm.dtlCdNm + '</option>';
				}else{
					el += '<option value="' + itm.dtlCd + '">' + itm.dtlCdNm + '</option>';
				}
				
				
			});
			el += '</select>';
			
			return el;
		}
		, selectTableData : function() {
			var table = $('#dataTables-example').DataTable(
					{
						//"paging": false,
						"processing" : true,
						"serverSide" : true,
						"bFilter": false,
						"autoWidth": true,
						"ordering": false,
						"iDisplayLength": 10,
						columnDefs: [ { visible: false, targets: [0] } ],
						//select:true,
						// "scrollY":        "300px",
						
					  //      "scrollCollapse": true,
						"aoColumns": [
						        { data: 'division' },
						        { data: 'name'},
						        //{ data: 'job' },
						        { data: 'job' , "render": function ( data ) { return viewPop.makeComboTest(data);} }, 
						        { data: 'email' },
						        { data: 'phone' }
						],
						"sAjaxSource" : G_CONTEXT_PATH+"/sampleHuman",
						"fnServerData" : function(sSource, aoData, fnCallback,	oSettings) {
							$("#detail").hide();
							aoData.push({
								"name" : "Input1",
								"value" : "xx"
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
			
			$('#dataTables-example tbody').on('click', 'tr', function () {
				/*
				if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        }   else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
				*/
		        var data = table.row( this ).data();
		        console.log(data);
		        //alert( 'You clicked on '+data.humanId+'\'s row' );
		        viewPop.initDetail();
		        viewPop.selectOneData(data.humanId);
		        
		    } );
			
		}
		, selectOneData : function(humanId){
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
				  		url : G_CONTEXT_PATH+"/sampleHuman/"+humanId
				  		, type : "GET"
						, data  : reqData
						, success : viewPop.selectOneDataCallBack
			});
		}
		, selectOneDataCallBack : function(json){
			//common.log( json );
			$(json.detail).each(function(idx, itm) {
				$("#humanId").val( itm.humanId);
				$("#division").val( itm.division);
				$("#name").val( itm.name);
				$("#job").val( itm.job );
				$("#email").val( itm.email);
				$("#phone").val( itm.phone);
			});
			
			$("#detail").show();
			
		}
		, insertData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			common.ajax({
			  			url : G_CONTEXT_PATH+"/sampleHuman"
				  		, type : "POST"
						, data  : reqData 
						, success : viewPop.insertDataCallBack
			});
		}
		, insertDataCallBack : function(json){
			if ( json.status == 200 ) {
				var table = $('#dataTables-example').dataTable();
				table.fnReloadAjax();
			}
			else {
				alert(json.msg);
			}	
		}
		, modifyData : function() {
			var reqData = $('form[name="f"]').serializeArray();
			var humanId=$("#humanId").val();
			
			common.ajax({
			  			url : G_CONTEXT_PATH+"/sampleHuman/" + humanId
				  		, type : "PUT"
						, data  : reqData 
						, success : viewPop.modifyDataCallBack
			});	
			
		}
		, modifyDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					var table = $('#dataTables-example').dataTable();
					table.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, deleteData : function() {
			var humanId=$("#humanId").val();
			common.ajax({
						url : G_CONTEXT_PATH+"/sampleHuman/" + humanId
				  		, type : "DELETE"
						, success : viewPop.deleteDataCallBack
			});		
		}
		, deleteDataCallBack : function(json){
			try {
				if ( json.status == 200 ) {
					alert('삭제 완료되었습니다.');
					var table = $('#dataTables-example').dataTable();
					table.fnReloadAjax();
				} else {
					alert(json.msg);
				}	
			}
			catch (e) {
				alert(e);
			}
		}
		, initDetail : function() {
			$("#humanId").val('');
			$("#division").val('');
			$("#name").val('');
			$("#job").val('');
			$("#email").val('');
			$("#phone").val('');
			
		}
	};

	$(function() {
		viewPop.onLoadEvent();
	});