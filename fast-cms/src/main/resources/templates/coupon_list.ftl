<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>优惠券信息管理</title>

	<link href="/plugins/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="/plugins/material-design-iconic-font-2.2.0/css/material-design-iconic-font.min.css" rel="stylesheet"/>
	<link href="/plugins/bootstrap-table-1.11.0/bootstrap-table.min.css" rel="stylesheet"/>
	<link href="/plugins/waves-0.7.5/waves.min.css" rel="stylesheet"/>
	<link href="/plugins/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>
	<link href="/plugins/My97DatePicker/skin/WdatePicker.css" rel="stylesheet"/>
	<link href="/plugins/select2/css/select2.min.css" rel="stylesheet"/>

	<link href="/css/common.css" rel="stylesheet"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-plus"></i> 新增优惠券</a>
		<a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑优惠券</a>
	</div>
	<table id="table"></table>
</div>
<!-- 新增 -->
<div id="createDialog" class="crudDialog" hidden>
	<form id="edit">
		<input type="hidden" name="id" id="id">
		<div class="form-group">
			<label for="name">优惠券名称</label>
			<input id="name" type="text" name="name" class="form-control" required="required">
		</div>
		<div class="form-group">
			<label for="productSort">产品类型</label>
			<select id="productSort" name="productSort" class="form-control" placeholder="产品类型">
				<#list products as product>
				<option value="${product}">${product.getName()}</option>
				</#list>
			</select>
		</div>
		<div class="form-group">
			<label for="type">优惠券类型</label>
			<select id="type" name="type" class="form-control" placeholder="优惠券类型">
				<#list types as type>
				<option value="${type}">${type.getName()}</option>
			</#list>
			</select>
		</div>
		<div class="form-group">
			<label for="num">天数/次数/金额</label>
			<input id="num" name="num" type="text" class="form-control" required="required">
		</div>

		<div class="form-group">
			<label for="status">优惠券状态</label>
			<select id="status" name="status" class="form-control" placeholder="优惠券状态">
				<#list statuses as status>
					<option value="${status}">${status.getName()}</option>
				</#list>
			</select>
		</div>

		<div class="form-group">
			<label for="channel">渠道</label>
			<select id="channel" name="channel" class="form-control" placeholder="优惠券状态">
				<#list channels as channel>
					<option value="${channel.getId()}">${channel.getChannelName()}</option>
				</#list>
			</select>
		</div>
		<div class="form-group">
			<label for="startDate">开始日期</label>
			<input id="startDate" name="startDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" class="form-control" required="required">
		</div>
		<div class="form-group">
			<label for="endDate">结束日期</label>
			<input id="endDate" type="text" name="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" class="form-control" required="required">
		</div>
	</form>
</div>

<div id="generateDialog" class="generateDialog" hidden>
	<form id="generate">
		<input type="hidden" name="id" id="id">
		<div class="form-group">
			<label for="name">优惠券名称</label>
			<input id="name" type="text" name="name" class="form-control" required="required">
		</div>

		<div class="form-group">
			<label for="genCount">生成数量（不能超过1W）</label>
			<input id="genCount" type="text" name="genCount" class="form-control" required="required">
		</div>

	</form>
</div>
<script src="/plugins/jquery.1.12.4.min.js"></script>
<script src="/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="/plugins/bootstrap-table-1.11.0/bootstrap-table.min.js"></script>
<script src="/plugins/bootstrap-table-1.11.0/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/plugins/waves-0.7.5/waves.min.js"></script>
<script src="/plugins/jquery-confirm/jquery-confirm.min.js"></script>
<script src="/plugins/My97DatePicker/WdatePicker.js"></script>
<script src="/plugins/select2/js/select2.min.js"></script>

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
		url: '/coupon/list',
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
			{field: 'name', title: '优惠券名称', sortable: true, halign: 'center'},
			{field: 'typeDesc', title: '优惠券类型', sortable: true, halign: 'center'},
			{field: 'productSortDesc', title: '产品类型', sortable: true, halign: 'center'},
			{field: 'num', title: '天数/次数/金额', sortable: true, halign: 'center'},
			{field: 'startDate', title: '开始日期', sortable: true, halign: 'center'},
			{field: 'endDate', title: '结束日期', sortable: true, halign: 'center'},

			{field: 'channel', title: '渠道', sortable: true, halign: 'center'},
			{field: 'channel', title: '锁定状态', sortable: true, halign: 'center',formatter:'wrapLockStatus'},
			{field: 'action', title: '操作', halign: 'center', align: 'center', clickToSelect: false,formatter: 'actionFormatter'}
		]
	}).on('all.bs.table', function (e, name, args) {
		$('[data-toggle="tooltip"]').tooltip();
		$('[data-toggle="popover"]').popover();  
	});
});
function actionFormatter(value, row, index) {
	if (row.lockStatus !== true) {
		return ['<a class="edit ml10" onclick="lockCoupon(' + row.id +')"' + ' data-toggle="tooltip" title="锁定"><i class="glyphicon glyphicon-edit"></i></a>　']
	}else {
		return ['<a class="edit ml10" onclick="generate(' + row.id +')"' + ' data-toggle="tooltip" title="生成"><i class="glyphicon glyphicon-edit"></i></a>　']
	}
}
function wrapLockStatus(value, row, index) {
	if(row.lockStatus === true){
		return "已锁定";
	}
	return '未锁定';

}

function lockCoupon(id){
	$.ajax({
		url: '/coupon/doEdit',
		data: {id:id,lockStatus:true},
		success: function (res) {
			$("#table").bootstrapTable('refresh');
		}
	})
}

function generate() {
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
		if(one.lockStatus !== true){
			$.alert('不能生成未锁定的优惠券');
			return
		}
		var id = one.id;
		var name = one.name;
		$.confirm({
			type: 'blue',
			animationSpeed: 300,
			title: '生成系统',
			content: $('#generateDialog').html(),
			onContentReady: function () {
				this.$content.find('#name').val(name);
				this.$content.find('#id').val(id);
				this.$content.find('label').addClass("active");
			},
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						$.ajax({
							url: '/coupon/generate',
							data: this.$content.find('#generate').serialize(),
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
			var productSort = one.productSort;
			var type = one.type;
			var status = one.status;
			var startDate = one.startDate;
			var endDate = one.endDate;
			var channel = one.channel;
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
					// this.$content.find('#typeDesc').val(typeDesc);
					// this.$content.find('#statusDesc').val(statusDesc);
					// this.$content.find('#statusDesc').val(productSort).select2().val(staffId).trigger('change');
					this.$content.find('#productSort').select2().val(productSort).trigger('change');

					this.$content.find('#type').select2().val(type).trigger('change');
					this.$content.find('#status').select2().val(status).trigger('change');
					this.$content.find('#channel').select2().val(channel).trigger('change');

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