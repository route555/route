var view = {
			onLoadEvent : function() {
		
				view.selectOneData();

			}
			
		, selectOneData : function(prsnNo){
			common.ajax({
				  		url : G_CONTEXT_PATH+"/dashBoard"
				  		, type : "GET"
						, success : view.selectOneDataCallBack
			});
		}
		, selectOneDataCallBack : function(json){
			
			
			$(json.prjt).each(function(idx, itm) {
				$.each(itm, function(k, v) {
					$("#"+k).html(v);
					
					
					
				});
			});
			
			$(json.saleCont).each(function(idx, itm) {
				$.each(itm, function(k, v) {
					if (k.indexOf("Amt") > -1) {
						v = common.toDotNumber(v);
					}
					$("#"+k).html(v);
				});
			});
			
			$(json.purchaseCont).each(function(idx, itm) {
				$.each(itm, function(k, v) {
					
					if (k.indexOf("Amt") > -1) {
						v = common.toDotNumber(v);
					}
					$("#"+k).html(v);
					
				});
			});
			
			$(json.saleMgt).each(function(idx, itm) {
				$.each(itm, function(k, v) {
					if (k.indexOf("Amt") > -1) {
						v = common.toDotNumber(v);
					}
					$("#"+k).html(v);
				});
			});
			
			$(json.purchaseMgt).each(function(idx, itm) {
				$.each(itm, function(k, v) {
					if (k.indexOf("Amt") > -1) {
						v = common.toDotNumber(v);
					}
					$("#"+k).html(v);
				});
			});
			
		
		}
	
	};

	$(function() {
		view.onLoadEvent();
	});
	
	
	