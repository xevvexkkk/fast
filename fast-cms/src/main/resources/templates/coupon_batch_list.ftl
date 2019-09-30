<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>优惠券使用管理</title>

	<link href="/plugins/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="/plugins/material-design-iconic-font-2.2.0/css/material-design-iconic-font.min.css" rel="stylesheet"/>
	<link href="/plugins/bootstrap-table-1.11.0/bootstrap-table.min.css" rel="stylesheet"/>
	<link href="/plugins/waves-0.7.5/waves.min.css" rel="stylesheet"/>
	<link href="/plugins/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>
	<link href="/plugins/My97DatePicker/skin/WdatePicker.css" rel="stylesheet"/>
<#--	<link href="/plugins/select2/css/select2.min.css" rel="stylesheet"/>-->

	<link href="/css/common.css" rel="stylesheet"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
<#--		<a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 重置状态</a>-->
<#--		<a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑优惠券</a>-->
	</div>
	<table id="table"></table>
</div>
<!-- 新增 -->

<script src="/plugins/jquery.1.12.4.min.js"></script>
<script src="/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="/plugins/bootstrap-table-1.11.0/bootstrap-table.min.js"></script>
<script src="/plugins/bootstrap-table-1.11.0/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/plugins/waves-0.7.5/waves.min.js"></script>
<script src="/plugins/jquery-confirm/jquery-confirm.min.js"></script>
<script src="/plugins/My97DatePicker/WdatePicker.js"></script>
<#--<script src="/plugins/select2/js/select2.min.js"></script>-->

<script src="/js/common.js"></script>
<script>
var $table = $('#table');
$(function() {
	// $('.js-example-basic-single').select2();
	$(document).on('focus', 'input[type="text"]', function() {
		$(this).parent().find('label').addClass('active');
	}).on('blur', 'input[type="text"]', function() {
		if ($(this).val() == '') {
			$(this).parent().find('label').removeClass('active');
		}
	});
	// bootstrap table初始化
	// http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
	$table.bootstrapTable({
		url: '/coupon/batch/list',
		height: getHeight(),
		striped: true,
		search: true,
		searchOnEnterKey: true,
		showRefresh: true,
		showToggle: true,
		showColumns: true,
		minimumCountColumns: 2,
		showPaginationSwitch: true,
		clickToSelect: true,
		detailView: true,
		detailFormatter: 'detailFormatter',
		pagination: true,
		paginationLoop: false,
		classes: 'table table-hover table-no-bordered',
		//sidePagination: 'server',
		//silentSort: false,
		smartDisplay: false,
		idField: 'id',
		sortName: 'id',
		sortOrder: 'desc',
		escape: true,
		searchOnEnterKey: true,
		idField: 'systemId',
		maintainSelected: true,
		toolbar: '#toolbar',
		columns: [
			{field: 'state', checkbox: true},
			{field: 'id', title: '编号', sortable: true, halign: 'center'},
			{field: 'couponId', title: '优惠券编码', sortable: true, halign: 'center'},
			{field: 'batchId', title: '批次编号', sortable: true, halign: 'center'},
			{field: 'code', title: '电子码', sortable: true, halign: 'center'},
			{field: 'statusDesc', title: '状态', sortable: true, halign: 'center'},
			{field: 'giveDate', title: '发券日期', sortable: true, halign: 'center'},
			{field: 'useDate', title: '使用日期', sortable: true, halign: 'center'},

			{field: 'customId', title: '客户编号', sortable: true, halign: 'center'},
			{field: 'action', title: '操作', halign: 'center', align: 'center', clickToSelect: false,formatter: 'actionFormatter'}
		]
	}).on('all.bs.table', function (e, name, args) {
		$('[data-toggle="tooltip"]').tooltip();
		$('[data-toggle="popover"]').popover();  
	});
});
function actionFormatter(value, row, index) {
	if (row.lockStatus !== true) {
		return ['<a class="edit ml10" onclick="resetStatus(' + row.id +')"' + ' data-toggle="tooltip" title="重置"><i class="glyphicon glyphicon-edit"></i></a>　']
	}
}
function wrapLockStatus(value, row, index) {
	if(row.lockStatus === true){
		return "已锁定";
	}
	return '未锁定';

}

