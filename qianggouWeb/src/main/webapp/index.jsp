<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Nike_抢购工具</title>
</head>
<body>

	<form
		action="<%=request.getContextPath()%>/servlet/ReloadUserInfoServlet"
		method="post" enctype="multipart/form-data">
		<div align="left">
			<br />
			<fieldset style='width: 80%'>
				<br />
				<legend>加载用户信息</legend>
				<div align="left">
					<input type="file" name="userfile" align="left">
				</div>
				<br />
				<div align="left">
					<input type="submit" id="loginid" value="用户登录" onclick="check(this.value)">
				</div>
				<br />
				<div align="left">
					登录结果<br />
					<br />
					<div>
						<span id="result1"><%=request.getAttribute("arg0")%></<span>
					</div>

				</div>
				<br />

			</fieldset>
		</div>
	</form>
	<br />
	<br />
	<form
		action="<%=request.getContextPath()%>/servlet/QianggouServlet"
		method="post">
		<div align="left">
			<br />
			<fieldset style='width: 80%'>
				<br />
				<legend>抢购</legend>
				<div align="left">
					<div align="left">抢购地址</div>
					<div>
						<input type="text" name="productUrl" id="urlid">
					</div>
				</div>
				<div align="left">
					<input type="submit" value="准备抢购">
				</div>
				<br />
				<div align="left">
					抢购结果<br />
					<br />
					<div>
						<span id="result2"></<span>
					</div>
				</div>
				<br />

			</fieldset>
		</div>
	</form>



	<input type="button" id="btn" value="免费获取验证码" />
	<input type="button" id="btn1" value="免费获取验证码" />
	<script type="text/javascript">
		function check(str)
		{
		   document.getElementById("str").value = "正在登录用户，请等待。";
		}
	</script>
</body>
</html>