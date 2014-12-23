<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title>上传图片</title>
</head>
<body>
	<form action="file/upload" method="post" enctype="multipart/form-data">
		<input type="file" name="file" /> <input type="submit" value="Submit" />
	</form>
</body>
</html>