function resetStatus(id){
	$.ajax({
		url: '/coupon/resetBatchStatus',
		data: {id:id,lockStatus:true},
		success: function (res) {
			$("#table").bootstrapTable('refresh');
		}
	})
}

	function actionFee(value, row, index) {
		return value / 100;
	}

	window.actionEvents = {
		'click .like': function (e, value, row, index) {
			alert('You click like icon, row: ' + JSON.stringify(row));
			console.log(value, row, index);
		},
		'click .ml10': function (e, value, row, index) {
			// alert('You click edit icon, row: ' + JSON.stringify(row));
			$.ajax({
				url: '/coupon/doEdit',
				data: {id: value.id, lockStatus: false},
				success: function (res) {
					$.alert(res.msg);
				}
			});

			console.log(value, row, index);
		},
		'click .remove': function (e, value, row, index) {
			alert('You click remove icon, row: ' + JSON.stringify(row));
			console.log(value, row, index);
		}
	};

	function detailFormatter(index, row) {
		var html = [];
		$.each(row, function (key, value) {
			html.push('<p><b>' + key + ':</b> ' + value + '</p>');
		});
		return html.join('');
	}

// 新增
	function createAction() {
		$.confirm({
			type: 'dark',
			animationSpeed: 300,
			title: '新增系统',
			content: $('#createDialog').html(),
			onContentReady: function () {
				// $('#type').select2();
				// $('#productSort').select2();

				// initialize the plugin when the model opens.
			},
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						$.ajax({
							url: '/coupon/doEdit',
							data: this.$content.find('#edit').serialize(),
							success: function (resData) {
								if (resData.success === true) {
									$.alert(resData.message);
									$("#table").bootstrapTable('refresh');
								}
							}
						});
					}
				},
				cancel: {
					text: '取消',
					btnClass: 'waves-effect waves-button'
				}
			}
		});
	}

// 编辑
	function updateAction() {
		var rows = $table.bootstrapTable('getSelections');
		if (rows.length !== 1) {
			$.confirm({
				title: false,
				content: '请选择一条记录！',
				autoClose: 'cancel|3000',
				backgroundDismiss: true,
				buttons: {
					cancel: {
						text: '取消',
						btnClass: 'waves-effect waves-button'
					}
				}
			});
		} else {
			var one = $table.bootstrapTable('getSelections')[0];
			if(one.lockStatus === true){
				$.alert('不能编辑已锁定的优惠券');
				return
			}
			var id = one.id;
			var name = one.name;
			var num = one.num;
			var productSortDesc = one.productSortDesc;
			var typeDesc = one.typeDesc;
			var statusDesc = one.statusDesc;
			var startDate = one.startDate;
			var endDate = one.endDate;
			$("#productSort option").each(function () {
				if ($(this).text() === productSortDesc) {
					$(this).attr("selected", true);
				}
			});
			$("#type option").each(function () {
				if ($(this).text() === typeDesc) {
					$(this).attr("selected", true);
				}
			});
			$("#status option").each(function () {
				if ($(this).text() === statusDesc) {
					$(this).attr("selected", true);
				}
			});
			$.confirm({
				type: 'blue',
				animationSpeed: 300,
				title: '编辑系统',
				content: $('#createDialog').html(),
				onContentReady: function () {
					this.$content.find('#name').val(name);
					this.$content.find('#id').val(id);
					this.$content.find('#num').val(num);
					this.$content.find('#startDate').val(startDate);
					this.$content.find('#endDate').val(endDate);
					this.$content.find('#typeDesc').val(typeDesc);
					this.$content.find('#statusDesc').val(statusDesc);
					this.$content.find('label').addClass("active");
				},
				buttons: {
					confirm: {
						text: '确认',
						btnClass: 'waves-effect waves-button',
						action: function () {
							$.ajax({
								url: '/coupon/doEdit',
								data: this.$content.find('#edit').serialize(),
								success: function (resData) {
									$.alert(resData.message);
									$("#table").bootstrapTable('refresh');
								}
							});
						}
					},
					cancel: {
						text: '取消',
						btnClass: 'waves-effect waves-button'
					}
				}
			});
		}
	}

// 删除
	function deleteAction() {
		var rows = $table.bootstrapTable('getSelections');
		if (rows.length == 0) {
			$.confirm({
				title: false,
				content: '请至少选择一条记录！',
				autoClose: 'cancel|3000',
				backgroundDismiss: true,
				buttons: {
					cancel: {
						text: '取消',
						btnClass: 'waves-effect waves-button'
					}
				}
			});
		} else {
			$.confirm({
				type: 'red',
				animationSpeed: 300,
				title: false,
				content: '确认删除该系统吗？',
				buttons: {
					confirm: {
						text: '确认',
						btnClass: 'waves-effect waves-button',
						action: function () {
							var ids = new Array();
							for (var i in rows) {
								ids.push(rows[i].systemId);
							}
							$.alert('删除：id=' + ids.join("-"));
						}
					},
					cancel: {
						text: '取消',
						btnClass: 'waves-effect waves-button'
					}
				}
			});
		}

	}
</script>
</body>
</html>