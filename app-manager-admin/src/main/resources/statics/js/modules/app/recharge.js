$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/recharge/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '订单id/充值记录id', name: 'orderId', index: 'order_id', width: 80 }, 			
			{ label: '会员id', name: 'memberId', index: 'member_id', width: 80 }, 			
			{ label: '充值金额', name: 'rechargeAmount', index: 'recharge_amount', width: 80 }, 			
			{ label: '充值时间', name: 'rechargeTime', index: 'recharge_time', width: 80 }, 			
			{ label: '付款方式。1:微信；2：支付宝', name: 'payType', index: 'pay_type', width: 80 }, 			
			{ label: '订单号', name: 'orderNumber', index: 'order_number', width: 80 }, 			
			{ label: '交易流水号', name: 'transactionId', index: 'transaction_id', width: 80 }, 			
			{ label: '是否发送短信（0表示不发送，1表示发送）', name: 'whetherSms', index: 'whether_sms', width: 80 }, 			
			{ label: '充值状态。0：待支付；1：充值中；2：交易成功；3：交易失败；4：已退款；5：交易关闭', name: 'rechargeStatus', index: 'recharge_status', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '购买的下载次数包', name: 'goodsId', index: 'goods_id', width: 80 }, 			
			{ label: '商品价格', name: 'goodsPrice', index: 'goods_price', width: 80 }			
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
		recharge: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.recharge = {};
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
                var url = vm.recharge.id == null ? "app/recharge/save" : "app/recharge/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.recharge),
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
                        url: baseURL + "app/recharge/delete",
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
			$.get(baseURL + "app/recharge/info/"+id, function(r){
                vm.recharge = r.recharge;
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