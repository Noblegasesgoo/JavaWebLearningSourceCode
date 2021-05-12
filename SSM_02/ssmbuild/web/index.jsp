<%--
  Created by IntelliJ IDEA.
  User: noblegasesgoo
  Date: 2021/5/4
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>主页</title>

    <style type="text/css">
      a{
          text-decoration: none;
          color: darkslategrey;
          font-size: 18px;
      }

      h3{
          width: 180px;
          height: 38px;
          margin: 100px;
          text-align: center;
          line-height: 38px;
          background: antiquewhite;
          border-radius: 5px;
      }

    </style>
  </head>
  <body>

  <h3>
    <a href="${pageContext.request.contextPath}/book/allbooks" >进入到书籍列表</a>
  </h3>

  </body>
</html>
