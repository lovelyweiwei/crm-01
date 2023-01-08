<%--
  Created by IntelliJ IDEA.
  User: 86199
  Date: 2023/1/5
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <title>Title</title>
</head>
<body>

<script>

    $.ajax({

        url : "",
        data : {

        },
        type : "",
        dataType : "json",
        success : function (data) {


        }

    })

    //创建事件，当前系统时间
    String createTime = DateTimeUtil.getSysTime();
    //创建人，当前登录用户
    String createBy = ((User) request.getSession().getAttribute("user")).getName();

</script>

</body>
</html>
