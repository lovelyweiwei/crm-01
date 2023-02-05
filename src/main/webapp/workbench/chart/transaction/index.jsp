<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    /*
        根据交易表中的不同阶段的数量进行一个统计，形成一个倒三角

        select
            stage,count(*)
        from
            tbl_tran
        group by stage
     */
%>


<html>
<head>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <title>Title</title>
</head>
<script src="ECharts/echarts.min.js"></script>
<script src="jquery/jquery-1.11.1-min.js"></script>

<script>

    $(function (){

        //页面加载完成后，绘制统计图表
        getCharts()

    })

    function getCharts(){

        $.ajax({

            url : "workbench/transaction/getCharts.do",
            type : "get",
            dataType : "json",
            success : function (data) {
                /*
                    data
                        {"total":total,"dataList":{}{}}
                 */

                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'))

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '交易漏斗图'
                    },
                    series: [
                        {
                            name: '交易漏斗图',
                            type: 'funnel',
                            left: '10%',
                            top: 60,
                            bottom: 60,
                            width: '80%',
                            min: 0,
                            max: data.total,
                            minSize: '0%',
                            maxSize: '100%',
                            sort: 'descending',
                            gap: 2,
                            label: {
                                show: true,
                                position: 'inside'
                            },
                            labelLine: {
                                length: 10,
                                lineStyle: {
                                    width: 1,
                                    type: 'solid'
                                }
                            },
                            itemStyle: {
                                borderColor: '#fff',
                                borderWidth: 1
                            },
                            emphasis: {
                                label: {
                                    fontSize: 20
                                }
                            },
                            data: data.dataList
                            /*[
                                { value: 60, name: 'Visit' },
                                { value: 13, name: 'Inquiry' },
                                { value: 2, name: 'Order' },
                                { value: 80, name: 'Click' },
                                { value: 100, name: 'Show' }
                            ]*/
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }

        })

    }

</script>
<body>
    <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>
