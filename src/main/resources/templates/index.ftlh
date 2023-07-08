<!doctype html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h1 align="center">单机版动态定时任务管理界面</h1>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">taskId</th>
					<th scope="col">表达式</th>
					<th scope="col">描述</th>
					<th scope="col">状态</th>
					<th scope="col"></th>
					<th scope="col"></th>
					<th scope="col"></th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<#list jobs as job>
				<tr>
					<th scope="row">${job.taskId}</th>
					<td>${job.cronExpression}</td>
					<td>${job.desc}</td>
					<td>${job.status?string("开启","关闭")}</td>
					<td><a href="/dynamicSchedule/startOne/${job.taskId}">手动执行一次</a></td>
					<td><a href="/dynamicSchedule/removeCronTask/${job.taskId}">停止 </a></td>
					<td><a href="/dynamicSchedule/startScheduleTask/${job.taskId}">开启</a></td>
					<td><a href="/dynamicSchedule/edit/${job.taskId}">编辑</a></td>
				</tr>
				</#list>
			</tbody>
		</table>
	</div>
</body>
</html>