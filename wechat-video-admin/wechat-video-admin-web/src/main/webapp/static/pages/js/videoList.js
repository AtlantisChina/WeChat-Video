var updateVideoStatus = function (videoId, status) {
    if (status == 1) {
        var flag = window.confirm("是否确认禁播？");
        var sta = 2;
    } else {
        var flag = window.confirm("是否取消禁播？");
        var sta = 1;
    }
    if (!flag) {
        return;
    }
    $.ajax({
        url: $("#hdnContextPath").val() + "/video/updateVideoStatus.action?videoId=" + videoId + "&status=" + sta,
        type: "POST",
        async: false,
        success: function (data) {
            if (data.status == 200 && data.msg == "OK") {
                alert("操作成功");
                var jqGrid = $("#videoList");
                jqGrid.jqGrid().trigger("reloadGrid");
            } else {
                console.log(JSON.stringify(data));
            }
        }
    })
}

var List = function () {
    var handleList = function () {
        // 上下文对象路径
        var hdnContextPath = $("#hdnContextPath").val();
        var APIServer = $("#APIServer").val();

        var jqGrid = $("#videoList");
        jqGrid.jqGrid({
            caption: "短视频信息列表",
            url: hdnContextPath + "/video/list.action",
            mtype: "post",
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式  
            datatype: "json",
            colNames: ['ID', '发布者', '描述', '短视频', '播放长度', '被收藏数', '创建时间', '视频状态', '操作'],
            colModel: [
                {name: 'id', index: 'id', width: 30, sortable: false, hidden: false},
                {name: 'userId', index: 'userId', width: 30, sortable: false, hidden: false},
                {name: 'videoDesc', index: 'videoDesc', width: 30, sortable: false, hidden: false},
                {
                    name: 'videoPath', index: 'videoPath', width: 30, sortable: false, hidden: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var src = APIServer + cellvalue;
                        var display = "<a href='" + src + "' target='_blank'>点我播放</a>"
                        return display;
                    }
                },
                {name: 'videoSeconds', index: 'videoSeconds', width: 30, sortable: false, hidden: false},
                {name: 'likeCounts', index: 'likeCounts', width: 30, sortable: false, hidden: false},
                {
                    name: 'createTime', index: 'createTime', width: 40, sortable: false, hidden: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var createTime = Common.formatTime(cellvalue, 'yyyy-MM-dd HH:mm:ss');
                        return createTime;
                    }
                },
                {
                    name: 'status', index: 'status', width: 40, sortable: false, hidden: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue == 1 ? '正常' : '禁播';
                    }
                },
                {
                    name: '', index: '', width: 20, sortable: false, hidden: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var button;
                        if (rowObject.status === 1) {
                            button = '<button class="btn btn-outline blue-chambray" id="" onclick=updateVideoStatus("' + rowObject.id + '","' + rowObject.status + '") style="padding: 1px 3px 1px 3px;">禁止播放</button>';
                        } else {
                            button = '<button class="btn btn-outline blue-chambray" id="" onclick=updateVideoStatus("' + rowObject.id + '","' + rowObject.status + '") style="padding: 1px 3px 1px 3px;">取消禁止</button>';
                        }
                        return button;
                    }
                }
            ],
            viewrecords: true,  		// 定义是否要显示总记录数
            rowNum: 10,					// 在grid上显示记录条数，这个参数是要被传递到后台
            rownumbers: true,  			// 如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'
            autowidth: true,  			// 如果为ture时，则当表格在首次被创建时会根据父元素比例重新调整表格宽度。如果父元素宽度改变，为了使表格宽度能够自动调整则需要实现函数：setGridWidth
            height: 500,				// 表格高度，可以是数字，像素值或者百分比
            rownumWidth: 36, 			// 如果rownumbers为true，则可以设置行号 的宽度
            pager: "#videoListPager",		// 分页控件的id
            subGrid: false				// 是否启用子表格
        }).navGrid('#videoListPager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        // 随着窗口的变化，设置jqgrid的宽度  
        $(window).bind('resize', function () {
            var width = $('.videoList_wrapper').width() * 0.99;
            jqGrid.setGridWidth(width);
        });

        // 不显示水平滚动条
        jqGrid.closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});

        // 条件查询所有用户列表
        $("#searchVideoListButton").click(function () {
            var searchUsersListForm = $("#searchVideoListForm");
            jqGrid.jqGrid().setGridParam({
                page: 1,
                url: hdnContextPath + "/video/list.action?" + searchUsersListForm.serialize(),
            }).trigger("reloadGrid");
        });
    }
    return {
        // 初始化各个函数及对象
        init: function () {
            handleList();
        }
    };
}();


jQuery(document).ready(function () {
    List.init();
});