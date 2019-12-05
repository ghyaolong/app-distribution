$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'app/member/list',
        datatype: "json",
        colModel: [			
			/*{ label: 'id', name: 'id', index: 'id', width: 50, key: true },*/
			{ label: '用户名', name: 'userName', index: 'user_name', width: 80 }, 			
			/*{ label: '密码', name: 'password', index: 'password', width: 80 },*/
			{ label: '手机', name: 'tel', index: 'tel', width: 80 }, 			
			{ label: '真实姓名', name: 'realName', index: 'real_name', width: 80 },
			{ label: '身份证号', name: 'idNumber', index: 'id_number', width: 80 }, 			
			{ label: '详细地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: 'qq号', name: 'qq', index: 'qq', width: 80 }, 			
			{ label: '公司名称', name: 'company', index: 'company', width: 80 }, 			
			{ label: '职位', name: 'position', index: 'position', width: 80 }, 			
			{ label: '头像', name: 'avatar', index: 'avatar', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '审核状态', name: 'status', index: 'status', width: 80,formatter: function(value, options, row){
			    // 0：未审核   1：审核中  2：审核通过   3：审核驳回
			    if(value === '0'){
			        return '未审核';
                }
			    if(value === '1'){
			        return '审核中';
                }
                if(value === '2'){
                    return '审核通过';
                }
                if(value === '3'){
                    return '审核驳回';
                }
                } },
			{ label: '冻结状态', name: 'isForbidden', index: 'is_forbidden', width: 80 }, 			
			{ label: '剩余下载次数', name: 'downloadCount', index: 'download_count', width: 80 }/*,
			{ label: '正面照', name: 'frontPhotoPath', index: 'front_photo_path', width: 80 },
			{ label: '背面照', name: 'backPhotoPath', index: 'back_photo_path', width: 80 },
			{ label: '手持照', name: 'handPhotoPath', index: 'hand_photo_path', width: 80 },
			{ label: '正面照oss路径', name: 'frontPhotoOssPath', index: 'front_photo_oss_path', width: 80 },
			{ label: '背面照oss路径', name: 'backPhotoOssPath', index: 'back_photo_oss_path', width: 80 },
			{ label: '手持照oss路径', name: 'handPhotoOssPath', index: 'hand_photo_oss_path', width: 80 }	*/
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
		member: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.member = {};
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
                var url = vm.member.id == null ? "app/member/save" : "app/member/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.member),
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
                        url: baseURL + "app/member/delete",
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
			$.get(baseURL + "app/member/info/"+id, function(r){
                vm.member = r.member;
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