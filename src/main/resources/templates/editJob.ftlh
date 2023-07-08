<!doctype html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h1 align="center"> 单机版动态定时任务编辑页面 </h1>
		<div>
			<form action="/dynamicSchedule/updateSchedule" method="post">
				<table class="table">
					<tbody>
					<thead>
						<tr>
							<th>属性</th>
							<th>数据</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>taskId</th>
							<td><input type="text" name="taskId" value="${job.taskId}"/></td>
						</tr>
						<tr>
							<th>表达式</th>
							<td><input type="text" name="cronExpression" value="${job.cronExpression}" /></td>
						</tr>
					</tbody>
				</table>
				<button type="submit" class="btn btn-primary">提交</button>
			</form>
		</div>
	</div>
</body>
</html>