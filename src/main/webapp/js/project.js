/**
 * Created by Administrator on 2016/8/4.
 */

var projectName, person, currentID, time, flag = true;
function Recodeload() {
    $(function () {
        $('#table').bootstrapTable({
            method: "get",
            striped: true,
            singleSelect: false,
            dataType: "json",
            pagination: true, //分页
            pageSize: 10,
            pageNumber: 1,
            search: false, //显示搜索框
            contentType: "application/x-www-form-urlencoded",
            queryParams: null,
            columns: [
                /*{
                    checkbox:"true",
                },*/
                {
                    title: "项目名称",
                    field: 'name',
                    align: 'center'
                },
               /* {
                    title: '项目金额',
                    field: 'sex',
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: '发起人',
                    field: 'type',
                    align: 'center'
                },*/
                {
                    title: '创建时间',
                    field: 'create_time',
                    align: 'center',
                    formatter:function (value) {
                        return changeDateFormat(value);
                    }
                },
                {
                    title: '操作',
                    field: 'id',
                    align: 'center',
                    formatter: function (value, row) {
                        var e = '<button button="#" mce_href="#" onclick="editRecode(\'' + row.id + '\')">编辑</button> ';
                        var c = '<button button="#" mce_href="#" onclick="delRecode(\'' + row.id + '\')">删除</button> ';
                        return e+c;
                    }
                }
            ]
        });
    });
   getRecodeTableData();
}
function getRecodeTableData() {
    if (flag) {
        projectName = "";
        person = "";
        time = "";
        flag = false;
    } else {
        projectName = $("#name").val();
        person = $("#person").val();
        time  = $("#time").val();
    }
    $.ajax({
        type: "get",
        url: "/project/findProjectList?projectName="+projectName+"&person="+person+"&time="+time,
        dataType: "json",
        success: function (result) {
            if (result.length > 0) {
                layer.msg("正在加载",{
                    icon:4,
                    time: 800 //1.5秒关闭（如果不配置，默认是3秒）
                },function () {
                    $('#table').bootstrapTable("load", result);
                });
            }else {
                layer.msg("哦~No 没有找到你要的数据！");
            }
        }
    })
}
function addRecode() {
    openlayer();
    currentID = "";
}
function editRecode(id) {
    currentID = id;
    openUpdateLayer();
}
function delRecode(id) {
    var RecodeId = id;
    $.ajax({
        url: '/project/deleteProject?id=' + RecodeId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if (data) {
                layer.msg('删除成功', {
                    icon: 1,
                    time: 800
                });
            } else {
                layer.msg('删除失败', {
                    icon: 4,
                    time: 800,
                    content:"正在返回项目管理页面"
                }, function(){
                    getRecodeTableData();
                });
            }

            window.location.reload();
        },
        error: function (err) {
        }
    });
}
function getCurrentID() {
    return currentID;
}
function openlayer() {
    layer.open({
        type: 2,
        title: '项目信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['98%', '98%'],
        shadeClose: true,
        closeBtn: 2,
        content:"../project/project_add.jsp"
    });
    
}
function openUpdateLayer() {
    layer.open({
        type: 2,
        title: '投资客户列表',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['98%', '98%'],
        shadeClose: true,
        closeBtn: 2,
        content:"../project/project_tail.jsp"
    });

}


function changeDateFormat(cellval) {
    if (cellval != null) {
        var date = new Date(cellval).toJSON();
        return new Date(+new Date(date)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
    }
}




