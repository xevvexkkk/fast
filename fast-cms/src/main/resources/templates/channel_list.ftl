<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>渠道管理</title>

	<link href="/plugins/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="/plugins/material-design-iconic-font-2.2.0/css/material-design-iconic-font.min.css" rel="stylesheet"/>
	<link href="/plugins/bootstrap-table-1.11.0/bootstrap-table.min.css" rel="stylesheet"/>
	<link href="/plugins/waves-0.7.5/waves.min.css" rel="stylesheet"/>
	<link href="/plugins/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>
	<link href="/plugins/select2/css/select2.min.css" rel="stylesheet"/>

	<link href="/css/common.css" rel="stylesheet"/>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<a class="waves-effect waves-button" href="javascript:;" onclick="createAction()"><i class="zmdi zmdi-edit"></i> 新增渠道</a>
		<a class="waves-effect waves-button" href="javascript:;" onclick="updateAction()"><i class="zmdi zmdi-edit"></i> 编辑渠道</a>
	</div>
	<table id="table"></table>
</div>
<!-- 新增 -->
<div id="createDialog" class="crudDialog" hidden>
	<form id="edit">
		<div class="form-group">
			<input type="hidden" id="id" name="id">
			<label for="channelName">渠道名称</label>
			<input id="channelName" type="text" name="channelName" class="form-control" required="required">
		</div>

	</form>
</div>
<script src="/plugins/jquery.1.12.4.min.js"></script>
<script src="/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="/plugins/bootstrap-table-1.11.0/bootstrap-table.min.js"></script>
<script src="/plugins/bootstrap-table-1.11.0/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/plugins/waves-0.7.5/waves.min.js"></script>
<script src="/plugins/jquery-confirm/jquery-confirm.min.js"></script>
<script src="/plugins/select2/js/select2.min.js"></script>

<script src="/js/common.js"></script>
<script>
var $table = $('#table');
$(function() {
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
		url: '/channel/list',
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
			{field: 'id', title: '渠道编号', sortable: true, halign: 'center'},
			{field: 'channelName', title: '渠道名称', sortable: true, halign: 'center'},
			{field: 'action', title: '操作', halign: 'center', align: 'center', clickToSelect: false}
		]
	}).on('all.bs.table', function (e, name, args) {
		$('[data-toggle="tooltip"]').tooltip();
		$('[data-toggle="popover"]').popover();  
	});
});

function detailFormatter(index, row) {
	var html = [];
	$.each(row, function (key, value) {
		html.push('<p><b>' + key + ':</b> ' + value + '</p>');
	});
	return html.join('');
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
		var channelName = one.channelName;
		var id = one.id;
		$.confirm({
			type: 'blue',
			animationSpeed: 300,
			title: '编辑系统',
			content: $('#createDialog').html(),
			onContentReady: function () {
				this.$content.find('#channelName').val(channelName);
				this.$content.find('#id').val(id);
				this.$content.find('label').addClass("active");
			},
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						$.ajax({
							url: '/channel/doEdit',
							data: this.$content.find('#edit').serialize(),
							method: 'POST',
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

// 新增
function createAction() {
		$.confirm({
			type: 'blue',
			animationSpeed: 300,
			title: '新增系统',
			content: $('#createDialog').html(),
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						$.ajax({
							url: '/channel/doEdit',
							data: this.$content.find('#edit').serialize(),
							method: 'POST',
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

</script>
</body>
</html>