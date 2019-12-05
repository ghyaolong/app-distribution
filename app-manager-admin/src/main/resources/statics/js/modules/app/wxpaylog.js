$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/wxpaylog/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '会员id', name: 'memeberId', index: 'memeber_id', width: 80 }, 			
			{ label: '商户订单号', name: 'outTradeNo', index: 'out_trade_no', width: 80 }, 			
			{ label: '业务结果', name: 'resultCode', index: 'result_code', width: 80 }, 			
			{ label: '状态码', name: 'returnCode', index: 'return_code', width: 80 }, 			
			{ label: '总金额（单位：分）', name: 'totalFee', index: 'total_fee', width: 80 }, 			
			{ label: '交易类型', name: 'tradeType', index: 'trade_type', width: 80 }, 			
			{ label: '微信支付订单号', name: 'transactionId', index: 'transaction_id', width: 80 }, 			
			{ label: '交易创建时间', name: 'gmtCreate', index: 'gmt_create', width: 80 }, 			
			{ label: '交易付款时间', name: 'gmtPayment', index: 'gmt_payment', width: 80 }, 			
			{ label: '支付完成时间', name: 'timeEnd', index: 'time_end', width: 80 }, 			
			{ label: '支付状态。0：充值中；1：交易成功；2：交易失败', name: 'payStatus', index: 'pay_status', width: 80 }, 			
			{ label: '订单金额', name: 'orderMoney', index: 'order_money', width: 80 }, 			
			{ label: '交易描述', name: 'body', index: 'body', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		wxpayLog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wxpayLog = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.wxpayLog.id == null ? "app/wxpaylog/save" : "app/wxpaylog/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.wxpayLog),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "app/wxpaylog/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "app/wxpaylog/info/"+id, function(r){
                vm.wxpayLog = r.wxpayLog;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});