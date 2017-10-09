<%--
  Created by IntelliJ IDEA.
  User: killeryuan
  Date: 2017/2/19
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body bgcolor="">

<form action="/user/upload.do?current_path=/test" method="post" enctype="multipart/form-data">
    <input type="file" name = "file1"><br>
    <input type="submit" value="upload">


</form>
<a href="showAllFile.do?path=files">查看所有已上传的文件</a>
</body>
</html>
