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

    String id = UUIDUtil.getUUID();
    String createTime = DateTimeUtil.getSysTime();
    String createBy = ((User) request.getSession().getAttribute("user")).getName();


    $(".time").datetimepicker({
        minView: "month",
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });
</script>

</body>
</html>
