<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script>

        $(function (){

            if (window.top != window){
                window.top.location = window.location
            }

            //页面加载完毕后，将用户文本框中的内容清空
            $("#loginAct").val("")
            $("#loginPwd").val("")

            //页面加载完毕后，让用户文本自动获得焦点
            $("#loginAct").focus()

            //为登录按钮绑定事件，执行登录操作
            $("#submitBtn").click(function (){
                login()
            })

            //为当前登录页窗口绑定敲键盘事件
            $(window).keydown(function (){
                // alert(event.keyCode)
                if (event.keyCode == 13){
                    login()
                }
            })

        })

        //普通的自定义的function方法，一定要写在$(function(){})的外面
        function login() {
            //验证账号密码是否为空
            //获取账号密码
            //将文本中的左右空格去掉，使用$.trim(文本)
            var loginAct = $.trim($("#loginAct").val())
            var loginPwd = $.trim($("#loginPwd").val())

            if (loginAct == "" || loginPwd == ""){
                $("#msg").html("<font style=\"color: #ff0000\">账号密码不能为空</font>")

                //如果账号密码为空，则需要及时强制终止该方法
                return false
            }

            $.ajax({
                url : "settings/user/login.do",
                data : {
                    "loginAct" : loginAct,
                    "loginPwd" : loginPwd
                },
                type : "post",
                dataType : "json",
                success : function (data){

                    /*
						data
							{"success":true/false,"msg":"哪错了"}
					 */

                    //如果登录成功
                    if (data.success){
                        window.location.href = "workbench/index.jsp"
                    } else {
                        $("#msg").html("<font style=\"color: #ff0000\">"+ data.msg +"</font>")
                    }
                }
            })
        }

    </script>

</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2023&nbsp;weiwei</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.html" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" placeholder="用户名" id="loginAct">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" placeholder="密码" id="loginPwd">
                </div>
                <div class="checkbox"  style="position: relative;top: 30px; left: 10px;">

                    <span id="msg"></span>

                </div>
                <%--将按钮类型设置为button--%>
                <button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
