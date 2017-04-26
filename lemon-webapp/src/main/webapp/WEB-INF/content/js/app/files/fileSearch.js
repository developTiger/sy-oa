define(function (require, exports, module) {

    require("jquery");
    var template = require("template");
    require("ajaxUpload");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    widget.init();
    var List = require("../common/pagelist");

    loadData();// 0  清空  urldate  1：新增 2：忽略
    $("#filequery").click(function () {
        loadData();
    });

    var urlDate = [];

    function loadData(backType) {
        var tpl = require("text!app/tpl/file/fileListTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize();
        List("#table", tpl, url, data, 1, 10);

    }


    $("body").delegate(".delete", "click", function () {
        var a = confirm("确定要删除么？");
        if (a == true) {
            var url = $(this).attr("data-url");
            var id = $(this).attr("data-id");
            var options = {
                url: url + "?t=" + new Date().getMilliseconds() + "&id=" + id,
                type: 'get',
                success: function (data) {
                    if (data.isSuccess) {
                        //alert("操作成功");//没必要那么多弹窗
                        loadData(2);
                    }
                    else
                        alert(data.msg);
                }
            };
            $.ajax(options);
        }
        else {
            return;
        }

    })
})