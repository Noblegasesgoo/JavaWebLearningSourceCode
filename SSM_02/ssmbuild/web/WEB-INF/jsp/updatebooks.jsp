<%--
  Created by IntelliJ IDEA.
  User: noblegasesgoo
  Date: 2021/5/10
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <html>
        <head>
            <title>修改书籍</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- 引入 Bootstrap -->
            <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        </head>
<body>
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍</small>
                </h1>
            </div>
        </div>
    </div>



    <form action="${pageContext.request.contextPath}/book/updatebooks" method="post">
        <%--
            我们再修改操作的过程中，发现了没有办法修改成功，于是初步考虑事务问题，结果并不是
            而是我们的sql语句需要根据id来修改，而我们后台没有取到对应的id！！！！
            所以我们在前端自行使用隐藏域来完成前台对后台的id传递。
        --%>

        <input type="hidden" name="bookID" value="${qBooks.bookID}">
        <div class="form-group">
            <label>书籍名称：</label>
            <input type="text" name="bookName" class="form-control" value="${qBooks.bookName}" required>
        </div>
        <div class="form-group">
            <label>书籍数量：</label>
            <input type="text" name="bookCounts" class="form-control" value="${qBooks.bookCounts}" required>
        </div>
        <div class="form-group">
            <label>书籍详情：</label>
            <input type="text" name="detail" class="form-control" value="${qBooks.detail}" required>
        </div>

        <div class="form-group">
            <input type="submit" class="form-control" value="修改">
        </div>
    </form>

</div></title>
</head>

</body>
</html>
