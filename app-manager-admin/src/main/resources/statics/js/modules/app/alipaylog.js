$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/alipaylog/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '通知时间', name: 'notifyTime', index: 'notify_time', width: 80 }, 			
			{ label: '通知类型', name: 'notifyType', index: 'notify_type', width: 80 }, 			
			{ label: '通知校验ID', name: 'notifyId', index: 'notify_id', width: 80 }, 			
			{ label: '商户网站唯一订单号', name: 'outTradeNo', index: 'out_trade_no', width: 80 }, 			
			{ label: '交易标题', name: 'subject', index: 'subject', width: 80 }, 			
			{ label: '支付宝交易号', name: 'tradeNo', index: 'trade_no', width: 80 }, 			
			{ label: '交易状态', name: 'tradeStatus', index: 'trade_status', width: 80 }, 			
			{ label: '卖家支付宝用户号', name: 'sellerId', index: 'seller_id', width: 80 }, 			
			{ label: '卖家支付宝账号', name: 'sellerEmail', index: 'seller_email', width: 80 }, 			
			{ label: '买家支付宝用户号', name: 'buyerId', index: 'buyer_id', width: 80 }, 			
			{ label: '买家支付宝账号', name: 'buyerEmail', index: 'buyer_email', width: 80 }, 			
			{ label: '交易金额', name: 'totalFee', index: 'total_fee', width: 80 }, 			
			{ label: '交易创建时间', name: 'gmtCreate', index: 'gmt_create', width: 80 }, 			
			{ label: '交易付款时间', name: 'gmtPayment', index: 'gmt_payment', width: 80 }, 			
			{ label: '支付状态。0：充值中；1：交易成功；2：交易失败', name: 'payStatus', index: 'pay_status', width: 80 }, 			
			{ label: '订单金额', name: 'orderMoney', index: 'order_money', width: 80 }			
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
		alipayLog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.alipayLog = {};
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
                var url = vm.alipayLog.id == null ? "app/alipaylog/save" : "app/alipaylog/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.alipayLog),
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
                        url: baseURL + "app/alipaylog/delete",
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
			$.get(baseURL + "app/alipaylog/info/"+id, function(r){
                vm.alipayLog = r.alipayLog;
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